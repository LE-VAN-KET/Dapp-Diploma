package io.ketlv.ediplomadapp.config;

import io.ketlv.ediplomadapp.filter.AuthenticationTokenFilter;
import io.ketlv.ediplomadapp.security.handler.AccessDeniedHandlerResolver;
import io.ketlv.ediplomadapp.security.handler.UnauthorizedEntryPoint;
import io.ketlv.ediplomadapp.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final AccessDeniedHandlerResolver accessDeniedHandlerResolver;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationTokenFilter authenticationJwtTokenFilter;

    @Autowired
    public SecurityConfig(UnauthorizedEntryPoint unauthorizedEntryPoint,
                          AccessDeniedHandlerResolver accessDeniedHandlerResolver,
                          CustomUserDetailsService customUserDetailsService,
                          AuthenticationTokenFilter authenticationJwtTokenFilter
    ) {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.accessDeniedHandlerResolver = accessDeniedHandlerResolver;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers("/actuator/health")
                .antMatchers("/webjars/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/api/auth/**")
                .antMatchers("/v1/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui/**");
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers()
                .contentSecurityPolicy("script-src 'self'");

        // enable cors and prevent CSRF
//        http = http.cors().configurationSource(
//                corsConfigurationSource()).csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and();
        http = http.cors().and().csrf().disable();
        // set session management stateless
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // set unauthorized request exception handler
        http = http.exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedEntryPoint)
                .accessDeniedHandler(accessDeniedHandlerResolver).and();

        http = http.headers()
                .frameOptions().sameOrigin().and();

        // set permission on endpoints
        http.authorizeHttpRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated().and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");

        // Add jwt token filter to validate tokens with every request
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

package io.ketlv.ediplomadapp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ketlv.ediplomadapp.exception.CommonErrorResponse;
import io.ketlv.ediplomadapp.exception.InvalidTokenHeader;
import io.ketlv.ediplomadapp.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.debug("JwtTokenFilter is calling for uri: {} {}",
                    request.getMethod(), request.getRequestURI());

            String jwt = tokenProvider.parseJwt(request);
            String requestUri = request.getRequestURI();

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.parseAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("Set Authentication to security context for '{}', uri: {}",
                        authentication.getName(), requestUri);
            }

            filterChain.doFilter(request, response);
        } catch (InvalidTokenHeader invalidTokenHeader) {
            log.error("[Error] Message: {}", invalidTokenHeader.getMessage());
            CommonErrorResponse commonErrorResponse = new CommonErrorResponse(
                    HttpStatus.UNAUTHORIZED,
                    invalidTokenHeader.getMessage());
            response.setContentType("application/json");
            response.setStatus(commonErrorResponse.getCode());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(commonErrorResponse);
            response.getWriter().write(json);
        }
    }

}

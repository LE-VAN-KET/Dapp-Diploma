package io.ketlv.ediplomadapp.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ketlv.ediplomadapp.exception.UserNotFoundException;
import io.ketlv.ediplomadapp.mapper.UserMapper;
import io.ketlv.ediplomadapp.security.model.CustomUserPrincipal;
import io.ketlv.ediplomadapp.services.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayList;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;
    private final HttpServletRequest request;

    @Autowired
    public CustomUserDetailsService(UserMapper userMapper,
                                    HttpServletRequest request) {
        this.userMapper = userMapper;
        this.request = request;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUserPrincipal loadUserByUsername(String username) {
        UserDto user = userMapper.findByUsernameEqualsIgnoreCase(username);
        if (user == null) {
            new UserNotFoundException("Username is not found!");
        }

        Collection<? extends GrantedAuthority> authorities = getAuthorities(new ArrayList<>(Collections.singleton(user.getAuthorityName())));

        if (user.getPassword() == null && user.getInitPassword() != null) {
            user.setPassword(user.getInitPassword());
        }

        return new CustomUserPrincipal(null,
                username,
                user.getPassword(),
                user.getId().toString(),
                user.getFullName(),
                true,
                authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roleList) {
        return roleList.stream().map(roleName ->
                new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toSet());
    }
}

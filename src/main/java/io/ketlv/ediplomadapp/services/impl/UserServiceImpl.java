package io.ketlv.ediplomadapp.services.impl;

import io.ketlv.ediplomadapp.config.properties.TokenProperties;
import io.ketlv.ediplomadapp.domain.User;
import io.ketlv.ediplomadapp.event.OnSendPassToMailEvent;
import io.ketlv.ediplomadapp.exception.CommonBadRequest;
import io.ketlv.ediplomadapp.exception.UserNotFoundException;
import io.ketlv.ediplomadapp.mapper.UserInformationMapper;
import io.ketlv.ediplomadapp.mapper.UserMapper;
import io.ketlv.ediplomadapp.security.jwt.TokenCreator;
import io.ketlv.ediplomadapp.security.jwt.TokenPair;
import io.ketlv.ediplomadapp.security.model.CustomUserPrincipal;
import io.ketlv.ediplomadapp.security.repository.RedisTokenRepository;
import io.ketlv.ediplomadapp.services.MailService;
import io.ketlv.ediplomadapp.services.UserService;
import io.ketlv.ediplomadapp.services.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserInformationMapper userInformationMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RedisTokenRepository redisTokenRepository;
    private final TokenCreator tokenCreator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void addUser(AddUserReq req) {
        boolean isExistUsId = userMapper.isExistID(req.getId());
        if (isExistUsId) {
            throw new CommonBadRequest("User ID is already exist.");
        }

        boolean isExistUsername = userMapper.isExistUsername(req.getUsername());
        if (isExistUsername) {
            throw new CommonBadRequest("Username is already exist.");
        }

        boolean isExistEmail = userMapper.isExistEmail(req.getEmail());
        if (isExistEmail) {
            throw new CommonBadRequest("Email is already exist.");
        }

        if (req.getCitizenIdentityNo() != null && !"".equals(req.getCitizenIdentityNo())) {
            boolean isExistCitizenNo = userInformationMapper.isExistCitizenIdentityNo(req.getCitizenIdentityNo());
            if (isExistCitizenNo) {
                throw new CommonBadRequest("Citizen identity No is already exist.");
            }
        }

        if(req.getPhoneNo() != null && !"".equals(req.getPhoneNo())) {
            boolean isExistPhoneNo = userInformationMapper.isExistPhoneNo(req.getPhoneNo());
            if (isExistPhoneNo) {
                throw new CommonBadRequest("Phone No is already exist.");
            }
        }

        User user = new User();
        BeanUtils.copyProperties(req, user);
        user.setFirstLogin(new Timestamp(System.currentTimeMillis()));
        user.setInitPassword(passwordEncoder.encode(user.getInitPassword()));
        user.setDisable(false);
        userMapper.addUser(user);
        userInformationMapper.addUserInfo(req);

        eventPublisher.publishEvent(new OnSendPassToMailEvent(this, req.getEmail(), req.getInitPassword()));

    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.getAll();
    }

    @Override
    public LoginResponse signin(LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            TokenPair tokenPair = createAndSaveToken(authentication);
            List<String> roleNameList = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
            return LoginResponse.builder()
                    .userId(principal.getSubId())
                    .donviSymbol(principal.getdonviSymbol())
                    .accessToken(tokenPair.getAccessToken())
                    .expiresIn(TokenProperties.accessTokenValidityInSeconds)
                    .refreshToken(tokenPair.getRefreshToken())
                    .refreshExpiresIn(TokenProperties.refreshTokenValidityInSeconds)
                    .role(roleNameList.get(0))
                    .tokenType("Bearer").build();
        } catch (UserNotFoundException exception) {
            throw new CommonBadRequest(exception.getMessage());
        } catch (BadCredentialsException | InternalAuthenticationServiceException badCredentialsException) {
            log.error("AuthenticationError: {}, cause: {}", badCredentialsException.getMessage(),
                    badCredentialsException.getCause());
            throw new UserNotFoundException("Incorrect Username or password!.");
        }
    }

    private TokenPair createAndSaveToken(Authentication authentication) {
        TokenPair tokenPair = tokenCreator.createTokenPair(authentication);
        redisTokenRepository.save(authentication.getName(), tokenPair);
        return tokenPair;
    }
}

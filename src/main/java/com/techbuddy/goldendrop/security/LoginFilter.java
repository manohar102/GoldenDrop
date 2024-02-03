package com.techbuddy.goldendrop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.request.LoginRequest;
import com.techbuddy.goldendrop.service.JWTTokenService;
import com.techbuddy.goldendrop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Log4j2
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserService userService;

    @Autowired
    JWTTokenService jwtService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    private final RequestMatcher loginRequestMatcher = new AntPathRequestMatcher("/login", HttpMethod.POST.toString());

    public LoginFilter(String string) {
        super(string);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        Authentication auth = null;
        if (isLoginRequest(request)) {
            try {
                LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
                log.info(String.format("Attempting Authentication for username : %s", loginRequest.getEmail()));
                User user = userService.loadUserByUsername(loginRequest.getEmail());
                if (authenticate(user, loginRequest)) {
                    auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                } else {
                    throw new Exception("Authentication Failure, Invalid Password");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return auth;
    }

    private Boolean authenticate(UserDetails userDetails, LoginRequest loginRequest) {
        return passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());
    }

    private Boolean authenticate(User user, LoginRequest loginRequest) {
        return passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response) && isLoginRequest(request);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        if (authResult != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            User user = (User) authResult.getPrincipal();
            APIToken apiToken = new APIToken(user.getId(), user.getUsername(), user.getRole());
            String token = jwtService.generateToken(apiToken);
            response.setContentType("application/json");
            Cookie cookie = new Cookie(JWTTokenService.JWT_COOKIE_NAME, token);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 3600);
            cookie.isHttpOnly();
            response.addCookie(cookie);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authResult);
            SecurityContextHolder.setContext(context);
        }
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return loginRequestMatcher.matches(request);
    }
}

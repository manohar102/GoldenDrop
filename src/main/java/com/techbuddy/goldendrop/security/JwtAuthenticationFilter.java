package com.techbuddy.goldendrop.security;

import com.techbuddy.goldendrop.constant.URLConstants;
import com.techbuddy.goldendrop.exception.UserDeletedException;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.service.JWTTokenService;
import com.techbuddy.goldendrop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private final RequestMatcher loginRequestMatcher = new AntPathRequestMatcher(URLConstants.LOGIN_URL,
            HttpMethod.POST.toString());

    private final RequestMatcher registerRequestMatcher = new AntPathRequestMatcher(URLConstants.REGISTER_URL,
            HttpMethod.POST.toString());

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Autowired
    UserService userService;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(String string) {
        super(string);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Authentication auth = null;
        String jwtCookie = getJWTCookieValue(request);
        if(jwtCookie != null) {
            APIToken apiToken = jwtTokenService.parseToken(jwtCookie);
            User user = userService.loadUserByUsername(apiToken.getEmail());
            if (!user.isEnabled()) {
                throw new UserDeletedException("User is not active, please check with admin.");
            }
            auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        if (auth == null) {
            throw new BadCredentialsException("AUTH TOKEN MISSING");
        }
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        if (authResult != null) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authResult);
            SecurityContextHolder.setContext(context);
        }
        chain.doFilter(request, response);
    }

    private String getJWTCookieValue(HttpServletRequest request) {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JWTTokenService.JWT_COOKIE_NAME)) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response)
                && !isLoginRequest(request) && !isRegistrationRequest(request);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return loginRequestMatcher.matches(request);
    }

    private boolean isRegistrationRequest(HttpServletRequest request) {
        return registerRequestMatcher.matches(request);
    }
}

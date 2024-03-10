package com.techbuddy.goldendrop.configuration;

import com.techbuddy.goldendrop.constant.URLConstants;
import com.techbuddy.goldendrop.security.JwtAuthenticationFilter;
import com.techbuddy.goldendrop.security.LoginFilter;
import com.techbuddy.goldendrop.service.JWTTokenService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    //    @Bean
    //    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    //    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers(HttpMethod.POST, URLConstants.REGISTER_URL, URLConstants.LOGIN_URL)
                .permitAll()
                .requestMatchers(HttpMethod.GET, URLConstants.SWAGGER_API_DOCS_URL)
                .permitAll()
                .requestMatchers(HttpMethod.GET, URLConstants.SWAGGER_URL)
                .permitAll()
                .requestMatchers(HttpMethod.GET, URLConstants.PRODUCT_POPULAR_URL)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutRequestMatcher(
                new AntPathRequestMatcher(URLConstants.LOGOUT_URL, "GET")));
        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                .deleteCookies("JSESSIONID")
                .deleteCookies(JWTTokenService.JWT_COOKIE_NAME)
                .invalidateHttpSession(true));

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(loginFilter(), JwtAuthenticationFilter.class);
        http.cors(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter("/**/*");
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager(userDetailsService, passwordEncoder()));
        return jwtAuthenticationFilter;
    }

    @Bean
    public LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter("/login");
        loginFilter.setAuthenticationManager(authenticationManager(userDetailsService, passwordEncoder()));
        return loginFilter;
    }
}

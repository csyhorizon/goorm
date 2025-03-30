package org.chinoel.goormspring.config;

import jakarta.servlet.DispatcherType;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests((auth) ->
                        auth
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers("/", "/auth/**", "/css/**", "/js/**", "/images/**", "/signup", "/login").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/auth/login")
                                .defaultSuccessUrl("/", true)
                                .failureHandler(((request, response, exception) -> {
                                    String errorMessage = "아이디 또는 비밀번호가 올바르지 않습니다.";
                                    String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                                    String redirectUrl = String.format("/login?error=true&message=%s", encodedMessage);

                                    log.debug("Login failed. {}", errorMessage);

                                    response.sendRedirect(redirectUrl);
                                }))
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                ).exceptionHandling(handling -> handling
                        .accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

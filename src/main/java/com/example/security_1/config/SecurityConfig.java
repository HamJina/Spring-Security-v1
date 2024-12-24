package com.example.security_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/user/**")).authenticated() // "/user/**" 경로는 인증된 사용자만 접근 가능
                        .requestMatchers(new AntPathRequestMatcher("/manager/**"))
                        .hasAnyRole("ADMIN", "MANAGER") // "/manager/**" 경로는 ADMIN 또는 MANAGER 권한 필요
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN") // "/admin/**" 경로는 ADMIN 권한 필요
                        .anyRequest().permitAll() // 그 외 모든 요청 허용
                )
                .formLogin(form -> form
                        .loginPage("/loginForm") // 커스텀 로그인 페이지 경로
                        .loginProcessingUrl("/login") // 로그인 처리 URL
                        .defaultSuccessUrl("/") // 로그인 성공 후 이동할 URL
                        .permitAll() // 로그인 관련 요청은 모두 허용
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 처리 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                );

        return http.build();
    }
}

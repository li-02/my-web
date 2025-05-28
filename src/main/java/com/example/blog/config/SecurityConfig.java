package com.example.blog.config;

import com.example.blog.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置
 * 可以参考Spring Security官方文档标准配置示例
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*
    标准模板
     */

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // 1. 密码加密器 - 必须有
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 这是最常用的加密方式
    }

    // 2. 认证提供者 - 告诉Spring怎么验证用户
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // 用户查询服务
        authProvider.setPasswordEncoder(passwordEncoder());// 密码加密器
        return authProvider;
    }

    // 3. 认证管理器 - 固定写法
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) //关闭CSRF保护（API项目常用）
                // 无状态会话（JWT需要）
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // 公开接口
                        .requestMatchers("/auth/login").permitAll() // 登录接口公开
                        .requestMatchers("/public/**").permitAll() // 前台接口公开
                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll() // Swagger UI和API文档公开
                        // 管理员接口需要认证
                        .requestMatchers("/admin/**").authenticated() // 管理员接口需要认证
                        // 其他请求允许访问
                        .anyRequest().permitAll()
                )
                //添加JWT验证过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
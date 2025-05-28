package com.example.blog.controller;

import com.example.blog.dto.LoginRequest;
import com.example.blog.dto.LoginResponse;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import com.example.blog.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证接口", description = "用户登录认证相关接口")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "管理员登录接口")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // 认证用户
            /*
              背后调用
              找用户 - 调用 CustomUserDetailsService
              验证密码 - 用 BCryptPasswordEncoder 比较加密密码
              处理异常 - 用户不存在、密码错误等
              返回结果 - 成功就返回认证信息，失败就抛异常
             */
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // 生成JWT令牌
            String token = jwtUtil.generateToken(loginRequest.getUsername());

            // 更新最后登录时间
            userService.updateLastLoginTime(loginRequest.getUsername());

            // 获取用户信息
            User user = userService.findByUsername(loginRequest.getUsername()).orElse(null);

            // 返回登录响应
            LoginResponse response = null;
            if (user != null) {
                response = new LoginResponse(
                        token,
                        user.getUsername(),
                        expiration / 1000 // 转换为秒
                );
            }

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "用户名或密码错误");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "用户退出", description = "退出登录")
    public ResponseEntity<?> logout() {
        // JWT是无状态的，客户端删除token即可
        Map<String, String> response = new HashMap<>();
        response.put("message", "退出成功");
        return ResponseEntity.ok(response);
    }
}

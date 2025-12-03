# CORS 配置指南

## 问题描述

前端在访问后端 API 时遇到 **403 FORBIDDEN** 错误，且浏览器显示 OPTIONS 预检请求被拒绝。

```
OPTIONS /api/auth/login → 403 FORBIDDEN
```

## 原因分析

1. **浏览器安全机制**：在发送跨域 POST/PUT/DELETE 请求前，浏览器会先发送 OPTIONS 预检请求
2. **后端 CORS 配置缺失**：后端未正确配置 CORS 响应头

## 解决方案（后端配置）

### Spring Boot (Java) 配置

在后端项目中添加 CORS 配置类：

```java
package com.lggyx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8913", "http://localhost:5173")  // 前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

或在 application.yml 中配置：

```yaml
spring:
  web:
    cors:
      allowed-origins: 'http://localhost:8913,http://localhost:5173'
      allowed-methods: 'GET,POST,PUT,DELETE,OPTIONS'
      allowed-headers: '*'
      allow-credentials: true
      max-age: 3600
```

### Spring Security（如果使用）

如果后端使用 Spring Security，需要在安全配置中允许 CORS：

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
                .and()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8913", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
```

## 前端配置（已完成）

前端 axios 实例已配置：

```javascript
// src/api/index.js
const instance = axios.create({
  baseURL: 'http://localhost:8912',
  timeout: 10000,
  withCredentials: true, // ✅ 允许跨域请求时携带 cookies
  headers: {
    'Content-Type': 'application/json',
  },
})
```

## 测试步骤

### 1. 验证后端 CORS 配置

在浏览器开发工具中发送 OPTIONS 预检请求：

```bash
curl -X OPTIONS http://localhost:8912/api/auth/login \
  -H "Origin: http://localhost:8913" \
  -H "Access-Control-Request-Method: POST" \
  -H "Content-Type: application/json" \
  -v
```

应该看到响应头：

```
Access-Control-Allow-Origin: http://localhost:8913
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: *
Access-Control-Max-Age: 3600
```

### 2. 前端调试

在浏览器 F12 → Network 标签中：

- 查看 OPTIONS 请求是否返回 200
- 查看 POST 请求的响应体是否包含 { code, msg, data }

### 3. 控制台日志

前端已添加详细日志：

```
[API] POST http://localhost:8912/api/auth/login { account, password }
[API Response] { code: 200, msg: "success", data: { token: "...", ... } }
[Login] Success, navigating to home
```

如果看到 403 或其他错误，会显示：

```
[API Error] {
  message: "...",
  status: 403,
  statusText: "FORBIDDEN",
  data: {...},
  url: "/api/auth/login"
}
```

## 常见错误及解决方案

| 错误                | 原因                   | 解决方案                         |
| ------------------- | ---------------------- | -------------------------------- |
| 403 FORBIDDEN       | CORS 配置缺失          | 后端添加上述配置                 |
| 401 UNAUTHORIZED    | 认证失败               | 检查账号密码                     |
| 504 Gateway Timeout | 后端未启动             | 启动后端服务 `java -jar xxx.jar` |
| CORS policy blocked | 前端地址未在后端白名单 | 添加前端地址到 allowedOrigins    |

## 生产环境配置

将 `allowedOrigins` 改为实际的前端部署地址：

```java
.allowedOrigins("https://your-domain.com")
```

## 联系方式

如仍有问题，请检查：

1. 后端是否已重启（配置修改后需重启）
2. 前端是否正确指向后端地址（检查 `VITE_API_BASE` 环境变量）
3. 防火墙是否允许 8912 端口访问

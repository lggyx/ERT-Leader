package com.lggyx.filter;

import com.lggyx.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;          // ← 构造器注入
    private static final AntPathMatcher MATCHER = new AntPathMatcher();
    private static final Set<String> SKIP = Set.of(
            "/doc.html",
            "/swagger-ui/*",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api/auth/login",
            "/api/auth/register");

    public JwtFilter(JwtUtil jwtUtil) {     // ← 手动 new 时传进来
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // 放行 OPTIONS（CORS 预检）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // 白名单匹配
        for (String pattern : SKIP) {
            if (MATCHER.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        request.setAttribute("account", jwtUtil.getAccountFromToken(token));
        chain.doFilter(request, response);
    }
}
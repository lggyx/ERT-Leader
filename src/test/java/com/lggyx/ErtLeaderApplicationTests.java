package com.lggyx;

import com.lggyx.constant.JwtClaimsConstant;
import com.lggyx.properties.JwtProperties;
import com.lggyx.utils.JwtUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ErtLeaderApplicationTests {

    @Test
    void md5Password() {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

    @Resource
    private JwtProperties jwtProperties;
    @Test
    void testJwt() {
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 将密钥转换为 Base64 编码的字符串
        String base64EncodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Base64 Encoded Key: " + base64EncodedKey);
        claims.put(JwtClaimsConstant.USER_ID, "1" /*id*/);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1小时后过期
                .signWith(key)
                .addClaims(claims);
        String jwt = jwtBuilder.compact();
        System.out.println("JWT: " + jwt);
    }
    @Test
    void testJwtUtil() {
        JwtUtil jwtUtil = new JwtUtil(jwtProperties);
        String token = jwtUtil.generateToken("123wwas@163.com");
        System.out.println(token);
        System.out.println(jwtUtil.getAccountFromToken(token));
        System.out.println(jwtUtil.validateToken(token));
    }

}

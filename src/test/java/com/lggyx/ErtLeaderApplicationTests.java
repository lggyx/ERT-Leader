package com.lggyx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.constant.JwtClaimsConstant;
import com.lggyx.mapper.AnswerMapper;
import com.lggyx.pojo.entity.Answer;
import com.lggyx.properties.JwtProperties;
import com.lggyx.utils.JwtUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.crypto.SecretKey;
import java.util.*;

/** todo 测试类
 *      此处的参数为项目路径下的.env.properties文件，启动前需设置参数
 *      运行此测试类时，请将.env.properties文件放在项目路径下
 *      参考参数1：spring.config.additional-location=file:D:/ERT-Leader/.env.properties
 *      参考参数2：spring.config.additional-location=file:D:/WorkSpace/ERT-Leader/.env.properties
 */
@SpringBootTest(properties = "spring.config.additional-location=file:D:/ERT-Leader/.env.properties")
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

    @Resource
    AnswerMapper answerMapper;
    @Test
    void testAnswerMapper() {
        LambdaQueryWrapper<Answer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Answer::getAssessmentId, 5L);

        List<Answer> answerList = answerMapper.selectList(queryWrapper);

        System.out.println(answerList);
        System.out.println(answerList.size());
    }
}

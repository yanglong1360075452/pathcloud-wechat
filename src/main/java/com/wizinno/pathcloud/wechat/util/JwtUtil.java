package com.wizinno.pathcloud.wechat.util;

import com.google.gson.Gson;
import com.wizinno.pathcloud.wechat.data.Config;
import com.wizinno.pathcloud.wechat.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * Created by LiuMei on 2017-05-23.
 */
@Component
public class JwtUtil {

    private static Gson gson = new Gson();
    private static Logger log = LoggerFactory.getLogger(JwtUtil.class);

//    @Value("${spring.profiles.active}")
    private  String profiles;

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public  SecretKey generalKey() {
        String stringKey = profiles + Config.jwtSecret;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public  String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 生成subject信息
     *
     * @param user
     * @return
     */
    public static String generalSubject(User user) {
        Map<String, Object> userMap = new HashedMap();
        userMap.put("openId",user.getOpenId());
        userMap.put("username", user.getUsername());
        return gson.toJson(userMap);
    }

}

package com.spz.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//SignatureVerificationException //签名不一致异常
//TokenExpiredException //令牌过期异常
//AlgorithmMismatchException //算法不匹配异常
//InvalidClaimException //失效的payload异常（传给客户端后，token被改动，验证不一致）
public class JWTUtils {
    // 过期续签 后期
    //    加密语言 加密码可以变动，有缓存就行，变动时前后两个加密码生成的token均可用
    // 加解密都在工具类中完成
    private static String SIGNATURE = "token!@#$%^7890";

    /**
     * 生成token
     * @param map //传入payload
     * @return 返回token
     */
    public static String generateTokenByStringMap(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();

        map.forEach((key,value)->{
            builder.withClaim(key,value);
        });

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,7);
        instance.add(Calendar.MINUTE,15);
//        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 验证token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 获取token中payload
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }


    public static Map<String, String> getStingMapByToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);

        Map<String, Claim> claimsMap = jwt.getClaims();
        Map<String, String> result = new HashMap<>();

        claimsMap.forEach((key, value) -> {
            result.put(key, value.asString());
        });

        return result;
    }

    public static String generateTokenById(int id){
        // token优点：不易被篡改，可以设置有效期
        // 缺点 可以被解密，被破解
        // 现在的逻辑太简单了，就相当于没有防御，还不如用session存储
        // 开发阶段能用就行，不考虑效率，安全
        return JWT.create().withClaim("id",id).sign(Algorithm.HMAC256(SIGNATURE));
    }
    public static int getIdByToken(String token){
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
        return jwt.getClaim("id").asInt();
    }
}

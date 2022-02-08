package com.example.dechuan.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.dechuan.model.usermanage.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TokenUtil {
	private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);
	 private static final long EXPIRE_TIME= 10*60*60*1000;
//	 private static final long EXPIRE_TIME= 10;
	    private static final String TOKEN_SECRET="txdy";  //密钥盐
	    /**
	     * 签名生成测试
	     * @param user
	     * @return
	     */
	    public static String sign(User user){
	        String token = null;
	        try {
	            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
	            token = JWT.create()
	                    .withIssuer("auth0")
	                    .withClaim("userName", user.getUserName())
	                    .withExpiresAt(expiresAt)
	                    // 使用了HMAC256加密算法。
	                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
	        } catch (Exception e){
	            e.printStackTrace();
	        }
	        return token;
	    }

	    /**
	     * 签名验证
	     * @param token
	     * @return
	     */
	    public static boolean verify(String token){
	        try {
	            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
	            DecodedJWT jwt = verifier.verify(token);
	            log.info("认证通过");
	            log.info("userName: " + jwt.getClaim("userName").asString());
	            log.info("过期时间：      " + jwt.getExpiresAt());
	            return true;
	        } catch (Exception e){
	            return false;
	        }
	    }

	    

}

package com.example.dechuan.core;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final String TOKEN_SECRET="txdy";  //密钥盐
	private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
	
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
	        if(request.getMethod().equals("OPTIONS")){
	            response.setStatus(HttpServletResponse.SC_OK);
	            return true;
	        }
	        
	        //http://localhost:8080/workordermanage/list
	        StringBuffer requestURL = request.getRequestURL();
	        if(requestURL.substring(0, 21).equals("http://localhost:8083")){
	        	 response.setStatus(HttpServletResponse.SC_OK);
		            return true;
	        }
	        
	        response.setCharacterEncoding("utf-8");
	        String token = request.getHeader("token");
	        if(token != null){
	        	HttpSession sessoin=request.getSession();//这就是session的创建
	            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
	            try{
		            DecodedJWT jwt = verifier.verify(token);
		            sessoin.setAttribute("username",jwt.getClaim("userName").asString());
	            }catch(Exception e){
		            JSONObject json = new JSONObject();
		            json.put("msg","token verify fail");
		            json.put("code","50000");
		            log.error("token时间过期");
		            response.getWriter().append(json.toJSONString());
		            return false;
	            }
	            boolean result = TokenUtil.verify(token);
	            if(result){
	            	log.info("通过拦截器");
	                return true;
	            }
	        }
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json; charset=utf-8");
	        try{
	            JSONObject json = new JSONObject();
	            json.put("msg","token verify fail");
	            json.put("code","50000");
	            response.getWriter().append(json.toJSONString());
	            log.info("认证失败，未通过拦截器");
	        }catch (Exception e){
	            e.printStackTrace();
	            response.sendError(500);
	            return false;
	        }
	        return false;
	    }

}

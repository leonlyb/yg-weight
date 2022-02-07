package com.example.dechuan.core;

import org.apache.coyote.Request;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 跨域请求支持/token拦截
 * tip:只能写在一个配置类里
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private TokenInterceptor tokenInterceptor;

    //构造方法
    public WebConfiguration(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        
        Request re = new Request();
        re.serverName();
        System.out.println(re.serverName());
        
//        URL resources = Thread.currentThread().getContextClassLoader().getResource("/");
//        @SuppressWarnings("all")
//		String pa = resources.getPath().substring(1);
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
        excludePath.add("/register");  //登录
        excludePath.add("/user/login");     //注册
        excludePath.add("/sigin");     //登录
        excludePath.add("/**");
        excludePath.add("/static/**");  //静态资源
        excludePath.add("/assets/**");  //静态资源
        excludePath.add("/swagger-ui.html");
        excludePath.add("/swagger**/**");
        excludePath.add("/swagger-resources/**");
        excludePath.add("/swagger-resources/**");
        excludePath.add("/swagger-ui.html/**");
        excludePath.add("/js");
        excludePath.add("/webjars/**");
        excludePath.add("/v2/api-docs/**");
        excludePath.add("/v2/api-docs/**");
        excludePath.add("/v2/api-docs-ext/**");
        excludePath.add("/docs.html");
        excludePath.add("/doc.html");
        excludePath.add("/error");
        excludePath.add("/");
        excludePath.add("/favicon.ico");


        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}

//package com.mee.generator.core.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.*;
//
//@Configuration
//public class AuthWebMvcConfigurer implements WebMvcConfigurer {
//
////    @Bean
////    public AuthInterceptor getAuthInterceptor(){
////        return new AuthInterceptor();
////    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //设置允许跨域的路径
//            registry
//                    .addMapping("/**")
//                //设置允许跨域请求的域名
//                .allowedOrigins("*")
//                .allowedHeaders("*")
////                .allowCredentials(true)//是否允许证书 不再默认开启
//                //设置允许的方法
//                .allowedMethods("GET", "PUT", "POST", "DELETE");
////                .maxAge(3600);//跨域允许时间
//    }
//
////    // 拦截器配置
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(getAuthInterceptor())
////                .addPathPatterns("/**")
//////                .excludePathPatterns("/","/login","/logout","/echo","/static/*","/*.html");
////                .excludePathPatterns("/login","/logout","/echo","/static/*");
////    }
//
////    /**
////     * 配置静态访问资源
////     * @param registry
////     */
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//////        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
////        registry.addResourceHandler("/*.html","*.html").addResourceLocations("classpath:/static/");
////    }
//
//}

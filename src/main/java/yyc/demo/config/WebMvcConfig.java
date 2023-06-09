package yyc.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yyc.demo.filter.RequestBodyFilter;
import yyc.demo.interceptor.ControllerHanderInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public RequestBodyFilter requestBodyFilter(){

        return new RequestBodyFilter();
    }

    @Bean
    public ControllerHanderInterceptor controllerHandlerInterceptor(){
        return new ControllerHanderInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(this.controllerHandlerInterceptor()).addPathPatterns("/**");
    }
}

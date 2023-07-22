package yyc.demo.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import yyc.demo.msg.Meg;
import yyc.demo.msg.Response;
//注解：RestControllerAdvice
//        增加拦截，并设定有效范围
//        basePackages指定包
//        basePackageClasses指定类
//
//接口ResponseBodyAdvice
//        默认需要重写supports和beforeBodyWrite。
//        当supports返回true时，会执行beforeBodyWrite，如果需要封装返回信息，可以在beforeBodyWrite进行逻辑处理。
//        这里我们是要设置统一异常处理，这里保持默认即可。
//
//捕捉异常方法handlerGlobeException  @handlerGlobeException 作用范围@RestControllerAdvice 等

//ExceptionHandler(value = Exception.class)
//        表示捕捉Exception异常
//        捕捉后处理handlerGlobeException的方法，这里稍微处理一下后，返回一个对象

@RestControllerAdvice
public class EceptionController implements ResponseBodyAdvice {

    @ExceptionHandler(value = Exception.class)
    //@ResponseBody
    public Response hanlerExctption(Exception e){

        Response<Meg> response = new Response<>();
        Meg meg = new Meg();
        meg.setCode("error");
        meg.setInfo(e.getMessage());
        response.setRespBody(meg);
        return response;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof Response){
            return body;
        }
        return null;
    }
}

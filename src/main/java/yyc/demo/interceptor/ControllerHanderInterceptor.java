package yyc.demo.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yyc.demo.exceptions.ComBizException;
import yyc.demo.msg.Request;
import yyc.demo.util.JacksonUtil;
import yyc.demo.wrapper.BodyReaderHttpServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHanderInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler){
        Request req = getRequest(request);
       // BaseResponseContextHolder.setReqHeader(req.getReqHeader());
        return true;

    }

    //异常不经过
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器后置方法");


    }

    //异常经过
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("拦截器afterCompletion");

    }






    public Request getRequest(HttpServletRequest request){
        String requestStr = getRequstString(request);
        System.out.println("输入报文"+requestStr);
        try{
           return JacksonUtil.parse(requestStr, Request.class);
        } catch (Exception e) {
            throw new ComBizException("0155","请求错误");
        }
    }

    private String getRequstString(HttpServletRequest request){
        return ((BodyReaderHttpServletRequestWrapper)request).getStr();
    }
}

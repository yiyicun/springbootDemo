//package yyc.demo.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//
//import org.springframework.core.annotation.Order;
//
//@Order(1)
//@WebFilter(filterName="firstFilter", urlPatterns="/*")
//public class FirstFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        System.out.println("first filter 1");
//        chain.doFilter(request, response);
//        System.out.println("first filter 2");
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//}
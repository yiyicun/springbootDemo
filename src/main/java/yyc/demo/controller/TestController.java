package yyc.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import yyc.demo.bean.Student;
import yyc.demo.mapper.UserMapper;
import yyc.demo.service.AsyncDemo;
import yyc.demo.service.TransationalImpl;
import yyc.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping(value = "/")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(AsyncDemo.class);



    @Autowired
    AsyncDemo asyncDemo;
    @Autowired
    UserService userService;
    @Autowired
    TransationalImpl transational;

    @RequestMapping(value = "/test")
    public String test(@RequestParam int id) throws Exception {

        if( id > 100){
            throw new Exception();
        }
        return "index";
    }

    @RequestMapping(value = "/xxx")
    @ResponseBody
    public String nginx() {
        System.out.println("xxx");
        HttpServletRequest request =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Enumeration<String> headers =  request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String value =  request.getHeader(name);
            if( name.equals("forwarded")) {
                System.out.println(name+"  :  "+value);
            }
        }



        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @RequestMapping(value = "/async")
    @ResponseBody
    public String testAsync() {


        HttpServletRequest request =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        System.out.println("Request URI: " + request.getRequestURI());

       // log.info("Controller 线程:{}",Thread.currentThread().getName());
        String ss = asyncDemo.asyncInvokeSimplest();

        return "Controller 线程,: "+Thread.currentThread().getName()+ "   async 线程: "+ss;
    }


    @RequestMapping(value = "/addUser")
    @ResponseBody
    public void addUser() {

        transational.addUser();

    }



}
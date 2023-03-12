package yyc.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import yyc.demo.bean.Student;
import yyc.demo.mapper.UserMapper;


@Component
public class AsyncDemo {

    private static final Logger log = LoggerFactory.getLogger(AsyncDemo.class);

    /**
     * 最简单的异步调用，返回值为void
     */
    @Async
    public String asyncInvokeSimplest() {
        log.info("async 线程{}:", Thread.currentThread().getName());

        return Thread.currentThread().getName();

    }




}

package yyc.demo.redis.lock;

import org.springframework.stereotype.Service;

/**
 * desc: 要获取锁的方法
 */
@Service
public class LockService {
    @Lock(key = "#user.id", maxSleepMills = 1,keepMills = 30)
    public String lock(User user) throws InterruptedException {
        System.out.println("持锁");
        Thread.sleep(20000);
        return "";
    }
}

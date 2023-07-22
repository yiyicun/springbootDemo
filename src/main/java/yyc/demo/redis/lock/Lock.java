package yyc.demo.redis.lock;

import java.lang.annotation.*;
/**
 * desc: 自定义 redisson 分布式锁注解
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    /**
     * 锁的key spel 表达式
     *
     * @return
     */
    String key();
    /**
     * 持锁时间
     *
     * @return
     */
    long keepMills() default 20;
    /**
     * 没有获取到锁时，等待时间
     *
     * @return
     */
    long maxSleepMills() default 30;
}

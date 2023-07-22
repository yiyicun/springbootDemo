package yyc.demo.redis.lock.test;

import java.util.concurrent.TimeUnit;

public class RedissonTest {
    public static void main(String[] args) throws Exception{
        Thread.sleep(2000L);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    //tryLock，第三个参数是等待时间，5秒内获取不到锁，则直接返回。 第四个参数 30是30秒后强制释放
                    boolean hasLock = DistributedLock.tryLock("lockKey", TimeUnit.SECONDS,5,30);
                    //获得分布式锁
                    if(hasLock){
                        System.out.println("idea1: " + Thread.currentThread().getName() + "获得了锁");

                        /**
                         * 由于在DistributedLock.tryLock设置的等待时间是5s，
                         * 所以这里如果休眠的小于5秒，这第二个线程能获取到锁，
                         *  如果设置的大于5秒，则剩下的线程都不能获取锁。可以分别试试2s,和8s的情况
                         */
                        Thread.sleep(10000L);
                        DistributedLock.unlock("lockKey");
                        System.out.println("释放锁");
                    } else {
                        System.out.println("idea1: " + Thread.currentThread().getName() + "无法获取锁");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }) .start();
        }
    }
}
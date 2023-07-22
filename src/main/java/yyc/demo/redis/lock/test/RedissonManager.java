package yyc.demo.redis.lock.test;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;


public class RedissonManager {
    private static RedissonClient redisson;
    static {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setDatabase(0)
                .setConnectionMinimumIdleSize(5)
                .setConnectionPoolSize(20)
                .setRetryAttempts(3)
                .setRetryInterval(1500);
        redisson = Redisson.create(config);
    }

    /**
     * 获取Redisson的实例对象
     * @return
     */
    public static RedissonClient getRedisson(){ return redisson;}
}
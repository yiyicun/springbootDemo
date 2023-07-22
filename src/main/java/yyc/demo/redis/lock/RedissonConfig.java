package yyc.demo.redis.lock;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//redisson配置类
@Configuration
public class RedissonConfig {

    /**
     * 配置一个临时的对象到spring容器中，不使用
     * @return 一个RedissonClient的实现
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()  //单例REDIS
                .setAddress("redis://127.0.0.1:6379")
                .setDatabase(0)
                .setConnectionMinimumIdleSize(5)
                .setConnectionPoolSize(20)
                .setRetryAttempts(3)
                .setRetryInterval(1500);
        return Redisson.create(config);

    }

}

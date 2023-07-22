package yyc.demo.redis.lock;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import yyc.demo.exceptions.ComBizException;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
/**
 * 切面类
 */
@Aspect
@Component
public class LockAspect {
    @Autowired
    private RedissonClient redissonClient;
    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    @Around("@annotation(yyc.demo.redis.lock.Lock)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object object = null;
        RLock lock = null;
        try {
            // 获取注解实体信息
            Lock lockEntity = (((MethodSignature) proceedingJoinPoint.getSignature()).getMethod())
                    .getAnnotation(Lock.class);
            // 根据名字获取锁实例
            lock = redissonClient.getLock(getKeyBySpeL(lockEntity.key(), proceedingJoinPoint));
            if (Objects.nonNull(lock)) {
                // maxSleepMills 获取锁等待时间   keepMills 执行锁最大运行时间
                if (lock.tryLock(lockEntity.maxSleepMills(), lockEntity.keepMills(), TimeUnit.SECONDS)) {
                    object = proceedingJoinPoint.proceed();
                } else {
                    throw new ComBizException("1111","获取锁失败");
                }
            }
        } finally {
            //锁存在 且 是获取锁的线程才能释放锁
            if (Objects.nonNull(lock) && lock.isHeldByCurrentThread()  ) {
                lock.unlock();
                System.out.println("释放锁");
            }
        }
        return object;
    }
    /**
     * 获取缓存的key
     *
     * key 定义在注解上，支持SPEL表达式
     *
     * @return
     */
    public String getKeyBySpeL(String spel, ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String[] paramNames = defaultParameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return String.valueOf(spelExpressionParser.parseExpression(spel).getValue(context));
    }
}

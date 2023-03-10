package com.gugumin.components;

import com.gugumin.config.Config;
import com.gugumin.utils.SystemProxyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * The type Proxy aspect.
 *
 * @author minmin
 * @date 2023 /03/09
 */
@Component
@Aspect
public class ProxyAspect {
    private final Config config;

    /**
     * Instantiates a new Proxy aspect.
     *
     * @param config the config
     */
    public ProxyAspect(Config config) {
        this.config = config;
    }

    @Pointcut("execution(* com.gugumin.service.impl.GitServiceImpl.*(..))")
    public void git() {
    }

    /**
     * Proxy object.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("git()")
    public Object proxy(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Config.Proxy configProxy = config.getProxy();
        if (configProxy.isOpen()) {
            SystemProxyUtil.setHttpProxy(configProxy.getHost(), configProxy.getPort());
            Object proceed = proceedingJoinPoint.proceed();
            SystemProxyUtil.removeHttpProxy();
            return proceed;
        }
        return proceedingJoinPoint.proceed();
    }
}

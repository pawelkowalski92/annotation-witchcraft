package com.github.pawelkowalski92.annotationwitchcraft.magic.configuration;

import com.github.pawelkowalski92.annotationwitchcraft.magic.interceptors.TimingLoggerInterceptor;
import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.LogusTimingus;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

@Configuration(proxyBeanMethods = false)
public class LogusTimingusSpellConfiguration {

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public PointcutAdvisor logusTimingusSpellAdvisor() {
        Pointcut pointcut = new AnnotationMatchingPointcut(null, LogusTimingus.class, true);
        Advice advice = new TimingLoggerInterceptor();
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}

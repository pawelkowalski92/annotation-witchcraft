package com.github.pawelkowalski92.annotationwitchcraft.magic.configuration;

import com.github.pawelkowalski92.annotationwitchcraft.magic.interceptors.EnlargingInterceptor;
import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.Engorgio;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class EngorgioSpellConfiguration {

    @Bean
    public PointcutAdvisor engorgioSpellAdvisor() {
        Pointcut pointcut = new AnnotationMatchingPointcut(null, Engorgio.class, true);
        Advice advice = new EnlargingInterceptor();
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
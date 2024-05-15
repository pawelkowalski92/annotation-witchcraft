package com.github.pawelkowalski92.annotationwitchcraft.magic.configuration;

import com.github.pawelkowalski92.annotationwitchcraft.magic.interceptors.ExpelliarmusProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Configuration(proxyBeanMethods = false)
public class ExpelliarmusSpellConfiguration {

    @Bean
    public static BeanPostProcessor expelliarmusSpellPostProcessor() {
        return new ExpelliarmusProxyPostProcessor();
    }

    static class ExpelliarmusProxyPostProcessor implements InstantiationAwareBeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            Class<?> beanClass = bean.getClass();
            if (Proxy.isProxyClass(beanClass)) {
                return bean;
            }
            ClassLoader classLoader = beanClass.getClassLoader();
            Class<?>[] interfaces = beanClass.getInterfaces();
            if (interfaces.length == 0) {
                return bean;
            }
            InvocationHandler handler = new ExpelliarmusProxy(bean);
            return Proxy.newProxyInstance(classLoader, interfaces, handler);
        }

    }

}

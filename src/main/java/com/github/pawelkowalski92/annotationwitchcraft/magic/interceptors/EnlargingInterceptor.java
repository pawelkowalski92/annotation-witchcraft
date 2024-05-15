package com.github.pawelkowalski92.annotationwitchcraft.magic.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class EnlargingInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result instanceof String str) {
            return str.toUpperCase();
        }
        throw new UnsupportedOperationException(
                "Method: %s needs to return object of type: %s".formatted(invocation.getMethod().getName(), String.class)
        );
    }

}

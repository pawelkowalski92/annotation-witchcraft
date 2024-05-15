package com.github.pawelkowalski92.annotationwitchcraft.magic.interceptors;

import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.LogusTimingus;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.core.annotation.AnnotationUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class TimingLoggerInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Instant start = Instant.now();
        try {
            return invocation.proceed();
        } finally {
            Instant end = Instant.now();
            targetLogger(invocation).atLevel(loggingLevel(invocation)).log(
                    "Execution of method: '{}' with arguments: '{}' took: {}",
                    invocation.getMethod().getName(),
                    Arrays.toString(invocation.getArguments()),
                    Duration.between(start, end)
            );
        }
    }

    private Logger targetLogger(MethodInvocation invocation) {
        Object target = Objects.requireNonNull(invocation.getThis(), "Invocation target must not be null");
        return LoggerFactory.getLogger(target.getClass());
    }

    private Level loggingLevel(MethodInvocation invocation) {
        LogusTimingus timing = AnnotationUtils.findAnnotation(invocation.getMethod(), LogusTimingus.class);
        return timing != null ? timing.loggingLevel() : Level.INFO;
    }

}

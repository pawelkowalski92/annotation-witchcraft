package com.github.pawelkowalski92.annotationwitchcraft.magic;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AutoProxyRegistrar.class)
public @interface EnableSpellcasting {

    AdviceMode mode() default AdviceMode.PROXY;
    boolean proxyTargetClass() default false;

}

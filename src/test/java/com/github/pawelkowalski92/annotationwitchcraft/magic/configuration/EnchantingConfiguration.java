package com.github.pawelkowalski92.annotationwitchcraft.magic.configuration;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EnchantingConfiguration {

    @Bean
    public static DefaultAdvisorAutoProxyCreator enchanter() {
        return new DefaultAdvisorAutoProxyCreator();
    }

}

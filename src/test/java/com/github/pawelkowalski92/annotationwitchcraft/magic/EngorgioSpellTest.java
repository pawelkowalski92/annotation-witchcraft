package com.github.pawelkowalski92.annotationwitchcraft.magic;

import com.github.pawelkowalski92.annotationwitchcraft.domain.Greeter;
import com.github.pawelkowalski92.annotationwitchcraft.magic.configuration.EnchantingConfiguration;
import com.github.pawelkowalski92.annotationwitchcraft.magic.configuration.EngorgioSpellConfiguration;
import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.Engorgio;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class EngorgioSpellTest {

    @Test
    public void shouldBecomeJDKDynamicProxy(@Autowired Greeter greeter) {
        assertThat(greeter).matches(AopUtils::isJdkDynamicProxy, "is JDK dynamic proxy");
    }

    @Test
    public void shouldBecomeCGLIBProxy(@Autowired SalaryProvider salaryProvider) {
        assertThat(salaryProvider).matches(AopUtils::isCglibProxy, "is CGLIB (class) proxy");
    }

    @Test
    public void shouldMegaGreet(@Autowired Greeter greeter) throws Exception {
        // given
        String name = "Semisiu";

        // when
        String greetings = greeter.greet(name);

        // then
        assertThat(greetings).isEqualTo("HELLO, MY FRIEND SEMISIU ❤");
    }

    @Test
    public void shouldThrowExceptionForNotSupportedReturnType(@Autowired SalaryProvider salaryProvider) {
        // when
        ThrowingCallable nextPayout = salaryProvider::nextMonthPayout;

        // then
        assertThatThrownBy(nextPayout).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Method: nextMonthPayout needs to return object of type: class java.lang.String");
    }

    static class MegaGreeter implements Greeter {

        @Engorgio
        public String greet(String name) {
            return "Hello, my friend %s ❤".formatted(name);
        }

    }

    static class SalaryProvider {

        @Engorgio // because who doesn't want to get more money
        public BigDecimal nextMonthPayout() {
            return BigDecimal.valueOf(3.50D);
        }

    }

    @Configuration
    @Import({EnchantingConfiguration.class, EngorgioSpellConfiguration.class})
    static class TestConfiguration {

        @Bean
        public Greeter megaGreeter() {
            return new MegaGreeter();
        }

        @Bean
        public SalaryProvider salaryProvider() {
            return new SalaryProvider();
        }

    }

}

package com.github.pawelkowalski92.annotationwitchcraft.magic;

import com.github.pawelkowalski92.annotationwitchcraft.domain.Greeter;
import com.github.pawelkowalski92.annotationwitchcraft.magic.configuration.ExpelliarmusSpellConfiguration;
import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.Expelliarmus;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ExpelliarmusSpellTest {

    @Test
    public void shouldGreetShortName(@Autowired Greeter greeter) throws Exception {
        // given
        String name = "Semiś";

        // when
        String greetings = greeter.greet(name);

        // then
        assertThat(greetings).isEqualTo("Hello, my friend Semiś ❤");
    }

    @Test
    public void shouldReturnNullOnLongName(@Autowired Greeter greeter) throws Exception {
        // given
        String name = "Semisiu";

        // when
        String greetings = greeter.greet(name);

        // then
        assertThat(greetings).isNull();
    }

    @Test
    public void shouldThrowExceptionAnywayForStubbornOne(@Autowired JDKProxyStubbornGreeter greeter) {
        // given
        String name = "Semisiu";

        // when
        ThrowingCallable greetings = () -> greeter.greet(name);

        // then
        assertThatThrownBy(greetings).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("I don't want to greet Semisiu");
    }


    static class ShortGreeter implements Greeter {

        @Override
        @Expelliarmus(exceptions = IndexOutOfBoundsException.class)
        public String greet(String name) {
            if (name.length() <= 5) {
                return "Hello, my friend %s ❤".formatted(name);
            }
            throw new IndexOutOfBoundsException(name.length() - 5);
        }

    }

    static class JDKProxyStubbornGreeter {

        @Expelliarmus(exceptions = UnsupportedOperationException.class)
        public String greet(String name) {
            throw new UnsupportedOperationException("I don't want to greet %s".formatted(name));
        }

    }

    @Configuration(proxyBeanMethods = false)
    @Import(ExpelliarmusSpellConfiguration.class)
    static class TestConfiguration {

        @Bean
        public Greeter shortGreeter() {
            return new ShortGreeter();
        }

        @Bean
        public JDKProxyStubbornGreeter stubbornGreeter() {
            return new JDKProxyStubbornGreeter();
        }

    }

}

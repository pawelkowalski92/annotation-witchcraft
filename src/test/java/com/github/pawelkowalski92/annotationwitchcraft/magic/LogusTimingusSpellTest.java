package com.github.pawelkowalski92.annotationwitchcraft.magic;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.github.pawelkowalski92.annotationwitchcraft.domain.Greeter;
import com.github.pawelkowalski92.annotationwitchcraft.magic.configuration.LogusTimingusSpellConfiguration;
import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.LogusTimingus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LogusTimingusSpellTest {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LoggingGreeter.class);
    private ListAppender<ILoggingEvent> testAppender;

    @BeforeEach
    public void setUp() {
        testAppender = new ListAppender<>();
        LOGGER.addAppender(testAppender);
        testAppender.start();
    }

    @AfterEach
    public void tearDown() {
        testAppender.stop();
        LOGGER.detachAppender(testAppender);
    }

    @Test
    public void shouldBecomeCGLIBProxy(@Autowired Greeter greeter) {
        assertThat(greeter).matches(AopUtils::isCglibProxy, "is class (CGLIB) proxy");
    }

    @Test
    public void shouldGreetAndLog(@Autowired Greeter greeter) throws Exception {
        // given
        String name = "Semisiu";

        // when
        String greetings = greeter.greet(name);

        // then
        assertThat(greetings).isEqualTo("Hello, my friend Semisiu ❤");
        assertThat(testAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .first()
                .matches(msg -> msg.startsWith("Execution of method: 'greet' with arguments: '[Semisiu]'"));
    }


    static class LoggingGreeter implements Greeter {

        @LogusTimingus
        public String greet(String name) {
            return "Hello, my friend %s ❤".formatted(name);
        }

    }

    @Configuration
    @EnableSpellcasting(proxyTargetClass = true)
    @Import(LogusTimingusSpellConfiguration.class)
    static class TestConfiguration {

        @Bean
        public Greeter loggingGreeter() {
            return new LoggingGreeter();
        }

    }

}

package com.github.pawelkowalski92.annotationwitchcraft.magic;

import com.github.pawelkowalski92.annotationwitchcraft.domain.Greeter;
import com.github.pawelkowalski92.annotationwitchcraft.magic.configuration.EngorgioSpellConfiguration;
import com.github.pawelkowalski92.annotationwitchcraft.magic.configuration.LogusTimingusSpellConfiguration;
import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.Engorgio;
import com.github.pawelkowalski92.annotationwitchcraft.magic.spells.LogusTimingus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpellComboTest {

    @Test
    public void shouldComboSpells(@Autowired Greeter greeter) throws Exception {
        // given
        String name = "Semisiu";

        // when
        String greetings = greeter.greet(name);

        // then
        assertThat(greetings).isEqualTo("SEMISIU HAS PERFORMED A C-C-COMBO!");
    }


    static class ComboGreeter implements Greeter {

        @Engorgio
        @LogusTimingus
        public String greet(String name) {
            return "%s has performed a c-c-combo!".formatted(name);
        }

    }

    @Configuration
    @EnableSpellcasting
    @Import({EngorgioSpellConfiguration.class, LogusTimingusSpellConfiguration.class})
    static class TestConfiguration {

        @Bean
        public Greeter comboGreeter() {
            return new ComboGreeter();
        }

    }

}

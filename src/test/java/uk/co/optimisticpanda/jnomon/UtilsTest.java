package uk.co.optimisticpanda.jnomon;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.jnomon.Utils.BORDER;
import static uk.co.optimisticpanda.jnomon.Utils.formatLine;
import static uk.co.optimisticpanda.jnomon.Utils.formatSeconds;
import static uk.co.optimisticpanda.jnomon.Utils.since;

import java.time.Duration;

import org.junit.Rule;
import org.junit.Test;

import uk.co.optimisticpanda.jnomon.Utils.Colour;

public class UtilsTest {

    @Rule public FixedClockRule clock = new FixedClockRule();
    
    @Test
    public void checkFormatLine() {
        assertThat(formatLine(5, "cat", "sat on the mat"))
            .isEqualTo("  cat " + BORDER + " sat on the mat");
    
        assertThat(formatLine(1, "cat", "sat on the mat"))
            .isEqualTo("cat " + BORDER + " sat on the mat");

        assertThat(formatLine(20, "cat", "sat on the mat"))
            .isEqualTo("                 cat " + BORDER + " sat on the mat");
    }

    @Test
    public void checkFormatSeconds() {
        assertThat(formatSeconds(Duration.ofMillis(1_234)))
            .isEqualTo("1.234s");
        assertThat(formatSeconds(Duration.ofMillis(-1_234)))
            .isEqualTo("-1.234s");
        assertThat(formatSeconds(Duration.ofMillis(12_345_678)))
            .isEqualTo("12345.678s");
    }
    
    @Test
    public void checkDurationSince() {
           assertThat(since(clock.getCurrentMillis() - 30_000).toMillis())
                .isEqualTo(30_000);
    }
    
    @Test
    public void checkColour() {
        assertThat(Colour.NO_COLOUR.colourize("Hello"))
        .isEqualTo("Hello");
        
        assertThat(Colour.RED_FG.colourize("Hello"))
            .isEqualTo("[31mHello[0m");
    }
}

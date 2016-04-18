package uk.co.optimisticpanda.jnomon;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.GREEN_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.NO_COLOUR;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.RED_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.YELLOW_FG;

import java.time.Duration;

import org.junit.Test;

import uk.co.optimisticpanda.jnomon.Utils.Colour;

public class ColourChooserTest {

    @Test
    public void fullyConfigured() {
        forColourChooserConfig("-h", "4", "-m", "2")
            .whenDurationIs(5).checkColourIs(RED_FG)
            .whenDurationIs(4).checkColourIs(RED_FG)
            .whenDurationIs(3).checkColourIs(YELLOW_FG)
            .whenDurationIs(2).checkColourIs(YELLOW_FG)
            .whenDurationIs(1).checkColourIs(GREEN_FG);
    }
    
    @Test
    public void noHigh() {
        forColourChooserConfig("-m", "3")
            .whenDurationIs(5).checkColourIs(YELLOW_FG)
            .whenDurationIs(4).checkColourIs(YELLOW_FG)
            .whenDurationIs(3).checkColourIs(YELLOW_FG)
            .whenDurationIs(2).checkColourIs(GREEN_FG)
            .whenDurationIs(1).checkColourIs(GREEN_FG);
    }
    
    @Test
    public void noMedium() {
        forColourChooserConfig("-h", "4")
            .whenDurationIs(5).checkColourIs(RED_FG)
            .whenDurationIs(4).checkColourIs(RED_FG)
            .whenDurationIs(3).checkColourIs(GREEN_FG)
            .whenDurationIs(2).checkColourIs(GREEN_FG)
            .whenDurationIs(1).checkColourIs(GREEN_FG);
    }
    
    @Test
    public void checkDefaultColourReturnedWhenNoConfiguration() {
        forColourChooserConfig()
            .whenDurationIs(5).checkColourIs(NO_COLOUR)
            .whenDurationIs(4).checkColourIs(NO_COLOUR)
            .whenDurationIs(3).checkColourIs(NO_COLOUR)
            .whenDurationIs(2).checkColourIs(NO_COLOUR)
            .whenDurationIs(1).checkColourIs(NO_COLOUR);
    }
    
    private ColourAsserter forColourChooserConfig(String... args) {
        return new ColourAsserter(Configuration.read(args).getColourChooser());
    }
    
    private static class ColourAsserter {

        private final ColourChooser chooser;
        private long duration;

        private ColourAsserter(ColourChooser chooser) {
            this.chooser = chooser;
        }
        
        private ColourAsserter whenDurationIs(long duration) {
            this.duration = duration;
            return this;
        }
        
        private ColourAsserter checkColourIs(Colour colour) {
            assertThat(chooser.colourForDuration(Duration.ofMillis(duration))).isEqualTo(colour);
            return this;
        }
    }
}

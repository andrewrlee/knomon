package uk.co.optimisticpanda.jnomon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import uk.co.optimisticpanda.jnomon.formatter.AbsoluteOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedLineOutputWriter;

public class ConfigurationTest {

    @Test
    public void shortCodes() {
        Configuration config = Configuration.read("--help", "-h", "2", "-m", "3", "-r", "100", "-t", "absolute");
        assertThat(config.getHigh()).contains(2);
        assertThat(config.getMedium()).contains(3);
        assertThat(config.getRealTime()).contains(100L);
        assertThat(config.getEventListener()).isInstanceOf(AbsoluteOutputWriter.class);
    }
    
    @Test
    public void longCodes() {
        Configuration config = Configuration.read("--help", "--high", "2", "--medium", "3", "--real-time", "100", "--type", "absolute");
        assertThat(config.getHigh()).contains(2);
        assertThat(config.getMedium()).contains(3);
        assertThat(config.getRealTime()).contains(100L);
        assertThat(config.getEventListener()).isInstanceOf(AbsoluteOutputWriter.class);
        assertThat(config.helpShown()).isTrue();
    }
    
    @Test
    public void defaults() {
        Configuration config = Configuration.read();
        assertThat(config.getHigh()).isEmpty();
        assertThat(config.getMedium()).isEmpty();
        assertThat(config.getRealTime()).contains(500L);
        assertThat(config.getEventListener()).isInstanceOf(ElapsedLineOutputWriter.class);
        assertThat(config.helpShown()).isFalse();
    }
    
    @Test
    public void realTimeParsing() {
        assertThat(Configuration.read("--real-time", "200").getRealTime()).contains(200L);
        assertThat(Configuration.read("--real-time", "false").getRealTime()).isEmpty();

        assertThatThrownBy(() -> 
            Configuration.read("--real-time", "Boingloings! In the Boingloings!").getRealTime())
                .isInstanceOf(NumberFormatException.class)
                .hasMessage("For input string: \"Boingloings! In the Boingloings!\"");
    }
}

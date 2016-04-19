package uk.co.optimisticpanda.jnomon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import uk.co.optimisticpanda.jnomon.formatter.AbsoluteOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedLineOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedTotalOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.EventListener;

import com.beust.jcommander.ParameterException;

public class EventListenerConverterTest {

    @Test
    public void checkValidOptions() {
        assertThat(listenerFor("elapsed-line")).isInstanceOf(ElapsedLineOutputWriter.class);
        assertThat(listenerFor("elapsed-total")).isInstanceOf(ElapsedTotalOutputWriter.class);
        assertThat(listenerFor("absolute")).isInstanceOf(AbsoluteOutputWriter.class);
    }
    
    @Test
    public void checkFailureCase() {
        assertThatThrownBy(() -> listenerFor("boingloings!"))
            .isInstanceOf(ParameterException.class)
            .hasMessage("\"event-listener-converter\": couldn't convert \"boingloings!\" to an EventListener (values: [elapsed-line, elapsed-total, absolute])");
    }

    private EventListener listenerFor(String option) {
        return new EventListenerConverter("event-listener-converter").convert(option);
    }
}

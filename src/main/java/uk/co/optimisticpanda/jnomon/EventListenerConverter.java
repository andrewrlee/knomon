package uk.co.optimisticpanda.jnomon;

import static java.util.Arrays.asList;

import java.util.List;

import uk.co.optimisticpanda.jnomon.formatter.AbsoluteOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedLineOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedTotalWriter;
import uk.co.optimisticpanda.jnomon.formatter.EventListener;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

public class EventListenerConverter extends BaseConverter<EventListener> {

    public EventListenerConverter(String optionName) {
        super(optionName);
    }

    @Override
        public EventListener convert(String label) {
            switch (label.toLowerCase().trim()) {
            case "elapsed-line" : {
                return new ElapsedLineOutputWriter();   
            }
            case "elapsed-total": {
                return new ElapsedTotalWriter();
            }
            case "absolute": {
                return new AbsoluteOutputWriter();
            }
            default: {
                List<String> options = asList("elapsed-line", "elapsed-total", "absolute");
                String message = getErrorString(label, "a TimestampType (values: " + options + ")");
                throw new ParameterException(message);
            }
        }
    }
}

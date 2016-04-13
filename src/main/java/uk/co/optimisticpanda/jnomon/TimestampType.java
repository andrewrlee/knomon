package uk.co.optimisticpanda.jnomon;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import uk.co.optimisticpanda.jnomon.formatter.AbsoluteOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedLineOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedTotalWriter;
import uk.co.optimisticpanda.jnomon.formatter.EventListener;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

public enum TimestampType {
    ELAPSED_LINE("elapsed-line", ElapsedLineOutputWriter::new), 
    ELAPSED_TOTAL("elapsed-total", ElapsedTotalWriter::new), 
    ABSOLUTE("absolute", AbsoluteOutputWriter::new);
    
    private final String optionName;
    private Supplier<EventListener> eventListenerFactory;

    private TimestampType(final String optionName, final Supplier<EventListener> eventListenerFactory){
        this.optionName = optionName;
        this.eventListenerFactory = eventListenerFactory;
    }
    
    public Supplier<EventListener> getEventListenerFactory() {
        return eventListenerFactory;
    }
    
    private static Optional<TimestampType> fromOption(String option) {
        return stream(TimestampType.values())
                .filter(t -> option.trim().equalsIgnoreCase(t.optionName))
                .findFirst();
    }
    
    public static class TimestampTypeConverter extends BaseConverter<TimestampType> {
        
        private static final List<String> OPTION_NAMES = stream(TimestampType.values())
                .map(TimestampType::name).collect(toList());

        public TimestampTypeConverter(String optionName) {
            super(optionName);
        }

        @Override
        public TimestampType convert(String label) {
                String message = getErrorString(label, "a TimestampType (values: " + OPTION_NAMES + ")");
                return fromOption(label).orElseThrow(() -> new ParameterException(message));
        }
    }
}

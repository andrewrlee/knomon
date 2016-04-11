package uk.co.optimisticpanda.jnomon;

import static java.util.Arrays.stream;

import java.util.Optional;

import uk.co.optimisticpanda.jnomon.formatter.AbsoluteOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedLineOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.ElapsedTotalOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.OutputWriter.OutputWriterBuilder;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

enum TimestampType {
    ELAPSED_LINE("elapsed-line", ElapsedLineOutputWriter::new), 
    ELAPSED_TOTAL("elapsed_total", ElapsedTotalOutputWriter::new), 
    ABSOLUTE("absolute", AbsoluteOutputWriter::new);
    
    private final String optionName;
    private OutputWriterBuilder formatterBuilder;

    private TimestampType(final String optionName, final OutputWriterBuilder formatterBuilder){
        this.optionName = optionName;
        this.formatterBuilder = formatterBuilder;
    }
    
    public OutputWriterBuilder getFormatterBuilder() {
        return formatterBuilder;
    }
    
    private static Optional<TimestampType> fromOption(String option) {
        return stream(TimestampType.values())
                .filter(t -> option.trim().equalsIgnoreCase(t.optionName))
                .findFirst();
    }
    
    static class TimestampTypeConverter extends BaseConverter<TimestampType> {
        
        TimestampTypeConverter(String optionName) {
            super(optionName);
        }

        @Override
        public TimestampType convert(String label) {
                return fromOption(label).orElseThrow(() -> 
                    new ParameterException(getErrorString(label, "a TimestampType")));
        }
    }
}

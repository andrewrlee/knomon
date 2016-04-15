package uk.co.optimisticpanda.jnomon;

import java.util.Optional;

import uk.co.optimisticpanda.jnomon.formatter.ElapsedLineOutputWriter;
import uk.co.optimisticpanda.jnomon.formatter.EventListener;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

class Configuration {

    static Configuration read(final String... args) {
        Configuration result = new Configuration();
        JCommander commander = new JCommander(result, args);
        if (result.help) {
            commander.usage();
        }
        return result;
    }

    @Parameter(names = "--help", help = true,
            description = "Show usage")
    private boolean help;
    
    @Parameter(names = { "-h", "--high" }, 
            description = "If the time is >= than this, in millis, then red highlight will be applied.")
    private Integer high;

    @Parameter(names = { "-m", "--medium" }, 
            description = "If the time is >= than this, in millis, then yellow highlight will be applied.")
    private Integer medium;

    @Parameter(names = { "-r", "--real-time" },
            description = "Increment to use when updating timestamp, in millis. `false` disables realtime entirely.")
    private String realTime = "500";

    @Parameter(names = { "-t", "--type" },
            converter = EventListenerConverter.class,
            description = "Timestamp type to display:"
            + "\n\t'elapsed-line': time since last line"
            + "\n\t'elapsed-total': time since start of the process"
            + "\n\t'absolute': Absolute timestamp in UTC")
    private EventListener eventListener = new ElapsedLineOutputWriter();
    
    Optional<Integer> getHigh() {
        return Optional.ofNullable(high);
    }

    Optional<Integer> getMedium() {
        return Optional.ofNullable(medium);
    }

    Optional<Long> getRealTime() {
        return Optional.of(realTime).filter(t -> !t.equalsIgnoreCase("false")).map(Long::parseLong);
    }
    
    EventListener getEventListener() {
        return eventListener;
    }
    
    ColourChooser getColourChooser() {
        return ColourChooser.newConfigBasedColourChooser(this);
    }
    
    boolean helpShown() {
        return help;
    }
    
}

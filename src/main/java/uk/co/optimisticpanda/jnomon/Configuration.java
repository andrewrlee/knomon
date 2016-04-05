package uk.co.optimisticpanda.jnomon;

import java.util.Optional;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Configuration {

    public static Configuration read(final String... args) {
        Configuration result = new Configuration();
        new JCommander(result, args);
        return result;
    }
    
    @Parameter(names = { "-h", "--high" }, 
            description = "If the time is >= than this, in millis, then red highlight will be applied.")
    private Integer high;
    
    @Parameter(names = { "-m", "--medium" }, 
            description = "If the time is >= than this, in millis, then yellow highlight will be applied.")
    private Integer medium;

    public Optional<Integer> getHigh() {
        return Optional.ofNullable(high);
    }

    public Optional<Integer> getMedium() {
        return Optional.ofNullable(medium);
    }
    
}

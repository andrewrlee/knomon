package uk.co.optimisticpanda.jnomon;

import java.util.Optional;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

class Configuration {

    static Configuration read(final String... args) {
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

    @Parameter(names = { "-r", "--real-time" },
            description = "Increment to use when updating timestamp, in millis. `false` disables realtime entirely.")
    private String realTime = "500";

    Optional<Integer> getHigh() {
        return Optional.ofNullable(high);
    }

    Optional<Integer> getMedium() {
        return Optional.ofNullable(medium);
    }

    Optional<Long> getRealTime() {
        return Optional.of(realTime).filter(t -> !t.equalsIgnoreCase("false")).map(Long::parseLong);
    }
}

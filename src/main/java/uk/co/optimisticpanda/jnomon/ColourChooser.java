package uk.co.optimisticpanda.jnomon;

import static uk.co.optimisticpanda.jnomon.Utils.Colour.GREEN_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.RED_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.NO_COLOUR;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.YELLOW_FG;
import uk.co.optimisticpanda.jnomon.Utils.Colour;

public interface ColourChooser {

    Colour colourForDuration(long elapsedTime); 
    
    static ColourChooser newConfigBasedColourChooser(final Configuration config) {
        return elapsedTime -> {
            if (config.getHigh().filter(level -> elapsedTime >= level).isPresent()) {
                return RED_FG;
            } else if (config.getMedium().filter(level -> elapsedTime >= level).isPresent()) {
                return YELLOW_FG;
            } else if (config.getHigh().isPresent() || config.getMedium().isPresent()){
                return GREEN_FG;
            }
            return NO_COLOUR;
        };
    }
}

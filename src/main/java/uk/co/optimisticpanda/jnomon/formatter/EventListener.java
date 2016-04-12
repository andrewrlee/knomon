package uk.co.optimisticpanda.jnomon.formatter;

import uk.co.optimisticpanda.jnomon.ColourChooser;

public interface EventListener {
    
    default void onBefore(long processStartTime, long lastStartTime, String line) {
        // NO-OP
    }

    default void onUpdate(long processStartTime, long currentStartTime) {
        // NO-OP
    }

    default void onEnd() {
        // NO-OP
    }
    
    public interface EventListenerFactory {
        EventListener build(ColourChooser colourChooser);
    }
    
}

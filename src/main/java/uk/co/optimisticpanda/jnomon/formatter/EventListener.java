package uk.co.optimisticpanda.jnomon.formatter;

import java.time.Duration;

import uk.co.optimisticpanda.jnomon.ColourChooser;

public interface EventListener {
    
    default void onBeforeAll() {
        // NO-OP
    }
    
    default void onLineStart(Duration sinceProcessStart, Duration sinceLastStart, String line) {
        // NO-OP
    }

    default void onUpdate(Duration sinceProcessStart, Duration sinceLastStart) {
        // NO-OP
    }

    default void onLineEnd(Duration sinceProcessStart, Duration sinceLastStart, String lastLine) {
        // NO-OP
    }

    default void onFinally(Duration sinceProcessStart, Duration sinceLastStart) {
        // NO-OP
    }
    
    public interface EventListenerFactory {
        EventListener build(ColourChooser colourChooser);
    }
}

package uk.co.optimisticpanda.jnomon.formatter;

import uk.co.optimisticpanda.jnomon.ColourChooser;

public interface EventListener {
    
    public void onBefore(long processStartTime, long lastStartTime, String line);
    
    public void onUpdate(long processStartTime, long currentStartTime);

    default void onEnd() {
        System.out.println();
    }
    
    public interface EventListenerFactory {
        EventListener build(ColourChooser colourChooser);
    }
    
}

package uk.co.optimisticpanda.jnomon.formatter;

import uk.co.optimisticpanda.jnomon.ColourChooser;

public interface OutputWriter {
    
    public void onBefore(long processStartTime, long lastStartTime, String line);
    
    public void onUpdate(long processStartTime, long currentStartTime);

    default void onEnd() {
        System.out.println();
    }
    
    public interface OutputWriterBuilder {
        OutputWriter build(ColourChooser colourChooser);
    }
    
}

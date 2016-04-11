package uk.co.optimisticpanda.jnomon.formatter;

import uk.co.optimisticpanda.jnomon.ColourChooser;

public class AbsoluteOutputWriter implements OutputWriter {

    private ColourChooser colourChooser;

    public AbsoluteOutputWriter(ColourChooser colourChooser) {
        this.colourChooser = colourChooser;
    }

    @Override
    public void onBefore(long processStartTime, long lastStartTime, String line) {
    }
    
    @Override
    public void onUpdate(long processStartTime, long currentStartTime) {
    }
}

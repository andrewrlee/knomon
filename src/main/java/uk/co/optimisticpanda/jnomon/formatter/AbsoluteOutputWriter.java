package uk.co.optimisticpanda.jnomon.formatter;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static uk.co.optimisticpanda.jnomon.Utils.BORDER;

import java.time.Instant;

import uk.co.optimisticpanda.jnomon.ColourChooser;
import uk.co.optimisticpanda.jnomon.Utils.Colour;

public class AbsoluteOutputWriter implements EventListener {
    private ColourChooser colourChooser;

    public AbsoluteOutputWriter(ColourChooser colourChooser) {
        this.colourChooser = colourChooser;
    }

    @Override
    public void onBefore(long processStartTime, long lastStartTime, String line) {
        onUpdate(lastStartTime, lastStartTime);
        System.out.printf("%24s %s %s\n", " ", BORDER, line);
    }
    
    @Override
    public void onUpdate(long processStartTime, long currentStartTime) {
        String text = format("%24s %s", Instant.now().toString(), BORDER);
        Colour colour = colourChooser.colourForDuration(currentTimeMillis() - currentStartTime);
        System.out.print("\033[1A\r" + colour.colourize(text) + "\n");
    }
}
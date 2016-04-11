package uk.co.optimisticpanda.jnomon.formatter;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.WHITE_BG;

import java.text.DecimalFormat;

import uk.co.optimisticpanda.jnomon.ColourChooser;

public class ElapsedLineOutputWriter implements OutputWriter {
    private static final String BORDER = WHITE_BG.colourize(" ");
    private static final DecimalFormat FORMAT =  new DecimalFormat("0.000");
    private final ColourChooser colourChooser;
    
    public ElapsedLineOutputWriter(ColourChooser colourChooser) {
        this.colourChooser = colourChooser;
    }
    
    @Override
    public void onBefore(long processStartTime, long lastStartTime, String line) {
        onUpdate(processStartTime, lastStartTime);
        System.out.printf("\n%8s  %s %s", "", BORDER, line);
    }
    
    @Override
    public void onUpdate(long processStartTime, long currentStartTime) {
        long elapsed = currentTimeMillis() - currentStartTime;
        String text = format("%8ss ", FORMAT.format((elapsed) / 1000d));
        System.out.print("\r" + colourChooser.colourForDuration(elapsed).colourize(text));
    }
}

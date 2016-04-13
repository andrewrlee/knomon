package uk.co.optimisticpanda.jnomon.formatter;

import static uk.co.optimisticpanda.jnomon.Utils.START_OF_PREV_LINE;
import static uk.co.optimisticpanda.jnomon.Utils.formatLine;
import static uk.co.optimisticpanda.jnomon.Utils.formatSeconds;

import java.time.Duration;

import uk.co.optimisticpanda.jnomon.Utils.Colour;

public class ElapsedLineOutputWriter implements EventListener {
    
    @Override
    public void onLineStart(Duration sinceProcessStart, Duration sinceLastStart, String line) {
        onUpdate(null, sinceProcessStart, sinceLastStart);
        System.out.println(formatLine(8, "", line));
    }
    
    @Override
    public void onUpdate(Colour colour, Duration sinceProcessStart, Duration sinceLastStart) {
        String text = formatLine(8, formatSeconds(sinceLastStart), "");
        System.out.println(START_OF_PREV_LINE + colour.colourize(text));
    }
    
    @Override
    public void onLineEnd(Duration sinceProcessStart, Duration sinceLastStart, String lastLine) {
        System.out.println(formatLine(8, formatSeconds(sinceLastStart), lastLine));
    }
    
    @Override
    public void onFinally(Duration sinceProcessStart, Duration sinceLastStart) {
        System.out.println(formatLine(8, "Total:", formatSeconds(sinceProcessStart)));
    }
}
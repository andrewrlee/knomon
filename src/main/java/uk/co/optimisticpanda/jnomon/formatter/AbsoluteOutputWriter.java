package uk.co.optimisticpanda.jnomon.formatter;

import static uk.co.optimisticpanda.jnomon.Utils.MOVE_TO_START_OF_PREV_LINE;
import static uk.co.optimisticpanda.jnomon.Utils.formatLine;
import static uk.co.optimisticpanda.jnomon.Utils.formatSeconds;

import java.time.Duration;
import java.time.Instant;

import uk.co.optimisticpanda.jnomon.Utils.Colour;

public class AbsoluteOutputWriter implements EventListener {

    @Override
    public void onLineStart(Colour colour, Duration sinceProcessStart, Duration sinceLastStart, String line) {
        onUpdate(colour, sinceProcessStart, sinceLastStart);
        System.out.println(formatLine(24, "", line));
    }
    
    @Override
    public void onUpdate(Colour colour, Duration sinceProcessStart, Duration sinceLastStart) {
        String text = formatLine(24, Instant.now().toString(), "");
        System.out.println(MOVE_TO_START_OF_PREV_LINE + colour.colourize(text));
    }
    
    @Override
    public void onLineEnd(Duration sinceProcessStart, Duration sinceLastStart, String lastLine) {
        System.out.println(formatLine(24, Instant.now().toString(), lastLine));
    }
    
    @Override
    public void onFinally(Duration sinceProcessStart, Duration sinceLastStart) {
        System.out.println(formatLine(24, "Total:", formatSeconds(sinceProcessStart)));
    }
}
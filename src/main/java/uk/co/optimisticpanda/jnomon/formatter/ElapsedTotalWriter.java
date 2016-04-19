package uk.co.optimisticpanda.jnomon.formatter;

import static uk.co.optimisticpanda.jnomon.Utils.MOVE_TO_START_OF_PREV_LINE;
import static uk.co.optimisticpanda.jnomon.Utils.formatLine;
import static uk.co.optimisticpanda.jnomon.Utils.formatSeconds;

import java.time.Duration;

import uk.co.optimisticpanda.jnomon.Utils.Colour;

public class ElapsedTotalWriter implements EventListener{

    @Override
    public void onLineStart(Colour colour, Duration sinceProcessStart, Duration sinceLastStart, String line) {
        onUpdate(colour, sinceProcessStart, sinceLastStart);
        printer().println(formatLine(8, "", line));
    }
    
    @Override
    public void onUpdate(Colour colour, Duration sinceProcessStart, Duration sinceLastStart) {
        String text = formatLine(8, formatSeconds(sinceProcessStart), "");
        printer().print(MOVE_TO_START_OF_PREV_LINE + colour.colourize(text) + "\n");
    }

    @Override
    public void onLineEnd(Duration sinceProcessStart, Duration sinceLastStart, String lastLine) {
        printer().println(formatLine(8, formatSeconds(sinceProcessStart), lastLine));
    }
    
    @Override
    public void onFinally(Duration sinceProcessStart, Duration sinceLastStart) {
        printer().println(formatLine(8, "Total:", formatSeconds(sinceProcessStart)));
    }
}

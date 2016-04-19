package uk.co.optimisticpanda.jnomon.formatter;

import java.time.Duration;
import java.util.Optional;

import uk.co.optimisticpanda.jnomon.ColourChooser;
import uk.co.optimisticpanda.jnomon.Utils.Colour;

public class EventListenerAdapter {

    private final EventListener eventListener;
    private final boolean realTime;
    private String lastLine;
    private ColourChooser colourChooser;

    public EventListenerAdapter(ColourChooser colourChooser, EventListener eventListener, boolean realTime) {
        this.colourChooser = colourChooser;
        this.eventListener = eventListener;
        this.realTime = realTime;
    }

    public void onBeforeAll() {
        if (realTime) {
            eventListener.onBeforeAll();
        }
    }
    
    public void onLineStart(Duration sinceProcessStart, Duration sinceLastStart, String line) {
        if (realTime) {
            Colour colour = colourChooser.colourForDuration(sinceLastStart);
            eventListener.onLineStart(colour, sinceProcessStart, sinceLastStart, line);
        } else {
            eventListener.onLineEnd(sinceProcessStart, sinceLastStart, lastLine());
        }
        this.lastLine = line;
    }
    
    public void onUpdate(Duration sinceProcessStart, Duration sinceLastStart) {
        if (realTime) {
            Colour colour = colourChooser.colourForDuration(sinceLastStart);
            eventListener.onUpdate(colour, sinceProcessStart, sinceLastStart);
        }
    }
    
    public void onFinally(Duration sinceProcessStart, Duration sinceLastStart) {
        if (!realTime) {
            eventListener.onLineEnd(sinceProcessStart, sinceLastStart, lastLine());
        }
        eventListener.onFinally(sinceProcessStart, sinceLastStart);
    }

    String lastLine() {
        return Optional.ofNullable(lastLine).orElse("");
    }
}

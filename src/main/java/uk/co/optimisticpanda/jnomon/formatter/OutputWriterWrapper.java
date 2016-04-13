package uk.co.optimisticpanda.jnomon.formatter;

import java.time.Duration;
import java.util.Optional;

public class OutputWriterWrapper implements EventListener {

    private final EventListener eventListener;
    private final boolean realTime;
    private String lastLine;

    public OutputWriterWrapper(EventListener eventListener, boolean realTime) {
        this.eventListener = eventListener;
        this.realTime = realTime;
    }

    @Override
    public void onBeforeAll() {
        if (realTime) {
            System.out.println();
        }
    }
    
    @Override
    public void onLineStart(Duration sinceProcessStart, Duration sinceLastStart, String line) {
        if (realTime) {
            eventListener.onLineStart(sinceProcessStart, sinceLastStart, line);
        } else {
            eventListener.onLineEnd(sinceProcessStart, sinceLastStart, getLastLine());
        }
        this.lastLine = line;
    }
    
    @Override
    public void onUpdate(Duration sinceProcessStart, Duration sinceLastStart) {
        if (realTime) {
            eventListener.onUpdate(sinceProcessStart, sinceLastStart);
        }
    }
    
    @Override
    public void onFinally(Duration sinceProcessStart, Duration sinceLastStart) {
        if (!realTime) {
            eventListener.onLineEnd(sinceProcessStart, sinceLastStart, getLastLine());
        }
        eventListener.onFinally(sinceProcessStart, sinceLastStart);
    }
    
    private String getLastLine() {
        return Optional.ofNullable(lastLine).orElse("");
    }
}

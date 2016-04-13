package uk.co.optimisticpanda.jnomon.formatter;

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
    public void onLineStart(long processStartTime, long lastStartTime, String line) {
        if (realTime) {
            eventListener.onLineStart(processStartTime, lastStartTime, line);
        } else {
            eventListener.onLineEnd(processStartTime, lastStartTime, getLastLine());
        }
        this.lastLine = line;
    }
    
    @Override
    public void onUpdate(long processStartTime, long currentStartTime) {
        if (realTime) {
            eventListener.onUpdate(processStartTime, currentStartTime);
        }
    }
    
    @Override
    public void onFinally(long processStartTime, long currentStartTime) {
        if (!realTime) {
            eventListener.onLineEnd(processStartTime, currentStartTime, getLastLine());
        }
        eventListener.onFinally(processStartTime, currentStartTime);
    }
    
    private String getLastLine() {
        return Optional.ofNullable(lastLine).orElse("");
    }
}

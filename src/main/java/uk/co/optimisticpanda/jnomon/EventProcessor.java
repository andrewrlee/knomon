package uk.co.optimisticpanda.jnomon;

import static uk.co.optimisticpanda.jnomon.Utils.now;
import static uk.co.optimisticpanda.jnomon.Utils.since;

import java.time.Duration;

import rx.functions.Action1;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Event.LineEvent;
import uk.co.optimisticpanda.jnomon.Event.QuitEvent;
import uk.co.optimisticpanda.jnomon.Event.TickEvent;
import uk.co.optimisticpanda.jnomon.formatter.EventListenerAdapter;

class EventProcessor implements Action1<Event> {
    private final PublishSubject<Integer> stopper;
    private final EventListenerAdapter eventListener;
    private long start = now(), processStart = now();

    EventProcessor(PublishSubject<Integer> stopper, EventListenerAdapter eventListener) {
        this.stopper = stopper;
        this.eventListener = eventListener;
        eventListener.onBeforeAll();
    }
    
    @Override
    public void call(Event step) {
        Duration sinceProcessStart = since(processStart);
        Duration sinceLastStart = since(start);
        
        if (step instanceof TickEvent) {
            eventListener.onUpdate(sinceProcessStart, sinceLastStart);
        } else if (step instanceof QuitEvent) {
            stopper.onCompleted();
            eventListener.onFinally(sinceProcessStart, sinceLastStart);
        } else {
            eventListener.onLineStart(sinceProcessStart, sinceLastStart, ((LineEvent) step).getLine());
            start = now();
        }
    }
}
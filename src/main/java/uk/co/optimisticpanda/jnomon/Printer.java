package uk.co.optimisticpanda.jnomon;

import static java.lang.System.currentTimeMillis;
import static uk.co.optimisticpanda.jnomon.Utils.since;

import java.time.Duration;

import rx.functions.Action1;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Step.LineStep;
import uk.co.optimisticpanda.jnomon.Step.QuitStep;
import uk.co.optimisticpanda.jnomon.Step.TickStep;
import uk.co.optimisticpanda.jnomon.Utils.Colour;
import uk.co.optimisticpanda.jnomon.formatter.EventListener;
import uk.co.optimisticpanda.jnomon.formatter.OutputWriterWrapper;

class Printer implements Action1<Step> {
    private final PublishSubject<Integer> stopper;
    private long start = currentTimeMillis(), processStart = currentTimeMillis();
    private EventListener eventListener;
    private ColourChooser colourChooser;

    Printer(PublishSubject<Integer> stopper, Configuration config) {
        this.stopper = stopper;
        this.eventListener = new OutputWriterWrapper(config.getEventListener(), config.getRealTime().isPresent());
        this.colourChooser = config.getColourChooser();
        eventListener.onBeforeAll();
    }
    
    @Override
    public void call(Step step) {
        Duration sinceProcessStart = since(processStart);
        Duration sinceLastStart = since(start);
        Colour colour = colourChooser.colourForDuration(sinceLastStart);
        
        if (step instanceof TickStep) {
            eventListener.onUpdate(colour, sinceProcessStart, sinceLastStart);
        } else if (step instanceof QuitStep) {
            stopper.onCompleted();
            eventListener.onFinally(sinceProcessStart, sinceLastStart);
        } else {
            eventListener.onLineStart(colour, sinceProcessStart, sinceLastStart, ((LineStep) step).getLine());
            start = Utils.clock.millis();
        }
    }
}
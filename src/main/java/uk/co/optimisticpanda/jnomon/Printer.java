package uk.co.optimisticpanda.jnomon;

import static java.lang.System.currentTimeMillis;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Step.LineStep;
import uk.co.optimisticpanda.jnomon.Step.QuitStep;
import uk.co.optimisticpanda.jnomon.Step.TickStep;
import uk.co.optimisticpanda.jnomon.formatter.EventListener;

class Printer implements Action1<Step> {
    private final PublishSubject<Integer> stopper;
    private long start = currentTimeMillis(), processStart = currentTimeMillis();
    private EventListener eventListener;

    Printer(PublishSubject<Integer> stopper, Configuration configuration) {
        this.stopper = stopper;
        this.eventListener = configuration.getEventListener();
        System.out.println();
    }
    
    @Override
    public void call(Step step) {
        if (step instanceof TickStep) {
            eventListener.onUpdate(processStart, start);
        } else if (step instanceof QuitStep) {
            stopper.onCompleted();
            eventListener.onEnd();
        } else {
            eventListener.onBefore(processStart, start, ((LineStep) step).getLine());
            start = currentTimeMillis();
        }
    }
}
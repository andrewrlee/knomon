package uk.co.optimisticpanda.jnomon;

import static java.lang.System.currentTimeMillis;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Step.LineStep;
import uk.co.optimisticpanda.jnomon.Step.QuitStep;
import uk.co.optimisticpanda.jnomon.Step.TickStep;
import uk.co.optimisticpanda.jnomon.formatter.OutputWriter;

class Printer implements Action1<Step> {
    private final PublishSubject<Integer> stopper;
    private long start = currentTimeMillis(), processStart = currentTimeMillis();
    private OutputWriter outputWriter;

    Printer(PublishSubject<Integer> stopper, Configuration configuration) {
        this.stopper = stopper;
        this.outputWriter = configuration.getOutputWriter();
    }
    
    @Override
    public void call(Step step) {
        if (step instanceof TickStep) {
            outputWriter.onUpdate(processStart, start);
        } else if (step instanceof QuitStep) {
            stopper.onCompleted();
            outputWriter.onEnd();
        } else {
            outputWriter.onBefore(processStart, start, ((LineStep) step).getLine());
            start = currentTimeMillis();
        }
    }
}
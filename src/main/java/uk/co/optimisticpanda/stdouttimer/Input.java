package uk.co.optimisticpanda.stdouttimer;

import static java.lang.System.currentTimeMillis;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.interval;
import static rx.Observable.just;
import static rx.schedulers.Schedulers.newThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.observables.BlockingObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.stdouttimer.Input.Step.LineStep;
import uk.co.optimisticpanda.stdouttimer.Input.Step.QuitStep;
import uk.co.optimisticpanda.stdouttimer.Input.Step.TickStep;

public class Input {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            PublishSubject<Integer> stopper = PublishSubject.create();

            Observable<Step> lines = from(reader)
                    .<Step> map(LineStepImpl::new)
                    .subscribeOn(Schedulers.io())
                    .concatWith(just(new QuitStep()));

            Observable<Step> ticks = interval(100, MILLISECONDS)
                    .<Step> map(TickStepImpl::new)
                    .takeUntil(stopper)
                    .observeOn(newThread());

            Observable<Step> combinedSteps = Observable.merge(lines, ticks);
            BlockingObservable.from(combinedSteps).subscribe(new Printer(stopper));
        }
    }

    private static class Printer implements Action1<Step> {
        private static final String BORDER = "\u001B[47m \u001B[0m";
        private final PublishSubject<Integer> stopper;
        private Long start = currentTimeMillis();

        public Printer(PublishSubject<Integer> stopper) {
            this.stopper = stopper;
        }

        private void printTimeSince(long start) {
            System.out.printf("\r   %.4fs %s ", ((currentTimeMillis() - start) / 1000f), BORDER);
        }
        
        @Override
        public void call(Step step) {
            if (step instanceof TickStep) {
                printTimeSince(start);
            } else if (step instanceof QuitStep) {
                stopper.onCompleted();
                System.out.println();
            } else {
                printTimeSince(start);
                System.out.printf("\n           %s %s", BORDER, ((LineStep) step).getLine());
                start = currentTimeMillis();
            }
        }
    }

    interface Step {
        @Dto @Immutable
        public static interface TickStep extends Step {
            Long getTick();
        }

        public static class QuitStep implements Step {
        }

        @Dto @Immutable
        public static interface LineStep extends Step {
            String getLine();
        }
    }

    public static Observable<String> from(final BufferedReader reader) {
        return Observable.create((Subscriber<? super String> subscriber) -> {
            try {
                String line;
                while (!subscriber.isUnsubscribed() && (line = reader.readLine()) != null) {
                    subscriber.onNext(line);
                }
            } catch (IOException e) {
                subscriber.onError(e);
            }
            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });
    }
    
    @Target({PACKAGE, TYPE})
    @Retention(CLASS) 
    @Style(allParameters = true, of = "new", typeImmutable = "*Impl", defaults = @Immutable(builder = false))
    public @interface Dto {}
}

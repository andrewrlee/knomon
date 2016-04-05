package uk.co.optimisticpanda.jnomon;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.interval;
import static rx.Observable.just;
import static rx.schedulers.Schedulers.newThread;
import static uk.co.optimisticpanda.jnomon.Utils.observableFrom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rx.Observable;
import rx.observables.BlockingObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Step.QuitStep;

public class Main {

    public static void main(String[] args) throws IOException {

        Configuration configuration = Configuration.read(args);
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            PublishSubject<Integer> stopper = PublishSubject.create();

            Observable<Step> lines = observableFrom(reader)
                    .<Step> map(LineStepImpl::new)
                    .subscribeOn(Schedulers.io())
                    .concatWith(just(new QuitStep()));

            Observable<Step> ticks = interval(100, MILLISECONDS)
                    .<Step> map(TickStepImpl::new)
                    .takeUntil(stopper)
                    .observeOn(newThread());

            Observable<Step> combinedSteps = Observable.merge(lines, ticks);
            BlockingObservable.from(combinedSteps).subscribe(new Printer(stopper, configuration));
        }
    }

}

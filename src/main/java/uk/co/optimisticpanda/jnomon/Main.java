package uk.co.optimisticpanda.jnomon;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.empty;
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
import uk.co.optimisticpanda.jnomon.Event.QuitEvent;
import uk.co.optimisticpanda.jnomon.formatter.EventListenerAdapter;

public class Main {

    public static void main(String[] args) throws IOException {

        Configuration config = Configuration.read(args);
        
        if (config.helpShown()) {
            return;
        }
        EventListenerAdapter eventListener = new EventListenerAdapter(
                config.getColourChooser(), config.getEventListener(), config.getRealTime().isPresent());
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            PublishSubject<Integer> stopper = PublishSubject.create();

            Observable<Event> lines = observableFrom(reader)
                    .<Event> map(LineEventImpl::new)
                    .subscribeOn(Schedulers.io())
                    .concatWith(just(new QuitEvent()));

            Observable<Event> ticks = config.getRealTime()
                    .map(time -> interval(time, MILLISECONDS)).orElse(empty())
                    .<Event> map(TickEventImpl::new)
                    .takeUntil(stopper)
                    .observeOn(newThread());

            Observable<Event> combinedSteps = Observable.merge(lines, ticks);
            BlockingObservable.from(combinedSteps).subscribe(new Printer(stopper, eventListener));
        }
    }
}

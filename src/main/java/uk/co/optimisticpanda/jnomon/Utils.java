package uk.co.optimisticpanda.jnomon;

import java.io.BufferedReader;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

class Utils {

    enum Colour {
        WHITE_BG(47),
        WHITE_FG(37),
        RED_FG(31), 
        GREEN_FG(32), 
        YELLOW_FG(33);

        private final int code;

        private Colour(final int code) {
            this.code = code;
        }
        
        public String colourize(final String text) {
            return  "\u001B[" + code + "m" + text + "\u001B[0m";
        }
    }
    
    static Observable<String> observableFrom(final BufferedReader reader) {
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
}

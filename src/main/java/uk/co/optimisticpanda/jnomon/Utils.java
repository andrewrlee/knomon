package uk.co.optimisticpanda.jnomon;

import static java.lang.System.currentTimeMillis;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.WHITE_BG;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Clock;
import java.time.Duration;

import rx.Observable;
import rx.Subscriber;

public class Utils {

    private static final DecimalFormat SECONDS_FORMAT =  new DecimalFormat("0.000s");
    static final String BORDER = WHITE_BG.colourize(" ");
    public static final String MOVE_TO_START_OF_PREV_LINE = "\033[1A\r";
    static Clock clock = Clock.systemDefaultZone();
    
    public enum Colour {
        WHITE_BG(47),
        RED_FG(31), 
        GREEN_FG(32), 
        YELLOW_FG(33),
        NO_COLOUR(-1) {
            @Override
            public String colourize(String text) {
                return text;
            }
        };

        private final int code;

        private Colour(final int code) {
            this.code = code;
        }
        
        public String colourize(final String text) {
            return  "\u001B[" + code + "m" + text + "\u001B[0m";
        }
    }
    
    public static String formatLine(int marginWidth, String left, String right) {
        return String.format("%" + marginWidth+ "s %s %s", left, BORDER, right);
    }

    public static Duration since(long millis) {
        return Duration.ofMillis(clock.millis() - millis);
    }
    
    public static String formatSeconds(Duration duration) {
        return SECONDS_FORMAT.format(duration.toMillis() / 1000d);
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

package uk.co.optimisticpanda.jnomon;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.GREEN_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.RED_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.WHITE_BG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.WHITE_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.YELLOW_FG;

import java.text.DecimalFormat;

import rx.functions.Action1;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Step.LineStep;
import uk.co.optimisticpanda.jnomon.Step.QuitStep;
import uk.co.optimisticpanda.jnomon.Step.TickStep;
import uk.co.optimisticpanda.jnomon.Utils.Colour;

class Printer implements Action1<Step> {
    private static final String BORDER = WHITE_BG.colourize(" ");
    private static final DecimalFormat FORMAT =  new DecimalFormat("0.000");
    private final PublishSubject<Integer> stopper;
    private Long start = currentTimeMillis();
    private Configuration configuration;

    Printer(PublishSubject<Integer> stopper, Configuration configuration) {
        this.stopper = stopper;
        this.configuration = configuration;
    }
    
    private void printTimeSince(long start) {
        long millis = currentTimeMillis() - start;
        String text = format("%8ss %s ", FORMAT.format((millis) / 1000d), BORDER);
        System.out.print("\r" + colourForDuration(millis).colourize(text));
    }

    private Colour colourForDuration(long millis) {
        Colour colour = WHITE_FG;
        if (configuration.getHigh().filter(level -> millis >= level).isPresent()) {
            colour = RED_FG;
        } else if (configuration.getMedium().filter(level -> millis >= level).isPresent()) {
            colour = YELLOW_FG;
        } else if (configuration.getHigh().isPresent() || configuration.getMedium().isPresent()){
            colour = GREEN_FG;
        }
        return colour;
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
            System.out.printf("\n%8s  %s %s", "", BORDER, ((LineStep) step).getLine());
            start = currentTimeMillis();
        }
    }
}
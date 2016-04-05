package uk.co.optimisticpanda.jnomon;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.GREEN_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.RED_FG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.WHITE_BG;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.YELLOW_FG;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Step.LineStep;
import uk.co.optimisticpanda.jnomon.Step.QuitStep;
import uk.co.optimisticpanda.jnomon.Step.TickStep;
import uk.co.optimisticpanda.jnomon.Utils.Colour;

class Printer implements Action1<Step> {
    private final PublishSubject<Integer> stopper;
    private Long start = currentTimeMillis();
    private Configuration configuration;

    public Printer(PublishSubject<Integer> stopper, Configuration configuration) {
        this.stopper = stopper;
        this.configuration = configuration;
    }

    private void printTimeSince(long start) {
        long millis = currentTimeMillis() - start;
        String text = format("   %.4fs %s ", Double.valueOf((millis) / 1000d), WHITE_BG.colourize(" "));
        System.out.print("\r" + colourForDuration(millis).colourize(text));
    }

    private Colour colourForDuration(long millis) {
        Colour colour = Colour.WHITE_FG;
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
            System.out.printf("\n           %s %s", WHITE_BG.colourize(" "), ((LineStep) step).getLine());
            start = currentTimeMillis();
        }
    }
}
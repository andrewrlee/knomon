package uk.co.optimisticpanda.jnomon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Event.LineEvent;
import uk.co.optimisticpanda.jnomon.Event.QuitEvent;
import uk.co.optimisticpanda.jnomon.Event.TickEvent;
import uk.co.optimisticpanda.jnomon.formatter.EventListenerAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventprocessorTest {

    @Mock
    private EventListenerAdapter eventListener;
    private PublishSubject<Integer> stopper;
    private EventProcessor eventProcessor;

    @Before
    public void setUp() throws Exception {
        stopper = PublishSubject.create();
        eventProcessor = new EventProcessor(stopper, eventListener);
    }

    @Test
    public void onBeforeCalled() {
        verify(eventListener).onBeforeAll();
    }

    @Test
    public void quitStepPublishesStopEvent() {
        eventProcessor.call(QuitEvent.INSTANCE);
        verify(eventListener).onFinally(any(), any());
        assertThat(stopper.hasCompleted()).isTrue();
    }

    @Test
    public void handlesTickEvents() {
        eventProcessor.call(new TickEvent(1L));
        verify(eventListener).onUpdate(any(), any());
    }

    @Test
    public void handlesLineEvents() {
        eventProcessor.call(new LineEvent("A line"));
        verify(eventListener).onLineStart(any(), any(), eq("A line"));
    }
}

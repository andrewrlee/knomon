package uk.co.optimisticpanda.jnomon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.subjects.PublishSubject;
import uk.co.optimisticpanda.jnomon.Event.QuitEvent;
import uk.co.optimisticpanda.jnomon.formatter.EventListenerAdapter;

@RunWith(MockitoJUnitRunner.class)
public class EventprocessorTest {

    @Mock private EventListenerAdapter eventListener;
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
        eventProcessor.call(new QuitEvent());
        verify(eventListener).onFinally(any(), any());
        assertThat(stopper.hasCompleted()).isTrue();
    }

    @Test
    public void handlesTickEvents() {
        eventProcessor.call(new TickEventImpl(1L));
        verify(eventListener).onUpdate(any(), any());
    }

    @Test
    public void handlesLineEvents() {
        eventProcessor.call(new LineEventImpl("A line"));
        verify(eventListener).onLineStart(any(), any(), eq("A line"));
    }
}

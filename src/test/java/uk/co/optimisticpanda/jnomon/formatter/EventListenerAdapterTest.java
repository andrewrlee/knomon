package uk.co.optimisticpanda.jnomon.formatter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.optimisticpanda.jnomon.ColourChooser;
import uk.co.optimisticpanda.jnomon.FixedClockRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.GREEN_FG;
import static uk.co.optimisticpanda.jnomon.Utils.since;

@RunWith(MockitoJUnitRunner.class)
public class EventListenerAdapterTest {
    @Rule
    public FixedClockRule clock = new FixedClockRule();
    @Mock
    private ColourChooser colourChooser;
    @Mock
    private EventListener eventListener;
    private EventListenerAdapter realTimeEventListenerAdapter;
    private EventListenerAdapter disabledRealTimeEventListenerAdapter;

    @Before
    public void setUp() throws Exception {
        realTimeEventListenerAdapter = new EventListenerAdapter(colourChooser, eventListener, true);
        disabledRealTimeEventListenerAdapter = new EventListenerAdapter(colourChooser, eventListener, false);
    }

    @Test
    public void onBeforeAllforRealTimeListener() {
        realTimeEventListenerAdapter.onBeforeAll();
        verify(eventListener).onBeforeAll();
    }

    @Test
    public void onBeforeAllWhenRealTimeIsDisabled() {
        disabledRealTimeEventListenerAdapter.onBeforeAll();
        verify(eventListener, never()).onBeforeAll();
    }

    @Test
    public void onLineStartForRealTime() {
        when(colourChooser.colourForDuration(since(200))).thenReturn(GREEN_FG);
        realTimeEventListenerAdapter.onLineStart(since(100), since(200), "hello");
        verify(eventListener).onLineStart(GREEN_FG, since(100), since(200), "hello");
        assertThat(realTimeEventListenerAdapter.lastLine()).isEqualTo("hello");
    }

    @Test
    public void onLineStartWhenRealTimeIsDisabled() {
        disabledRealTimeEventListenerAdapter.onLineStart(since(100), since(200), "hello");
        verify(eventListener).onLineEnd(since(100), since(200), "");
        assertThat(disabledRealTimeEventListenerAdapter.lastLine()).isEqualTo("hello");
    }

    @Test
    public void onUpdateForRealTime() {
        when(colourChooser.colourForDuration(since(200))).thenReturn(GREEN_FG);
        realTimeEventListenerAdapter.onUpdate(since(100), since(200));
        verify(eventListener).onUpdate(GREEN_FG, since(100), since(200));
    }

    @Test
    public void onUpdateWhenRealTimeIsDisabled() {
        disabledRealTimeEventListenerAdapter.onUpdate(since(100), since(200));
        verify(eventListener, never()).onUpdate(any(), any(), any());
    }

    @Test
    public void onFinallyForRealTime() {
        realTimeEventListenerAdapter.onFinally(since(100), since(200));
        verify(eventListener).onFinally(since(100), since(200));
    }

    @Test
    public void onFinallyWhenRealTimeIsDisabled() {
        disabledRealTimeEventListenerAdapter.onFinally(since(100), since(200));
        verify(eventListener).onLineEnd(since(100), since(200), "");
        verify(eventListener).onFinally(since(100), since(200));
    }
}

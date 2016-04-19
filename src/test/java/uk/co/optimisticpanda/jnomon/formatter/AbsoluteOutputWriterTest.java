package uk.co.optimisticpanda.jnomon.formatter;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.co.optimisticpanda.jnomon.Utils.since;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.RED_FG;

import java.io.PrintStream;
import java.time.Instant;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.optimisticpanda.jnomon.FixedClockRule;

@RunWith(MockitoJUnitRunner.class)
public class AbsoluteOutputWriterTest {

    @Rule public FixedClockRule clock = new FixedClockRule(Instant.parse("2007-12-03T10:15:30.00Z"));
    @Mock private PrintStream printStream;
    private AbsoluteOutputWriter outputWriter;

    @Before
    public void setUp() throws Exception {
        outputWriter = spy(new AbsoluteOutputWriter());
        when(outputWriter.printer()).thenReturn(printStream);
    }

    @Test
    public void onBeforeAll() {
        outputWriter.onBeforeAll();
        verify(printStream).println();
        verifyNoMoreInteractions(printStream);
    }
    
    @Test
    public void onLineStart() {
        outputWriter.onLineStart(RED_FG, since(100L), since(200L), "This is the line");
        verify(printStream).println("[1A\r" + RED_FG.colourize("    2007-12-03T10:15:30Z [47m [0m "));
        verify(printStream).println("                         [47m [0m This is the line");
        verifyNoMoreInteractions(printStream);
    }
    
    @Test
    public void onUpdate() {
        outputWriter.onUpdate(RED_FG, since(100L), since(200L));
        verify(printStream).println("[1A\r" + RED_FG.colourize("    2007-12-03T10:15:30Z [47m [0m "));
        verifyNoMoreInteractions(printStream);
    }
    
    @Test
    public void onLineEnd() {
        outputWriter.onLineEnd(since(100L), since(200L), "This is the line");
        verify(printStream).println("    2007-12-03T10:15:30Z [47m [0m This is the line");
        verifyNoMoreInteractions(printStream);
    }

    @Test
    public void onFinally() {
        outputWriter.onFinally(since(100L), since(200L));
        verify(printStream).println("                  Total: [47m [0m 1196676929.900s");
        verifyNoMoreInteractions(printStream);
    }
}

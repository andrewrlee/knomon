package uk.co.optimisticpanda.jnomon.formatter;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.co.optimisticpanda.jnomon.Utils.now;
import static uk.co.optimisticpanda.jnomon.Utils.since;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.RED_FG;

import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.optimisticpanda.jnomon.FixedClockRule;

@RunWith(MockitoJUnitRunner.class)
public class ElapsedLineOutputWriterTest {

    @Rule public FixedClockRule clock = new FixedClockRule(Instant.parse("2007-12-03T10:15:30.00Z"));
    @Mock private PrintStream printStream;
    private ElapsedLineOutputWriter outputWriter;
    private Duration sinceProcessStart, sinceLastStart;

    @Before
    public void setUp() throws Exception {
        outputWriter = spy(new ElapsedLineOutputWriter());
        when(outputWriter.printer()).thenReturn(printStream);
        sinceProcessStart = since(now() - 2234);
        sinceLastStart = since(now() - 1234);
    }

    @Test
    public void onBeforeAll() {
        outputWriter.onBeforeAll();
        verify(printStream).println();
        verifyNoMoreInteractions(printStream);
    }
    
    @Test
    public void onLineStart() {
        outputWriter.onLineStart(RED_FG, sinceProcessStart, sinceLastStart, "This is the line");
        verify(printStream).println("[1A\r" + RED_FG.colourize("  1.234s [47m [0m "));
        verify(printStream).println("         [47m [0m This is the line");
        verifyNoMoreInteractions(printStream);
    }
    
    @Test
    public void onUpdate() {
        outputWriter.onUpdate(RED_FG, sinceProcessStart, sinceLastStart);
        verify(printStream).println("[1A\r" + RED_FG.colourize("  1.234s [47m [0m "));
        verifyNoMoreInteractions(printStream);
    }
    
    @Test
    public void onLineEnd() {
        outputWriter.onLineEnd(sinceProcessStart, sinceLastStart, "This is the line");
        verify(printStream).println("  1.234s [47m [0m This is the line");
        verifyNoMoreInteractions(printStream);
    }

    @Test
    public void onFinally() {
        outputWriter.onFinally(sinceProcessStart, sinceLastStart);
        verify(printStream).println("  Total: [47m [0m 2.234s");
        verifyNoMoreInteractions(printStream);
    }
}

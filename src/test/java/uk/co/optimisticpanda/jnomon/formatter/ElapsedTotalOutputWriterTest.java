package uk.co.optimisticpanda.jnomon.formatter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.optimisticpanda.jnomon.FixedClockRule;

import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.co.optimisticpanda.jnomon.Utils.Colour.RED_FG;
import static uk.co.optimisticpanda.jnomon.Utils.now;
import static uk.co.optimisticpanda.jnomon.Utils.since;

@RunWith(MockitoJUnitRunner.class)
public class ElapsedTotalOutputWriterTest {

    @Rule
    public FixedClockRule clock = new FixedClockRule(Instant.parse("2007-12-03T10:15:30.00Z"));
    @Mock
    private PrintStream printStream;
    private ElapsedTotalOutputWriter outputWriter;
    private Duration sinceProcessStart, sinceLastStart;

    @Before
    public void setUp() throws Exception {
        outputWriter = spy(new ElapsedTotalOutputWriter());
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
        verify(printStream).println("         [47m [0m This is the line");
        verify(printStream).print("[1A\r" + RED_FG.colourize("  2.234s [47m [0m ") + "\n");
        verifyNoMoreInteractions(printStream);
    }

    @Test
    public void onUpdate() {
        outputWriter.onUpdate(RED_FG, sinceProcessStart, sinceLastStart);
        verify(printStream).print("[1A\r" + RED_FG.colourize("  2.234s [47m [0m ") + "\n");
        verifyNoMoreInteractions(printStream);
    }

    @Test
    public void onLineEnd() {
        outputWriter.onLineEnd(sinceProcessStart, sinceLastStart, "This is the line");
        verify(printStream).println("  2.234s [47m [0m This is the line");
        verifyNoMoreInteractions(printStream);
    }

    @Test
    public void onFinally() {
        outputWriter.onFinally(sinceProcessStart, sinceLastStart);
        verify(printStream).println("  Total: [47m [0m 2.234s");
        verifyNoMoreInteractions(printStream);
    }
}

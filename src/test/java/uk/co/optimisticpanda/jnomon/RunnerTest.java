package uk.co.optimisticpanda.jnomon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class RunnerTest {

    @Test
    public void runRunner() throws IOException {
        Runner runner = new Runner();
        StringReader reader = new StringReader("1\n2\n3\n4\n");
        runner.run(Configuration.read(), new BufferedReader(reader));
    }
}

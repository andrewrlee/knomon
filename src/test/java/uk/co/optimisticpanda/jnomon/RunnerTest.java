package uk.co.optimisticpanda.jnomon;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
public class RunnerTest {

    @Test
    public void checkUsage() throws IOException {
        StringReader reader = new StringReader("1\n2\n3\n4\n");
        assertThat(new Runner().run(Configuration.read("--help"), new BufferedReader(reader))).isFalse();
    }
    
    @Test
    public void runRunner() throws IOException {
        StringReader reader = new StringReader("1\n2\n3\n4\n");
        assertThat(new Runner().run(Configuration.read(), new BufferedReader(reader))).isTrue();
    }
}

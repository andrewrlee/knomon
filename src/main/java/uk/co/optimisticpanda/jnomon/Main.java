package uk.co.optimisticpanda.jnomon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration config = Configuration.read(args);
        new Runner().run(config, new BufferedReader(new InputStreamReader(System.in)));
    }
}

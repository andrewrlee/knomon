package uk.co.optimisticpanda.jnomon;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.rules.ExternalResource;

public class FixedClockRule extends ExternalResource {

    private Instant instant;

    public FixedClockRule() {
        this(Instant.now());
    }
    
    public FixedClockRule(Instant instant) {
        this.instant = instant;
    }
    
    @Override
    protected void before() throws Throwable {
        Utils.clock = Clock.fixed(instant, ZoneOffset.UTC);
    }
    
    @Override
    protected void after() {
        Utils.clock = Clock.systemDefaultZone();
    }
    
    protected Long getCurrentMillis() {
        return Utils.clock.millis();
    }
    
}

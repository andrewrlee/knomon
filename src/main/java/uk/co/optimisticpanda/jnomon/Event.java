package uk.co.optimisticpanda.jnomon;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

interface Event {
    
    @Target({ PACKAGE, TYPE })
    @Retention(CLASS)
    @Style(allParameters = true, of = "new", typeImmutable = "*Impl", defaults = @Immutable(builder = false))
    @interface Dto {
    }

    @Dto @Immutable
    interface TickEvent extends Event {
        Long getTick();
    }

    static class QuitEvent implements Event {
    }

    @Dto @Immutable
    interface LineEvent extends Event {
        String getLine();
    }
}
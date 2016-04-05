package uk.co.optimisticpanda.jnomon;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

interface Step {
    
    @Target({ PACKAGE, TYPE })
    @Retention(CLASS)
    @Style(allParameters = true, of = "new", typeImmutable = "*Impl", defaults = @Immutable(builder = false))
    @interface Dto {
    }

    @Dto @Immutable
    interface TickStep extends Step {
        Long getTick();
    }

    static class QuitStep implements Step {
    }

    @Dto @Immutable
    interface LineStep extends Step {
        String getLine();
    }
}
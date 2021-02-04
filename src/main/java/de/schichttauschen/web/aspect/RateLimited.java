// $Id\$
package de.schichttauschen.web.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimited {

    long requests() default 1;

    Interval interval() default Interval.Seconds;

    enum Interval {
        Seconds,
        Minutes,
        Hours
    }

}

package org.algobench.discovery;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BenchmarkSettings {
    int warmupIterations() default 3;

    int measurementIterations() default 5;

    int forks() default 5;

    int[] sizes() default {1000};
}

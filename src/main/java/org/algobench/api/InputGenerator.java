package org.algobench.api;
@FunctionalInterface
public interface ResultValidator<I> {
    I generateInput(BenchmarkContext context) throws Exception;
}

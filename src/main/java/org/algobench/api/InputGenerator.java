package org.algobench.api;

@FunctionalInterface
public interface InputGenerator<I> {
    I generateInput(BenchmarkContext context) throws Exception;
}
package org.algobench.api;

@FunctionalInterface
public interface ResultValidator<R, T> {
    void validate(R input, T output) throws Exception;
}

package org.algobench.api;

public interface BenchmarkLifecycle <I>{
    default void beforeAll() throws Exception {}
    default void beforeEach(I input) throws Exception {}
    default void afterEach(I input) throws Exception {}
    default void afterAll() throws Exception {}
}

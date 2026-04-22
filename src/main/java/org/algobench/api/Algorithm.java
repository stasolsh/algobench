package org.algobench.api;

public interface Algorithm<T, K> {
    String name();

    T run(K input) throws Exception;
}

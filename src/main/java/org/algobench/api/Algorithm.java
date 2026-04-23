package org.algobench.api;

public interface Algorithm<K, L> {
    String name();

    L run(K input) throws Exception;
}

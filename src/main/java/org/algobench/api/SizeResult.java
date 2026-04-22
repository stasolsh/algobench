package org.algobench.api;

import java.util.List;

public final class SizeResult {
    private final int size;
    private final List<Long> timingsNanos;

    public SizeResult(int size, List<Long> timingsNanos) {
        this.size = size;
        this.timingsNanos = List.copyOf(timingsNanos);
    }

    public int getSize() {
        return size;
    }

    public List<Long> getTimingsNanos() {
        return timingsNanos;
    }
}

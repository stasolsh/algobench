package org.algobench.api;

import java.util.List;

public record SizeResult(int size, List<Long> timingsNanos) {
    public SizeResult(int size, List<Long> timingsNanos) {
        this.size = size;
        this.timingsNanos = List.copyOf(timingsNanos);
    }
}

package org.algobench.api;

import java.util.List;

public final class BenchmarkConfig {
    private final int warmupIterations;
    private final int measurementIterations;
    private final int forks;
    private final List<Integer> sizes;
    private final boolean copyInputPerRan;

    private BenchmarkConfig(Builder builder) {
        this.warmupIterations = builder.warmapIterations;
        this.measurementIterations = builder.measuredIterations;
        this.forks = builder.forks;
        this.sizes = List.copyOf(builder.sizes);
        this.copyInputPerRan = builder.copyInputPerRan;
    }

    private static final class Builder {
        private int warmapIterations = 5;
        private int measuredIterations = 10;
        private int forks = 1;
        private List<Integer> sizes = List.of(1000);
        private boolean copyInputPerRan = false;

        public Builder warmupIterations(int warmupIterations) {
            this.warmapIterations = warmupIterations;
            return this;
        }

        public Builder measurementIterations(int measurementIterations) {
            this.measuredIterations = measurementIterations;
            return this;
        }

        public Builder forks(int forks) {
            this.forks = forks;
            return this;
        }

        public Builder sizes(List<Integer> sizes) {
            this.sizes = sizes;
            return this;
        }

        public Builder copyInputPerRan(boolean copyInputPerRan) {
            this.copyInputPerRan = copyInputPerRan;
            return this;
        }

        public BenchmarkConfig build() {
            return new BenchmarkConfig(this);
        }
    }
}

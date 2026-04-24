package org.algobench.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Benchmark<K, L> {
    private final String name;
    private final List<Algorithm<K, L>> algorithms;
    private BenchmarkConfig config;
    private final InputGenerator<K> inputGenerator;
    private final ResultValidator<K, L> validator;
    private final BenchmarkLifecycle<K> lifecycle;

    private Benchmark(Builder<K, L> builder) {
        this.name = builder.name;
        this.algorithms = List.copyOf(builder.algorithms);
        this.config = builder.config;
        this.inputGenerator = builder.inputGenerator;
        this.validator = builder.validator;
        this.lifecycle = builder.lifecycle;
    }

    public String getName() {
        return name;
    }

    public List<Algorithm<K, L>> getAlgorithms() {
        return algorithms;
    }

    public BenchmarkConfig getConfig() {
        return config;
    }

    public InputGenerator<K> getInputGenerator() {
        return inputGenerator;
    }

    public ResultValidator<K, L> getValidator() {
        return validator;
    }

    public BenchmarkLifecycle<K> getLifecycle() {
        return lifecycle;
    }

    public static <K, L> Builder<K, L> builder(String name) {
        return new Builder<>(name);
    }

    public static final class Builder<K, L> {
        private final String name;
        private final List<Algorithm<K, L>> algorithms = new ArrayList<>();
        private BenchmarkConfig config = BenchmarkConfig.builder().build();
        private InputGenerator<K> inputGenerator;
        private ResultValidator<K, L> validator = (input, output) -> {
        };
        private BenchmarkLifecycle<K> lifecycle = new BenchmarkLifecycle<K>() {
        };

        private Builder(String name) {
            this.name = Objects.requireNonNull(name);
        }

        public Builder<K, L> addAlgorithm(Algorithm<K, L> algorithm) {
            this.algorithms.add(Objects.requireNonNull(algorithm));
            return this;
        }

        public Builder<K, L> addAlgorithms(List<Algorithm<K, L>> algorithms) {
            this.algorithms.addAll(algorithms);
            return this;
        }

        public Builder<K, L> config(BenchmarkConfig config) {
            this.config = Objects.requireNonNull(config);
            return this;
        }

        public Builder<K, L> inputGenerator(InputGenerator<K> inputGenerator) {
            this.inputGenerator = Objects.requireNonNull(inputGenerator);
            return this;
        }

        public Builder<K, L> validator(ResultValidator<K, L> validator) {
            this.validator = Objects.requireNonNull(validator);
            return this;
        }

        public Builder<K, L> lifecycle(BenchmarkLifecycle<K> lifecycle) {
            this.lifecycle = Objects.requireNonNull(lifecycle);
            return this;
        }

        public Benchmark<K, L> build() {
            if (algorithms.isEmpty()) {
                throw new IllegalArgumentException("At list one algorithm is required");
            }

            if (inputGenerator == null) {
                throw new IllegalArgumentException("Input generator is required");
            }

            return new Benchmark<>(this);
        }
    }
}

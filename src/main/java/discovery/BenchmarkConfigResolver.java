package discovery;

import org.algobench.api.BenchmarkConfig;

import java.util.Arrays;

public final class BenchmarkConfigResolver {
    private BenchmarkConfigResolver() {
    }

    public static BenchmarkConfig resolve(Class<?> benchmarkClass) {
        BenchmarkSettings settings = benchmarkClass.getAnnotation(BenchmarkSettings.class);
        if (settings == null) {
            return BenchmarkConfig.builder().build();
        }
        return BenchmarkConfig.builder()
                .warmupIterations(settings.warmupIterations())
                .measurementIterations(settings.measurementIterations())
                .forks(settings.forks())
                .sizes(Arrays.stream(settings.sizes()).boxed().toList())
                .build();
    }
}

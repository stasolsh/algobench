package org.algobench.examples.sorting;

import org.algobench.discovery.AlgorithmScanner;
import org.algobench.discovery.BenchmarkConfigResolver;
import org.algobench.discovery.BenchmarkSettings;
import org.algobench.api.Benchmark;
import org.algobench.api.InputGenerator;
import org.algobench.api.ResultValidator;
import org.algobench.report.ConsoleReportFormatter;
import org.algobench.runner.DefaultBenchmarkRunner;

import java.util.Random;


@BenchmarkSettings(
        warmupIterations = 3,
        measurementIterations = 5,
        forks = 2,
        sizes = {100, 1000, 3000}
)
public class SortingExample {

    public static void main(String[] args) throws Exception {
        Benchmark<int[], int[]> benchmark = Benchmark.<int[], int[]>builder("Sorting Benchmark")
                .addAlgorithms(
                        AlgorithmScanner.findAlgorithms(
                                "org.algobench.examples.sorting",
                                AbstractIntArraySortAlgorithm.class)
                )
                .inputGenerator(getInputGenerator())
                .validator(getResultValidator())
                .config(BenchmarkConfigResolver.resolve(SortingExample.class))
                .build();

        var result = new DefaultBenchmarkRunner().run(benchmark);
        var formatter = new ConsoleReportFormatter();
        System.out.println(formatter.format(result));
    }

    private static ResultValidator<int[], int[]> getResultValidator() {
        return (input, output) -> {
            for (int i = 1; i < output.length; i++) {
                if (output[i - 1] > output[i]) {
                    throw new IllegalStateException("Output is not sorted.");
                }
            }
        };
    }

    private static InputGenerator<int[]> getInputGenerator() {
        return ctx -> {
            Random random = new Random(ctx.seed());
            int[] arr = new int[ctx.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt();
            }
            return arr;
        };
    }
}

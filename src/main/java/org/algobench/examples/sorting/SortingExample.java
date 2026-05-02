package org.algobench.examples.sorting;

import org.algobench.api.Benchmark;
import org.algobench.discovery.AlgorithmScanner;
import org.algobench.discovery.BenchmarkConfigResolver;
import org.algobench.discovery.BenchmarkSettings;
import org.algobench.report.ConsoleReportFormatter;
import org.algobench.runner.DefaultBenchmarkRunner;

import static org.algobench.api.InputGeneratorImpl.getInputGenerator;
import static org.algobench.api.ResultValidatorImpl.getResultValidator;


@BenchmarkSettings(
        warmupIterations = 3,
        measurementIterations = 5,
        forks = 2,
        sizes = {100, 1000, 3000}
)
public class SortingExample {
    private static final String SORTING_BENCHMARK = "Sorting Benchmark";
    private static final String PACKAGE_NAME = "org.algobench.examples.sorting";

    public static void main(String[] args) throws Exception {
        System.out.println(new ConsoleReportFormatter().format(new DefaultBenchmarkRunner().run(
                Benchmark.<int[], int[]>builder(SORTING_BENCHMARK)
                        .addAlgorithms(
                                AlgorithmScanner.findAlgorithms(
                                        PACKAGE_NAME,
                                        AbstractIntArraySortAlgorithm.class)
                        )
                        .inputGenerator(getInputGenerator())
                        .validator(getResultValidator())
                        .config(BenchmarkConfigResolver.resolve(SortingExample.class))
                        .build()
        )));
    }
}

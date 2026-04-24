package examples.sorting;

import org.algobench.api.Benchmark;
import org.algobench.api.BenchmarkConfig;
import org.algobench.api.InputGenerator;
import org.algobench.api.ResultValidator;
import report.ConsoleReportFormatter;
import runner.DefaultBenchmarkRunner;

import java.util.List;
import java.util.Random;

import static examples.sorting.SortAlgorithms.all;

public class SortingExample {

    public static void main(String[] args) throws Exception {
        Benchmark<int[], int[]> benchmark = Benchmark.<int[], int[]>builder("Sorting Benchmark")
                .addAlgorithms(all())
                .inputGenerator(getInputGenerator())
                .validator(getResultValidator())
                .config(getBenchmarkConfig())
                .build();

        var result = new DefaultBenchmarkRunner().run(benchmark);
        var formatter = new ConsoleReportFormatter();
        System.out.println(formatter.format(result));
    }

    private static BenchmarkConfig getBenchmarkConfig() {
        return BenchmarkConfig.builder()
                .warmupIterations(3)
                .measurementIterations(5)
                .forks(2)
                .sizes(List.of(100, 1000, 3000))
                .build();
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

package org.algobench.report;

import org.algobench.api.AlgorithmResult;
import org.algobench.api.BenchmarkResult;
import org.algobench.api.SizeResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleReportFormatterTest {

    private static final String SORTING_BENCHMARK = "Sorting Benchmark";
    private final ReportFormatter formatter = new ConsoleReportFormatter();

    @Test
    void shouldFormatBenchmarkName() {
        //given
        BenchmarkResult result = new BenchmarkResult(SORTING_BENCHMARK, List.of());
        //then
        String report = formatter.format(result);
        //when
        assertTrue(report.contains("Benchmark: Sorting Benchmark"));
    }

    @Test
    void shouldFormatAlgorithmName() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                SORTING_BENCHMARK,
                List.of(new AlgorithmResult(
                        "BubbleSort",
                        List.of(new SizeResult(100, List.of(10L, 20L, 30L)))
                ))
        );
        //then
        String report = formatter.format(result);
        //when
        assertTrue(report.contains("Algorithm: BubbleSort"));
    }

    @Test
    void shouldFormatStatisticsForSingleSizeResult() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                SORTING_BENCHMARK,
                List.of(new AlgorithmResult(
                        "BubbleSort",
                        List.of(new SizeResult(100, List.of(30L, 10L, 20L)))
                ))
        );
        //then
        String report = formatter.format(result);
        //when
        assertTrue(report.contains("size=100"));
        assertTrue(report.contains("runs=3"));
        assertTrue(report.contains("min=10 ns"));
        assertTrue(report.contains("median=20 ns"));
        assertTrue(report.contains("avg=20 ns"));
        assertTrue(report.contains("max=30 ns"));
    }

    @Test
    void shouldCalculateAverageAsLongValue() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                "Benchmark",
                List.of(new AlgorithmResult(
                        "Algorithm",
                        List.of(new SizeResult(100, List.of(10L, 20L, 20L)))
                ))
        );
        //then
        String report = formatter.format(result);
        //when
        assertTrue(report.contains("avg=16 ns"));
    }

    @Test
    void shouldSortTimingsBeforeCalculatingMinMedianAndMax() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                "Benchmark",
                List.of(new AlgorithmResult(
                        "Algorithm",
                        List.of(new SizeResult(100, List.of(50L, 10L, 40L, 20L, 30L)))
                ))
        );
        //then
        String report = formatter.format(result);
        //when
        assertTrue(report.contains("min=10 ns"));
        assertTrue(report.contains("median=30 ns"));
        assertTrue(report.contains("max=50 ns"));
    }

    @Test
    void shouldFormatMultipleSizeResults() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                SORTING_BENCHMARK,
                List.of(new AlgorithmResult(
                        "BubbleSort",
                        List.of(
                                new SizeResult(100, List.of(10L, 20L, 30L)),
                                new SizeResult(1000, List.of(100L, 200L, 300L))
                        )
                ))
        );
        //then
        String report = formatter.format(result);
        //when
        assertTrue(report.contains("size=100"));
        assertTrue(report.contains("size=1000"));
        assertTrue(report.contains("min=10 ns"));
        assertTrue(report.contains("min=100 ns"));
    }

    @Test
    void shouldFormatMultipleAlgorithms() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                SORTING_BENCHMARK,
                List.of(
                        new AlgorithmResult(
                                "BubbleSort",
                                List.of(new SizeResult(100, List.of(30L, 10L, 20L)))
                        ),
                        new AlgorithmResult(
                                "HeapSort",
                                List.of(new SizeResult(100, List.of(3L, 1L, 2L)))
                        )
                )
        );
        //then
        String report = formatter.format(result);
        //when
        assertTrue(report.contains("Algorithm: BubbleSort"));
        assertTrue(report.contains("Algorithm: HeapSort"));
        assertTrue(report.contains("min=10 ns"));
        assertTrue(report.contains("min=1 ns"));
    }

    @Test
    void shouldReturnExpectedFullReportForSimpleResult() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                SORTING_BENCHMARK,
                List.of(new AlgorithmResult(
                        "BubbleSort",
                        List.of(new SizeResult(100, List.of(30L, 10L, 20L)))
                ))
        );
        //then
        String report = formatter.format(result);

        String expected = """
                Benchmark: Sorting Benchmark
                
                Algorithm: BubbleSort
                  size=100, runs=3, min=10 ns, median=20 ns, avg=20 ns, max=30 ns
                
                """;
        //when
        assertEquals(expected, report);
    }

    @Test
    void shouldThrowExceptionWhenSizeResultHasNoTimings() {
        //given
        BenchmarkResult result = new BenchmarkResult(
                "Benchmark",
                List.of(new AlgorithmResult(
                        "Algorithm",
                        List.of(new SizeResult(100, List.of()))
                ))
        );
        //then when
        assertThrows(NoSuchElementException.class, () ->
                formatter.format(result)
        );
    }
}
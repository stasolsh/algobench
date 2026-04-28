package org.algobench.report;

import org.algobench.api.AlgorithmResult;
import org.algobench.api.BenchmarkResult;
import org.algobench.api.SizeResult;

import java.util.List;

public class ConsoleReportFormatter implements ReportFormatter {
    @Override
    public String format(BenchmarkResult result) {
        StringBuilder report = new StringBuilder();
        report.append("Benchmark: ").append(result.benchmarkName()).append("\n\n");
        for (AlgorithmResult algorithmResult : result.algorithmResults()) {
            report.append("Algorithm: ").append(algorithmResult.algorithmName()).append("\n");
            buildReport(algorithmResult, report);
            report.append("\n");
        }

        return report.toString();
    }

    private static void buildReport(AlgorithmResult algorithmResult, StringBuilder sb) {
        for (SizeResult sizeResult : algorithmResult.sizeResults()) {
            List<Long> sorted = sizeResult.timingsNanos().stream().sorted().toList();
            long min = sorted.getFirst();
            long max = sorted.getLast();
            long median = sorted.get(sorted.size() / 2);
            double avg = sorted.stream().mapToLong(Long::longValue).average().orElse(0);

            sb.append("  size=").append(sizeResult.size())
                    .append(", runs=").append(sorted.size())
                    .append(", min=").append(min)
                    .append(" ns, median=").append(median)
                    .append(" ns, avg=").append((long) avg)
                    .append(" ns, max=").append(max)
                    .append(" ns\n");
        }
    }
}

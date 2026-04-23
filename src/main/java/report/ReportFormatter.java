package report;

import org.algobench.api.BenchmarkResult;

public interface ReportFormatter {
    String format(BenchmarkResult result);
}

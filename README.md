# AlgoBench

![Build](https://github.com/stasolsh/algobench/actions/workflows/custom-action.yml/badge.svg)
![Coverage](https://codecov.io/gh/stasolsh/algobench/branch/master/graph/badge.svg)
![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue)
![License](https://img.shields.io/badge/license-MIT-green)

AlgoBench is a small Java benchmarking framework for comparing algorithms with configurable input sizes, iterations, forks, validation, and formatted benchmark reports.

It is designed for learning, experimenting, and comparing algorithm implementations in a simple and extensible way.

---

## Features

- Generic `Algorithm<I, O>` abstraction
- Benchmark builder API
- Configurable benchmark settings
- Input generation based on benchmark context
- Result validation
- Lifecycle hooks
- Annotation-based algorithm discovery
- Annotation-based benchmark configuration
- Console report formatter
- Example sorting benchmark

---

## Requirements

- Java 21
- Maven 3.9+

---

## Installation

Clone the repository:

```bash
git clone https://github.com/stasolsh/algobench.git
cd algobench
```

Run tests:

```bash
mvn clean test
```

Build the project:

```bash
mvn clean package
```

---

## Core Concepts

### Algorithm

Every benchmarked algorithm implements the `Algorithm<I, O>` interface:

```java
public interface Algorithm<I, O> {
    String name();

    O run(I input) throws Exception;
}
```

---

## Benchmark Configuration

Benchmark settings can be defined with `@BenchmarkSettings`:

```java
@BenchmarkSettings(
        warmupIterations = 3,
        measurementIterations = 5,
        forks = 2,
        sizes = {100, 1000, 3000}
)
public class SortingExample {
}
```

The configuration is resolved with:

```java
BenchmarkConfigResolver.resolve(SortingExample.class)
```

---

## Algorithm Discovery

Algorithms can be registered automatically with `@BenchmarkAlgorithm`.

```java
@BenchmarkAlgorithm
public final class HeapSortAlgorithm extends AbstractIntArraySortAlgorithm {

    public HeapSortAlgorithm() {
        super("HeapSort");
    }

    @Override
    protected void sort(int[] array) {
        // sorting logic
    }
}
```

Then all matching algorithms can be discovered by package:

```java
AlgorithmScanner.findAlgorithms(
        "org.algobench.examples.sorting",
        AbstractIntArraySortAlgorithm.class
)
```

This removes the need for a manual algorithm factory.

---

## Sorting Example

AlgoBench includes a sorting benchmark example with:

- Bubble Sort
- Heap Sort
- JDK `Arrays.sort`

The shared base class copies the input before sorting, so each algorithm receives the same original data shape:

```java
public abstract class AbstractIntArraySortAlgorithm implements Algorithm<int[], int[]> {

    @Override
    public int[] run(int[] input) throws Exception {
        int[] copy = Arrays.copyOf(input, input.length);
        sort(copy);
        return copy;
    }

    protected abstract void sort(int[] arr);
}
```

---

## Running a Benchmark

Example benchmark setup:

```java
Benchmark<int[], int[]> benchmark = Benchmark.<int[], int[]>builder("Sorting Benchmark")
        .addAlgorithms(
                AlgorithmScanner.findAlgorithms(
                        "org.algobench.examples.sorting",
                        AbstractIntArraySortAlgorithm.class
                )
        )
        .inputGenerator(InputGeneratorImpl.getInputGenerator())
        .validator(ResultValidatorImpl.getResultValidator())
        .config(BenchmarkConfigResolver.resolve(SortingExample.class))
        .build();

BenchmarkResult result = new DefaultBenchmarkRunner().run(benchmark);

System.out.println(new ConsoleReportFormatter().format(result));
```

---

## Input Generator

Input generators create input data for every benchmark execution.

For sorting benchmarks:

```java
InputGenerator<int[]> generator = InputGeneratorImpl.getInputGenerator();
```

It creates a random `int[]` using values from `BenchmarkContext`:

```java
public record BenchmarkContext(int size, long seed, int iteration, int fork) {
}
```

---

## Result Validator

Validators check whether an algorithm result is correct.

For sorting algorithms:

```java
ResultValidator<int[], int[]> validator = ResultValidatorImpl.getResultValidator();
```

It verifies that the output array is sorted in ascending order.

---

## Console Report

The console formatter prints benchmark results in a readable format:

```text
Benchmark: Sorting Benchmark

Algorithm: BubbleSort
  size=100, runs=6, min=12345 ns, median=14000 ns, avg=15000 ns, max=20000 ns
```

The report includes:

- benchmark name
- algorithm name
- input size
- number of runs
- min time
- median time
- average time
- max time

---

## Project Structure

```text
src/main/java/org/algobench
├── api
│   ├── Algorithm.java
│   ├── Benchmark.java
│   ├── BenchmarkConfig.java
│   ├── BenchmarkContext.java
│   ├── BenchmarkLifecycle.java
│   ├── BenchmarkResult.java
│   ├── InputGenerator.java
│   ├── ResultValidator.java
│   └── SizeResult.java
├── discovery
│   ├── AlgorithmScanner.java
│   ├── BenchmarkAlgorithm.java
│   ├── BenchmarkConfigResolver.java
│   └── BenchmarkSettings.java
├── examples
│   └── sorting
│       ├── AbstractIntArraySortAlgorithm.java
│       ├── BubbleSortAlgorithm.java
│       ├── HeapSortAlgorithm.java
│       ├── JdkArraySortAlgorithm.java
│       └── SortingExample.java
├── report
│   ├── ConsoleReportFormatter.java
│   └── ReportFormatter.java
└── runner
    ├── BenchmarkRunner.java
    └── DefaultBenchmarkRunner.java
```

---

## Adding a New Sorting Algorithm

1. Create a new class extending `AbstractIntArraySortAlgorithm`.
2. Add `@BenchmarkAlgorithm`.
3. Implement `sort`.

Example:

```java
@BenchmarkAlgorithm
public final class MergeSortAlgorithm extends AbstractIntArraySortAlgorithm {

    public MergeSortAlgorithm() {
        super("MergeSort");
    }

    @Override
    protected void sort(int[] arr) {
        // merge sort implementation
    }
}
```

No factory update is required.

---

## Example Benchmark Result

Configuration:

- sizes: `100`, `1000`, `3000`, `10000`
- runs per size: `30`

```text
Algorithm: Arrays.sort
  size=100, runs=30, min=13700 ns, median=16000 ns, avg=23090 ns, max=212800 ns
  size=1000, runs=30, min=35900 ns, median=48800 ns, avg=64290 ns, max=243500 ns
  size=3000, runs=30, min=142000 ns, median=162800 ns, avg=165966 ns, max=215400 ns
  size=10000, runs=30, min=545200 ns, median=667900 ns, avg=707293 ns, max=1233600 ns

Algorithm: BubbleSort
  size=100, runs=30, min=17800 ns, median=48900 ns, avg=67676 ns, max=142900 ns
  size=1000, runs=30, min=593600 ns, median=673700 ns, avg=801480 ns, max=1299200 ns
  size=3000, runs=30, min=4698200 ns, median=4819600 ns, avg=4863166 ns, max=5489700 ns
  size=10000, runs=30, min=89858400 ns, median=91913700 ns, avg=92484243 ns, max=102395000 ns

Algorithm: HeapSort
  size=100, runs=30, min=7100 ns, median=9900 ns, avg=23943 ns, max=286500 ns
  size=1000, runs=30, min=101200 ns, median=134700 ns, avg=132613 ns, max=202200 ns
  size=3000, runs=30, min=205400 ns, median=225900 ns, avg=246573 ns, max=388900 ns
  size=10000, runs=30, min=767700 ns, median=819700 ns, avg=858126 ns, max=1335900 ns

```
## Notes

This project is intended as a lightweight educational benchmarking framework.
For production-grade JVM benchmarking, consider using JMH. AlgoBench is useful for understanding benchmark structure, algorithm comparison, input generation, validation, reporting, and framework design.

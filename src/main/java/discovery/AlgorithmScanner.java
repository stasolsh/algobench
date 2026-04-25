package discovery;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.algobench.api.Algorithm;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class AlgorithmScanner {
    private AlgorithmScanner() {
    }

    public static <K, L> List<Algorithm<K, L>> findAlgorithms(
            String packageName,
            Class<? extends Algorithm<K, L>> baseType) {
        List<Algorithm<K, L>> algorithms = new ArrayList<>();
        try (ScanResult scanResult = getScanResult(packageName)) {
            for (Class<?> clazz : scanResult.getClassesWithAnnotation(BenchmarkAlgorithm.class).loadClasses()) {
                if (!baseType.isAssignableFrom(clazz) || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                algorithms.add(createInstance(clazz));
            }
        }
        algorithms.sort(Comparator.comparing(Algorithm::name));
        return algorithms;
    }

    private static ScanResult getScanResult(String packageName) {
        return new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(packageName)
                .scan();
    }

    @SuppressWarnings("unchecked")
    private static <K, L> Algorithm<K, L> createInstance(Class<?> clazz) {
        try {
            return (Algorithm<K, L>) clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(
                    "Cannot create algorithm instance: " + clazz.getName() +
                            ". Make sure it has a public no-arg constructor.", e
            );
        }
    }
}

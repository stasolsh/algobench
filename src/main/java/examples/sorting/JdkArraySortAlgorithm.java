package examples.sorting;

import java.util.Arrays;

public final class JdkArraySortAlgorithm extends AbstractIntArraySortAlgorithm {

    public JdkArraySortAlgorithm() {
        super("Arrays.sort");
    }

    @Override
    protected void sort(int[] array) {
        Arrays.sort(array);
    }
}
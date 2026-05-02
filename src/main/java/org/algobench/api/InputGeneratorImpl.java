package org.algobench.api;

import java.util.Random;

public class InputGeneratorImpl {
    public static InputGenerator<int[]> getInputGenerator() {
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

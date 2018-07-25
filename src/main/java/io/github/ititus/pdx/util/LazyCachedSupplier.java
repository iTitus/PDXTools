package io.github.ititus.pdx.util;

import java.util.function.Supplier;

public class LazyCachedSupplier<T> implements Supplier<T> {

    private T cache;
    private Supplier<T> supplier;

    public LazyCachedSupplier(Supplier<T> supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException();
        }
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (supplier != null) {
            cache = supplier.get();
            supplier = null;
        }
        return cache;
    }
}

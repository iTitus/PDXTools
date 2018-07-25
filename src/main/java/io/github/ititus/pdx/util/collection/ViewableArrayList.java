package io.github.ititus.pdx.util.collection;

import io.github.ititus.pdx.util.LazyCachedSupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ViewableArrayList<E> extends ArrayList<E> implements ViewableList<E> {

    private static final long serialVersionUID = 1;

    private LazyCachedSupplier<List<E>> view = new LazyCachedSupplier<>(() -> Collections.unmodifiableList(this));

    public ViewableArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public ViewableArrayList() {
        super();
    }

    public ViewableArrayList(Collection<? extends E> c) {
        super(c);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone() {
        ViewableArrayList<E> v = (ViewableArrayList<E>) super.clone();
        v.view = new LazyCachedSupplier<>(() -> Collections.unmodifiableList(v));
        return v;
    }

    @Override
    public List<E> getView() {
        return view.get();
    }
}

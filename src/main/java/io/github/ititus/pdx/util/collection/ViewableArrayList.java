package io.github.ititus.pdx.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ViewableArrayList<E> extends ArrayList<E> implements ViewableList<E> {

    private transient List<E> view;

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
    public List<E> getView() {
        if (view == null) {
            view = Collections.unmodifiableList(this);
        }
        return view;
    }
}

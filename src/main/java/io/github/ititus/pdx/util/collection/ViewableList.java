package io.github.ititus.pdx.util.collection;

import java.util.List;

public interface ViewableList<E> extends List<E> {

    List<E> getView();

}

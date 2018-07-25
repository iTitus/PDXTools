package io.github.ititus.pdx.util.collection;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CollectionUtil {

    private static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    private static final ViewableList EMPTY_VIEWABLE_LIST = new EmptyViewableList<>();

    @SuppressWarnings("unchecked")
    public static <T> ViewableList<T> viewableListOf() {
        return (ViewableList<T>) EMPTY_VIEWABLE_LIST;
    }

    public static <T> ViewableList<T> viewableListOf(T t) {
        return new ViewableSingletonList<>(t);
    }

    public static <T> ViewableList<T> viewableListOf(T... t) {
        return new ViewableArrayList<>(listOf(t));
    }

    public static <T> List<T> listOf() {
        return Collections.emptyList();
    }

    public static <T> List<T> listOf(T t) {
        return Collections.singletonList(t);
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... t) {
        if (t != null && t.length > 0) {
            if (t.length == 1) {
                return listOf(t[0]);
            }
            List<T> l = new ArrayList<>(t.length);
            l.addAll(Arrays.asList(t));
            return l;
        }
        return listOf();
    }

    public static <T> List<T> mutableListOf(T t) {
        List<T> l = new ArrayList<>(1);
        l.add(t);
        return l;
    }

    public static <T> Set<T> setOf() {
        return Collections.emptySet();
    }


    public static <T> Set<T> setOf(T t) {
        return Collections.singleton(t);
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... t) {
        if (t != null && t.length > 0) {
            if (t.length == 1) {
                return setOf(t[0]);
            }
            Set<T> s = new HashSet<>(t.length);
            s.addAll(Arrays.asList(t));
            return s;
        }
        return setOf();
    }

    public static <T> Collector<T, ?, ViewableList<T>> toViewableList() {
        return new CollectorImpl<>(
                (Supplier<ViewableList<T>>) ViewableArrayList::new,
                List::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                CH_ID);
    }

}

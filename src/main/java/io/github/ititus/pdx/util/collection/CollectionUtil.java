package io.github.ititus.pdx.util.collection;

import com.koloboke.collect.set.hash.HashObjSets;

import java.util.*;
import java.util.stream.Collector;

public class CollectionUtil {

    private static final Set<Collector.Characteristics> CH_CONCURRENT_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
    private static final Set<Collector.Characteristics> CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED));
    private static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    private static final Set<Collector.Characteristics> CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
    private static final Set<Collector.Characteristics> CH_NOID = setOf();

    private static final ViewableList EMPTY_VIEWABLE_LIST = new EmptyViewableList<>();

    @SuppressWarnings("unchecked")
    public static <T> ViewableList<T> viewableListOf() {
        return (ViewableList<T>) EMPTY_VIEWABLE_LIST;
    }

    public static <T> ViewableList<T> viewableListOf(T t) {
        return new ViewableSingletonList<>(t);
    }

    @SafeVarargs
    public static <T> ViewableList<T> viewableListOf(T... t) {
        if (t != null && t.length > 0) {
            if (t.length == 1) {
                return viewableListOf(t[0]);
            }
            return ViewableUnmodifiableArrayList.<T>builder().addAll(t).build();
        }
        return viewableListOf();
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
            return ViewableUnmodifiableArrayList.<T>builder().addAll(t).build();
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
        return HashObjSets.newImmutableSetOf(t);
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... t) {
        if (t != null && t.length > 0) {
            if (t.length == 1) {
                return setOf(t[0]);
            }
            return HashObjSets.newImmutableSet(t);
        }
        return setOf();
    }

    public static <T> Collector<T, ViewableUnmodifiableArrayList.Builder<T>, ViewableList<T>> toViewableList() {
        return new CollectorImpl<>(
                ViewableUnmodifiableArrayList::builder,
                ViewableUnmodifiableArrayList.Builder::add,
                ViewableUnmodifiableArrayList.Builder::addAll,
                ViewableUnmodifiableArrayList.Builder::build,
                CH_NOID);
    }

}

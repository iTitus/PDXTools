package io.github.ititus.pdx.util;

import java.util.*;

public class CollectionUtil {

    public static <T> List<T> listOf() {
        return Collections.emptyList();
    }

    public static <T> List<T> listOf(T t) {
        return Collections.singletonList(t);
    }

    public static <T> List<T> listOf(T t1, T t2, T t3) {
        List<T> l = new ArrayList<>(3);
        l.add(t1);
        l.add(t2);
        l.add(t3);
        return l;
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... t) {
        if (t != null && t.length > 0) {
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
            Set<T> s = new HashSet<>(t.length);
            s.addAll(Arrays.asList(t));
            return s;
        }
        return setOf();
    }

}

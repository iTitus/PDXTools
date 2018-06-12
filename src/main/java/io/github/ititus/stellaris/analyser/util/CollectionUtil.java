package io.github.ititus.stellaris.analyser.util;

import java.util.*;

public class CollectionUtil {

    public static <T> List<T> listOf() {
        List<T> l = new ArrayList<>();
        return l;
    }


    public static <T> List<T> listOf(T t) {
        List<T> l = new ArrayList<>();
        l.add(t);
        return l;
    }

    public static <T> List<T> listOf(T... t) {
        List<T> l = new ArrayList<>();
        if (t != null && t.length > 0) {
            l.addAll(Arrays.asList(t));
        }
        return l;
    }

    public static <T> List<T> listOf(T t1, T t2, T t3) {
        List<T> l = new ArrayList<>();
        l.add(t1);
        l.add(t2);
        l.add(t3);
        return l;
    }

    public static <T> Set<T> setOf() {
        Set<T> s = new HashSet<>();
        return s;
    }


    public static <T> Set<T> setOf(T t) {
        Set<T> s = new HashSet<>();
        s.add(t);
        return s;
    }

    public static <T> Set<T> setOf(T... t) {
        Set<T> s = new HashSet<>();
        if (t != null && t.length > 0) {
            s.addAll(Arrays.asList(t));
        }
        return s;
    }

}

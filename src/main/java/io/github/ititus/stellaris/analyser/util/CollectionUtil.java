package io.github.ititus.stellaris.analyser.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {

    public static <T> List<T> listOf() {
        List<T> l = new ArrayList<>();
        return l;
    }


    public static <T> List<T> listOf(T t1) {
        List<T> l = new ArrayList<>();
        l.add(t1);
        return l;
    }

    public static <T> List<T> listOf(T t1, T t2) {
        List<T> l = new ArrayList<>();
        l.add(t1);
        l.add(t2);
        return l;
    }

    public static <T> List<T> listOf(T t1, T t2, T t3) {
        List<T> l = new ArrayList<>();
        l.add(t1);
        l.add(t2);
        l.add(t3);
        return l;
    }

}

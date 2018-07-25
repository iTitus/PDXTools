package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.*;
import java.util.function.Function;

public final class PdxScriptList implements IPdxScript {

    private final Mode mode;
    private final PdxRelation relation;
    private final ViewableList<IPdxScript> list;

    public PdxScriptList(Mode mode, PdxRelation relation, Collection<IPdxScript> list) {
        this.mode = mode;
        this.relation = relation;
        this.list = new ViewableArrayList<>(list);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public PdxRelation getRelation() {
        return relation;
    }

    public int size() {
        return list.size();
    }

    public IPdxScript getRaw(int i) {
        return i >= 0 && i < size() ? list.get(i) : null;
    }

    public IPdxScript get(int i) {
        IPdxScript o = getRaw(i);
        if (o instanceof PdxScriptList) {
            PdxScriptList l = (PdxScriptList) o;
            if (l.size() == 1) {
                return l.get(0);
            }
        }
        return o;
    }

    public String getString(int i) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof String) {
                return (String) v;
            }
        }
        return null;
    }

    public ViewableList<PdxScriptValue> getAsValueList() {
        return getAsList(s -> s instanceof PdxScriptValue ? (PdxScriptValue) s : null);
    }

    public String[] getAsStringArray() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof String ? (String) v.getValue() : null).toArray(String[]::new);
    }

    public ViewableList<String> getAsStringList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof String ? (String) v.getValue() : null).collect(CollectionUtil.toViewableList());
    }

    public Number[] getAsNumberArray() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Number ? (Number) v.getValue() : null).toArray(Number[]::new);
    }

    public ViewableList<Number> getAsNumberList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Number ? (Number) v.getValue() : null).collect(CollectionUtil.toViewableList());
    }

    public int[] getAsIntArray() {
        return getAsValueList().stream().mapToInt(v -> v != null && v.getValue() instanceof Number ? ((Number) v.getValue()).intValue() : 0).toArray();
    }

    public ViewableList<Integer> getAsUnsignedIntegerList() {
        return getAsValueList().stream().map(v -> {
            if (v != null) {
                Object o = v.getValue();
                if (o instanceof Integer) {
                    return (Integer) o;
                } else if (o instanceof Long) {
                    return ((Long) o).intValue();
                }
            }
            return null;
        }).collect(CollectionUtil.toViewableList());
    }

    public ViewableList<Integer> getAsIntegerList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Integer ? (Integer) v.getValue() : null).collect(CollectionUtil.toViewableList());
    }

    public long[] getAsLongArray() {
        return getAsValueList().stream().mapToLong(v -> v != null && v.getValue() instanceof Number ? ((Number) v.getValue()).longValue() : 0).toArray();
    }

    public ViewableList<Long> getAsLongList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Long ? (Long) v.getValue() : null).collect(CollectionUtil.toViewableList());
    }

    public double[] getAsDoubleArray() {
        return getAsValueList().stream().mapToDouble(v -> v != null && v.getValue() instanceof Number ? ((Number) v.getValue()).doubleValue() : 0).toArray();
    }

    public ViewableList<Double> getAsDoubleList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Double ? (Double) v.getValue() : null).collect(CollectionUtil.toViewableList());
    }

    public <T> ViewableList<T> getAsList(Function<IPdxScript, T> fct) {
        return list.stream().map(fct).collect(CollectionUtil.toViewableList());
    }

    @Override
    public PdxScriptList append(IPdxScript script) {
        return mode == Mode.IMPLICIT ? builder().addAll(list).add(script).build(Mode.IMPLICIT, PdxRelation.EQUALS) : IPdxScript.super.append(script);
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        // TODO: Maybe add support for printing lists in comma mode
        if ((root && indent != 0) || (root && mode != Mode.NORMAL) || (root && key != null) || (root && list.isEmpty()) || (mode != Mode.NORMAL && list.size() < 2)) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder();

        IPdxScript.listObjectOpen(indent, root || mode == Mode.IMPLICIT, key, b, relation, list.isEmpty());

        if (mode == Mode.COMMA) {
            b.append(PdxScriptParser.indent(indent + 1)).append(COMMENT_CHAR).append(SPACE_CHAR).append("COMMA LIST").append(LINE_FEED);
        }

        list.forEach(script -> {
            b.append(script.toPdxScript(root || mode == Mode.IMPLICIT ? indent : indent + 1, false, mode == Mode.IMPLICIT ? key : null));
            b.append(LINE_FEED);
        });

        IPdxScript.listObjectClose(indent, root || mode == Mode.IMPLICIT, b, list.isEmpty());

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdxScriptList)) {
            return false;
        }
        PdxScriptList that = (PdxScriptList) o;
        return mode == that.mode && relation == that.relation && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, relation, list);
    }

    @Override
    public String toString() {
        return "PdxScriptList{" +
                "mode=" + mode +
                ", relation=" + relation +
                ", list=" + list +
                '}';
    }

    enum Mode {
        NORMAL, COMMA, IMPLICIT
    }

    public static class Builder {

        private static final Map<Mode, Map<PdxRelation, PdxScriptList>> EMPTY_CACHE;

        static {
            EMPTY_CACHE = new EnumMap<>(Mode.class);
            Arrays.stream(Mode.values()).forEach(mode -> {
                Map<PdxRelation, PdxScriptList> map = EMPTY_CACHE.computeIfAbsent(mode, k -> new EnumMap<>(PdxRelation.class));
                Arrays.stream(PdxRelation.values()).forEach(relation -> map.put(relation, new PdxScriptList(mode, relation, Collections.emptyList())));
            });
        }

        private final List<IPdxScript> list;

        public Builder() {
            this.list = new ArrayList<>();
        }

        public Builder add(IPdxScript value) {
            if (value != null) {
                list.add(value);
            }
            return this;
        }

        public Builder addAll(Collection<IPdxScript> values) {
            list.addAll(values);
            return this;
        }

        public PdxScriptList build(PdxRelation relation) {
            return build(Mode.NORMAL, relation);
        }

        public PdxScriptList build(Mode mode, PdxRelation relation) {
            if (list.isEmpty()) {
                return EMPTY_CACHE.get(mode).get(relation);
            }
            return new PdxScriptList(mode, relation, list);
        }
    }
}

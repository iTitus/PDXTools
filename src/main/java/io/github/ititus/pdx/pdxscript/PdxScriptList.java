package io.github.ititus.pdx.pdxscript;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PdxScriptList implements IPdxScript {

    private final boolean implicit;
    private final PdxRelation relation;
    private final List<IPdxScript> list;

    public PdxScriptList(boolean implicit, PdxRelation relation, Collection<IPdxScript> list) {
        this.implicit = implicit;
        this.relation = relation;
        this.list = new ArrayList<>(list);
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isImplicit() {
        return implicit;
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

    public List<PdxScriptValue> getAsValueList() {
        return getAsList(s -> s instanceof PdxScriptValue ? (PdxScriptValue) s : null);
    }

    public String[] getAsStringArray() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof String ? (String) v.getValue() : null).toArray(String[]::new);
    }

    public List<String> getAsStringList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof String ? (String) v.getValue() : null).collect(Collectors.toList());
    }

    public Number[] getAsNumberArray() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Number ? (Number) v.getValue() : null).toArray(Number[]::new);
    }

    public List<Number> getAsNumberList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Number ? (Number) v.getValue() : null).collect(Collectors.toList());
    }

    public int[] getAsIntArray() {
        return getAsValueList().stream().mapToInt(v -> v != null && v.getValue() instanceof Number ? ((Number) v.getValue()).intValue() : null).toArray();
    }

    public List<Integer> getAsIntegerList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Integer ? (Integer) v.getValue() : null).collect(Collectors.toList());
    }

    public long[] getAsLongArray() {
        return getAsValueList().stream().mapToLong(v -> v != null && v.getValue() instanceof Number ? ((Number) v.getValue()).longValue() : null).toArray();
    }

    public List<Long> getAsLongList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Long ? (Long) v.getValue() : null).collect(Collectors.toList());
    }

    public double[] getAsDoubleArray() {
        return getAsValueList().stream().mapToDouble(v -> v != null && v.getValue() instanceof Number ? ((Number) v.getValue()).doubleValue() : null).toArray();
    }

    public List<Double> getAsDoubleList() {
        return getAsValueList().stream().map(v -> v != null && v.getValue() instanceof Double ? (Double) v.getValue() : null).collect(Collectors.toList());
    }

    public <T> List<T> getAsList(Function<IPdxScript, T> fct) {
        return list.stream().map(fct).collect(Collectors.toList());
    }

    @Override
    public PdxScriptList append(IPdxScript script) {
        return implicit ? builder().addAll(list).add(script).build(true, PdxRelation.EQUALS) : IPdxScript.super.append(script);
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if ((root && indent != 0) || (root && implicit) || (root && key != null)) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder();

        IPdxScript.listObjectOpen(indent, root || implicit, key, b, relation, list.isEmpty());

        list.forEach(script -> {
            if (implicit) {
                b.append(script.toPdxScript(indent, false, key));
            } else {
                b.append(script.toPdxScript(root ? indent : indent + 1, false, null));
            }
            b.append('\n');
        });

        IPdxScript.listObjectClose(indent, root || implicit, b, list.isEmpty());

        return b.toString();
    }

    @Override
    public String toString() {
        return "PdxScriptList{" +
                "implicit=" + implicit +
                ", relation=" + relation +
                ", list=" + list +
                '}';
    }

    public static class Builder {

        private final List<IPdxScript> list;

        public Builder() {
            this.list = new ArrayList<>();
        }

        public Builder add(IPdxScript value) {
            list.add(value);
            return this;
        }

        public Builder addAll(Collection<IPdxScript> values) {
            list.addAll(values);
            return this;
        }

        public PdxScriptList build(PdxRelation relation) {
            return build(false, relation);
        }

        public PdxScriptList build(boolean implicit, PdxRelation relation) {
            return new PdxScriptList(implicit, relation, list);
        }
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
        return relation == that.relation && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, list);
    }
}

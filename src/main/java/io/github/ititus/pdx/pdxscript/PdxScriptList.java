package io.github.ititus.pdx.pdxscript;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PdxScriptList implements IPdxScript {

    private final List<IPdxScript> list;

    public PdxScriptList(Collection<IPdxScript> list) {
        this.list = new ArrayList<>(list);
    }

    public static Builder builder() {
        return new Builder();
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
    public IPdxScript append(IPdxScript object) {
        return builder().addAll(list).add(object).build();
    }

    @Override
    public String toPdxScript(int indent, boolean bound, boolean indentFirst) {
        StringBuilder b = new StringBuilder(indentFirst ? PdxScriptParser.indent(indent) : "").append("{");
        if (list.size() > 0) {
            b.append('\n');
        }

        list.forEach(script -> {
            b.append(script.toPdxScript(indent + 1, true, true));
            b.append('\n');
        });

        if (list.size() > 0) {
            b.append(PdxScriptParser.indent(indent));
        }
        return b.append('}').toString();
    }

    @Override
    public String toString() {
        return "list = [" + list + "]";
    }

    public static class Builder {

        private final List<IPdxScript> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder add(IPdxScript value) {
            list.add(value);
            return this;
        }

        public Builder addAll(Collection<IPdxScript> values) {
            list.addAll(values);
            return this;
        }

        public PdxScriptList build() {
            return new PdxScriptList(list);
        }
    }
}

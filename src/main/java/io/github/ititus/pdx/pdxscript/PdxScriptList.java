package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.Deduplicator;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class PdxScriptList implements IPdxScript {

    private static final Deduplicator<PdxScriptList> DEDUPLICATOR = new Deduplicator<>(l -> !l.list.isEmpty());

    private final Mode mode;
    private final PdxRelation relation;
    private final ImmutableList<IPdxScript> list;

    private PdxScriptList(Mode mode, PdxRelation relation, ImmutableList<IPdxScript> list) {
        this.mode = mode;
        this.relation = relation;
        this.list = list;
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

    private IPdxScript getRaw(int i) {
        return i >= 0 && i < size() ? list.get(i) : null;
    }

    public IPdxScript get(int i) {
        return getRaw(i);
    }

    public Object getValue(int i) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            return ((PdxScriptValue) o).getValue();
        }
        return null;
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

    public boolean getBoolean(int i) {
        return getBoolean(i, false);
    }

    public boolean getBoolean(int i, boolean def) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Boolean) {
                return (boolean) v;
            }
        }
        return def;
    }

    public int getUnsignedInt(int i) {
        return getUnsignedInt(i, 0);
    }

    public int getUnsignedInt(int i, int def) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Integer) {
                return (int) v;
            } else if (v instanceof Long) {
                long l = (long) v;
                if (l >= 0 && l <= UNSIGNED_INT_MAX_LONG) {
                    return (int) l;
                }
            }
        }
        return def;
    }

    public int getInt(int i) {
        return getInt(i, 0);
    }

    public int getInt(int i, int def) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Integer) {
                return (int) v;
            }
        }
        return def;
    }

    public long getLong(int i) {
        return getLong(i, 0);
    }

    public long getLong(int i, long def) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Long || v instanceof Integer) {
                return ((Number) v).longValue();
            }
        }
        return def;
    }

    public double getDouble(int i) {
        return getDouble(i, 0);
    }

    public double getDouble(int i, double def) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Double) {
                return (double) v;
            }
        }
        return def;
    }

    public Date getDate(int i) {
        IPdxScript o = get(i);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Date) {
                return (Date) v;
            }
        }
        return null;
    }

    public Stream<PdxScriptValue> getAsValueStream() {
        return list.stream().filter(s -> s instanceof PdxScriptValue).map(s -> (PdxScriptValue) s);
    }

    public Stream<String> getAsStringStream() {
        return getAsValueStream().filter(v -> v.getValue() instanceof String).map(v -> (String) v.getValue());
    }

    public String[] getAsStringArray() {
        return getAsStringStream().toArray(String[]::new);
    }

    public ImmutableList<String> getAsStringList() {
        return getAsStringStream().collect(Collectors2.toImmutableList());
    }

    public Stream<Number> getAsNumberStream() {
        return getAsValueStream().filter(v -> v.getValue() instanceof Number).map(v -> (Number) v.getValue());
    }

    public Number[] getAsNumberArray() {
        return getAsNumberStream().toArray(Number[]::new);
    }

    public ImmutableList<Number> getAsNumberList() {
        return getAsNumberStream().collect(Collectors2.toImmutableList());
    }

    public IntStream getAsIntStream() {
        return getAsNumberStream().mapToInt(Number::intValue);
    }

    public int[] getAsIntArray() {
        return getAsIntStream().toArray();
    }

    public ImmutableIntList getAsIntList() {
        return CollectionUtil.toImmutableList(getAsIntStream());
    }

    public LongStream getAsLongStream() {
        return getAsNumberStream().mapToLong(Number::longValue);
    }

    public long[] getAsLongArray() {
        return getAsLongStream().toArray();
    }

    public ImmutableLongList getAsLongList() {
        return CollectionUtil.toImmutableList(getAsLongStream());
    }

    public DoubleStream getAsDoubleStream() {
        return getAsNumberStream().mapToDouble(Number::doubleValue);
    }

    public double[] getAsDoubleArray() {
        return getAsDoubleStream().toArray();
    }

    public ImmutableDoubleList getAsDoubleList() {
        return CollectionUtil.toImmutableList(getAsDoubleStream());
    }

    public <T> ImmutableList<T> getAsList(Function<IPdxScript, T> fct) {
        return list.collect(fct);
    }

    @Override
    public PdxScriptList append(IPdxScript script) {
        return mode == Mode.IMPLICIT ? builder().addAllIterable(list).add(script).build(Mode.IMPLICIT, PdxRelation.EQUALS) : IPdxScript.super.append(script);
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if ((root && indent != 0) || (root && mode != Mode.NORMAL) || (root && key != null) || (root && list.isEmpty()) || (mode != Mode.NORMAL && list.size() < 2)) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder();

        IPdxScript.listObjectOpen(indent, root || mode == Mode.IMPLICIT, key, b, relation, list.isEmpty());

        // TODO: Add support for printing lists in comma mode
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

    public PdxUsageStatistic getUsageStatistic() {
        MutableMap<String, PdxUsage> usages = Maps.mutable.empty();
        list.forEach(s -> {
            if (s instanceof PdxScriptObject) {
                ((PdxScriptObject) s).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(k, usage, PdxUsage::merge));
            } else if (s instanceof PdxScriptList) {
                ((PdxScriptList) s).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(k, usage, PdxUsage::merge));
            }
        });
        return new PdxUsageStatistic(usages);
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

        private static final ImmutableMap<Mode, ImmutableMap<PdxRelation, PdxScriptList>> EMPTY_CACHE;

        static {
            Map<Mode, ImmutableMap<PdxRelation, PdxScriptList>> map = new EnumMap<>(Mode.class);
            Arrays.stream(Mode.values()).forEach(mode -> {
                Map<PdxRelation, PdxScriptList> map1 = new EnumMap<>(PdxRelation.class);
                Arrays.stream(PdxRelation.values()).forEach(relation -> map1.put(relation, new PdxScriptList(mode, relation, Lists.immutable.empty())));
                map.put(mode, Maps.immutable.withAll(map1));
            });
            EMPTY_CACHE = Maps.immutable.withAll(map);
        }

        private final MutableList<IPdxScript> list;

        public Builder() {
            this.list = Lists.mutable.empty();
        }

        public Builder add(IPdxScript value) {
            if (value != null) {
                list.add(value);
            }
            return this;
        }

        public Builder addAllIterable(Iterable<? extends IPdxScript> values) {
            list.addAllIterable(values);
            return this;
        }

        public Builder addAll(Collection<? extends IPdxScript> values) {
            list.addAll(values);
            return this;
        }

        public PdxScriptList build(PdxRelation relation) {
            return build(Mode.NORMAL, relation);
        }

        public PdxScriptList build(Mode mode, PdxRelation relation) {
            return DEDUPLICATOR.deduplicate(buildRaw(mode, relation));
        }

        public PdxScriptList buildRaw(Mode mode, PdxRelation relation) {
            if (list.isEmpty()) {
                return EMPTY_CACHE.get(mode).get(relation);
            }
            return new PdxScriptList(mode, relation, list.toImmutable());
        }
    }
}

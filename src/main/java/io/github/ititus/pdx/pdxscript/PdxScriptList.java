package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.*;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;
import static org.eclipse.collections.impl.collector.Collectors2.toImmutableList;

public final class PdxScriptList implements IPdxScript {

    public static final PdxScriptList EMPTY_NORMAL = builder().buildRaw(Mode.NORMAL, PdxRelation.EQUALS);
    public static final PdxScriptList EMPTY_COMMA = builder().buildRaw(Mode.COMMA, PdxRelation.EQUALS);
    public static final PdxScriptList EMPTY_IMPLICIT = builder().buildRaw(Mode.IMPLICIT, PdxRelation.EQUALS);

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

    public IPdxScript getRaw(int i) {
        return i >= 0 && i < size() ? list.get(i) : null;
    }

    public IPdxScript get(int i) {
        IPdxScript raw = getRaw(i);
        if (raw == null) {
            throw new NoSuchElementException("given index " + i + " contains no value");
        }

        return raw;
    }

    public IPdxScript get(int i, IPdxScript def) {
        IPdxScript raw = getRaw(i);
        return raw != null ? raw : def;
    }

    public Stream<IPdxScript> stream() {
        return list.stream();
    }

    public Stream<PdxScriptValue> valueStream() {
        return list.stream()
                .map(IPdxScript::expectValue);
    }

    public Stream<PdxScriptList> listStream() {
        return list.stream()
                .map(IPdxScript::expectList);
    }

    public Stream<PdxScriptObject> objectStream() {
        return list.stream()
                .map(IPdxScript::expectObject);
    }

    public IntStream intStream() {
        return valueStream()
                .mapToInt(PdxScriptValue::expectInt);
    }

    public int[] getAsIntArray() {
        return intStream()
                .toArray();
    }

    public ImmutableIntList getAsIntList() {
        return intStream()
                .collect(IntLists.mutable::empty, MutableIntList::add, MutableIntList::addAll)
                .toImmutable();
    }

    public IntStream unsignedIntStream() {
        return valueStream()
                .mapToInt(PdxScriptValue::expectUnsignedInt);
    }

    public int[] getAsUnsignedIntArray() {
        return intStream()
                .toArray();
    }

    public ImmutableIntList getAsUnsignedIntList() {
        return intStream()
                .collect(IntLists.mutable::empty, MutableIntList::add, MutableIntList::addAll)
                .toImmutable();
    }

    public LongStream longStream() {
        return valueStream()
                .mapToLong(PdxScriptValue::expectLong);
    }

    public long[] getAsLongArray() {
        return longStream()
                .toArray();
    }

    public ImmutableLongList getAsLongList() {
        return longStream()
                .collect(LongLists.mutable::empty, MutableLongList::add, MutableLongList::addAll)
                .toImmutable();
    }

    public DoubleStream doubleStream() {
        return valueStream()
                .mapToDouble(PdxScriptValue::expectDouble);
    }

    public double[] getAsDoubleArray() {
        return doubleStream()
                .toArray();
    }

    public ImmutableDoubleList getAsDoubleList() {
        return doubleStream()
                .collect(DoubleLists.mutable::empty, MutableDoubleList::add, MutableDoubleList::addAll)
                .toImmutable();
    }

    public Stream<Number> numberStream() {
        return valueStream()
                .map(PdxScriptValue::expectNumber);
    }

    public Number[] getAsNumberArray() {
        return numberStream()
                .toArray(Number[]::new);
    }

    public ImmutableList<Number> getAsNumberList() {
        return numberStream()
                .collect(toImmutableList());
    }

    public Stream<String> stringStream() {
        return valueStream()
                .map(PdxScriptValue::expectString);
    }

    public String[] getAsStringArray() {
        return stringStream()
                .toArray(String[]::new);
    }

    public ImmutableList<String> getAsStringList() {
        return stringStream()
                .collect(toImmutableList());
    }

    public Stream<LocalDate> dateStream() {
        return valueStream()
                .map(PdxScriptValue::expectDate);
    }

    public LocalDate[] getAsDateArray() {
        return dateStream()
                .toArray(LocalDate[]::new);
    }

    public ImmutableList<LocalDate> getAsDateList() {
        return dateStream()
                .collect(toImmutableList());
    }

    public Stream<PdxColor> colorStream() {
        return valueStream()
                .map(PdxScriptValue::expectColor);
    }

    public PdxColor[] getAsColorArray() {
        return colorStream()
                .toArray(PdxColor[]::new);
    }

    public ImmutableList<PdxColor> getAsColorList() {
        return colorStream()
                .collect(toImmutableList());
    }

    public <T> ImmutableList<T> getAsList(Function<? super IPdxScript, ? extends T> fct) {
        return stream()
                .map(fct)
                .collect(toImmutableList());
    }

    @Override
    public PdxScriptList append(IPdxScript script) {
        if (mode == Mode.IMPLICIT) {
            return builder()
                    .addAllIterable(list)
                    .add(script)
                    .build(mode, relation);
        }

        return IPdxScript.super.append(script);
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if ((root && indent != 0) || (root && mode != Mode.NORMAL) || (root && key != null) || (root && list.isEmpty()) || (mode != Mode.NORMAL && list.size() < 2)) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder();

        PdxHelper.listObjectOpen(indent, root || mode == Mode.IMPLICIT, key, b, relation, list.isEmpty());

        // TODO: Add support for printing lists in comma mode
        if (mode == Mode.COMMA) {
            b.append(PdxHelper.indent(indent + 1)).append(COMMENT_CHAR).append(SPACE_CHAR).append("COMMA LIST").append(LINE_FEED);
        }

        list.forEach(script -> {
            b.append(script.toPdxScript(root || mode == Mode.IMPLICIT ? indent : indent + 1, false,
                    mode == Mode.IMPLICIT ? key : null));
            b.append(LINE_FEED);
        });

        PdxHelper.listObjectClose(indent, root || mode == Mode.IMPLICIT, b, list.isEmpty());

        return b.toString();
    }

    public PdxUsageStatistic getUsageStatistic() {
        MutableMap<String, PdxUsage> usages = Maps.mutable.empty();
        list.forEach(s -> {
            if (s instanceof PdxScriptObject) {
                ((PdxScriptObject) s).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(k,
                        usage, PdxUsage::merge));
            } else if (s instanceof PdxScriptList) {
                ((PdxScriptList) s).getUsageStatistic().getUsages().forEachKeyValue((k, usage) -> usages.merge(k,
                        usage, PdxUsage::merge));
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
                Arrays.stream(PdxRelation.values()).forEach(relation -> map1.put(relation, new PdxScriptList(mode,
                        relation, Lists.immutable.empty())));
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
            return buildRaw(mode, relation);
        }

        public PdxScriptList buildRaw(Mode mode, PdxRelation relation) {
            if (list.isEmpty()) {
                return EMPTY_CACHE.get(mode).get(relation);
            }
            return new PdxScriptList(mode, relation, list.toImmutable());
        }
    }
}

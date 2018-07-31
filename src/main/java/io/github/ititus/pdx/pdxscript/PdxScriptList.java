package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;

import java.util.*;

public final class PdxScriptList implements IPdxScript {

    private final Mode mode;
    private final PdxRelation relation;
    private final ImmutableList<IPdxScript> list;

    public PdxScriptList(Mode mode, PdxRelation relation, ImmutableList<IPdxScript> list) {
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

    public ImmutableList<PdxScriptValue> getAsValueList() {
        return getAsList(PdxScriptValue.class);
    }

    public String[] getAsStringArray() {
        return getAsStringList().toArray(new String[0]);
    }

    public ImmutableList<String> getAsStringList() {
        return getAsValueList().collectIf(v -> v.getValue() instanceof String, v -> (String) v.getValue());
    }

    public Number[] getAsNumberArray() {
        return getAsNumberList().toArray(new Number[0]);
    }

    public ImmutableList<Number> getAsNumberList() {
        return getAsValueList().collectIf(v -> v.getValue() instanceof Number, v -> (Number) v.getValue());
    }

    public int[] getAsIntArray() {
        return getAsNumberList().stream().mapToInt(Number::intValue).toArray();
    }

    public ImmutableIntList getAsIntList() {
        return IntLists.immutable.with(getAsIntArray());
    }

    public long[] getAsLongArray() {
        return getAsNumberList().stream().mapToLong(Number::longValue).toArray();
    }

    public ImmutableLongList getAsLongList() {
        return LongLists.immutable.with(getAsLongArray());
    }

    public double[] getAsDoubleArray() {
        return getAsNumberList().stream().mapToDouble(Number::doubleValue).toArray();
    }

    public ImmutableDoubleList getAsDoubleList() {
        return DoubleLists.immutable.with(getAsDoubleArray());
    }

    public <T> ImmutableList<T> getAsList(Function<IPdxScript, T> fct) {
        return list.collect(fct);
    }

    public <T> ImmutableList<T> getAsList(Predicate<IPdxScript> prdct, Function<IPdxScript, T> fct) {
        return list.collectIf(prdct, fct);
    }

    public <T> ImmutableList<T> getAsList(Class<T> clazz) {
        return list.selectInstancesOf(clazz);
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

        // TODO: Maybe add support for printing lists in comma mode
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

        private static final ImmutableMap<Mode, ImmutableMap<PdxRelation, PdxScriptList>> EMPTY_CACHE;

        static {
            Map<Mode, ImmutableMap<PdxRelation, PdxScriptList>> map = new EnumMap<>(Mode.class);
            Arrays.stream(Mode.values()).forEach(mode -> {
                Map<PdxRelation, PdxScriptList> map1 = new EnumMap<>(PdxRelation.class);
                Arrays.stream(PdxRelation.values()).forEach(relation -> map1.put(relation, new PdxScriptList(mode, relation, Lists.immutable.empty())));
                map.put(mode, Maps.adapt(map1).toImmutable());
            });
            EMPTY_CACHE = Maps.adapt(map).toImmutable();
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
            if (list.isEmpty()) {
                return EMPTY_CACHE.get(mode).get(relation);
            }
            return new PdxScriptList(mode, relation, list.toImmutable());
        }
    }
}

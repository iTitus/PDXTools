package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.*;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.primitive.*;

import java.util.*;
import java.util.function.*;

public final class PdxScriptObject implements IPdxScript {

    private final PdxRelation relation;
    private final ImmutableMap<String, IPdxScript> map;

    private MutableMultimap<String, String> used;
    private MutableMultimap<String, String> wronglyUsed;

    public PdxScriptObject(PdxRelation relation, ImmutableMap<String, IPdxScript> map) {
        this.relation = relation;
        this.map = map;
    }

    public static <T> Function<IPdxScript, T> nullOr(Function<IPdxScript, T> fct) {
        return s -> s != null && (!(s instanceof PdxScriptValue) || ((PdxScriptValue) s).getValue() != null) ? fct.apply(s) : null;
    }

    public static <T> Function<IPdxScript, T> objectOrNull(Function<IPdxScript, T> fct) {
        return s -> s instanceof PdxScriptObject ? fct.apply(s) : null;
    }

    public static Builder builder() {
        return new Builder();
    }

    private static String getTypeString(IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return OBJECT;
        }
        if (s instanceof PdxScriptList) {
            return ((PdxScriptList) s).getMode() == PdxScriptList.Mode.IMPLICIT ? IMPLICIT_LIST : LIST;
        }
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Date) {
                return DATE;
            } else if (v instanceof Double) {
                return DOUBLE;
            } else if (v instanceof Long) {
                long l = (long) v;
                if (l >= 0 && l <= UNSIGNED_INT_MAX_LONG) {
                    return UNSIGNED_INT;
                }
                return LONG;
            } else if (v instanceof Integer) {
                return INT;
            } else if (v instanceof Boolean) {
                return BOOLEAN;
            } else if (v instanceof String) {
                return STRING;
            }
        }
        return NULL;
    }

    @Override
    public PdxRelation getRelation() {
        return relation;
    }

    public void use(String key, String usage) {
        if (used == null) {
            used = Multimaps.mutable.set.with(key, usage);
        } else {
            used.put(key, usage);
        }
    }

    private void useWrongly(String key, String usage) {
        if (wronglyUsed == null) {
            wronglyUsed = Multimaps.mutable.set.with(key, usage);
        } else {
            wronglyUsed.put(key, usage);
        }
    }

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public boolean hasKey(String key, String type) {
        return map.containsKey(key) && getTypeString(get(key)).equals(type);
    }

    private IPdxScript getRaw(String key) {
        return map.get(key);
    }

    public IPdxScript get(String key) {
        return getRaw(key);
    }

    public PdxScriptObject getObject(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptObject) {
            use(key, OBJECT);
            return (PdxScriptObject) o;
        }
        useWrongly(key, OBJECT);
        return null;
    }

    public PdxScriptList getImplicitList(String key) {
        IPdxScript s = get(key);
        if (s instanceof PdxScriptList && ((PdxScriptList) s).getMode() == PdxScriptList.Mode.IMPLICIT) {
            use(key, IMPLICIT_LIST);
            return (PdxScriptList) s;
        } else if (s != null) {
            use(key, getTypeString(s));
            return PdxScriptList.builder().add(s).build(PdxScriptList.Mode.IMPLICIT, PdxRelation.EQUALS);
        }
        useWrongly(key, IMPLICIT_LIST);
        return PdxScriptList.builder().build(PdxScriptList.Mode.IMPLICIT, PdxRelation.EQUALS);
    }

    public PdxScriptList getList(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptList) {
            use(key, LIST);
            return (PdxScriptList) o;
        }
        useWrongly(key, LIST);
        return null;
    }

    public Object getValue(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            use(key, getTypeString(o));
            return v;
        }
        useWrongly(key, NULL);
        return null;
    }

    public String getString(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof String) {
                use(key, STRING);
                return (String) v;
            }
        }
        useWrongly(key, STRING);
        return null;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Boolean) {
                use(key, BOOLEAN);
                return (boolean) v;
            }
        }
        useWrongly(key, BOOLEAN);
        return def;
    }

    public int getUnsignedInt(String key) {
        return getUnsignedInt(key, 0);
    }

    public int getUnsignedInt(String key, int def) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Integer) {
                use(key, UNSIGNED_INT);
                return (int) v;
            } else if (v instanceof Long) {
                long l = (long) v;
                if (l >= 0 && l <= UNSIGNED_INT_MAX_LONG) {
                    use(key, UNSIGNED_INT);
                    return (int) l;
                }
            }
        }
        useWrongly(key, UNSIGNED_INT);
        return def;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Integer) {
                use(key, INT);
                return (int) v;
            }
        }
        useWrongly(key, INT);
        return def;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long def) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Long || v instanceof Integer) {
                use(key, LONG);
                return ((Number) v).longValue();
            }
        }
        useWrongly(key, LONG);
        return def;
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double def) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Double) {
                use(key, DOUBLE);
                return (double) v;
            }
        }
        useWrongly(key, DOUBLE);
        return def;
    }

    public Date getDate(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Date) {
                use(key, DATE);
                return (Date) v;
            }
        }
        useWrongly(key, DATE);
        return null;
    }

    public <T> T getAs(Function<PdxScriptObject, T> fct) {
        return fct.apply(this);
    }

    public <V> ImmutableIntObjectMap<V> getAsIntObjectMap(ToIntFunction<String> keyFct, Function<IPdxScript, V> valueFct) {
        MutableIntObjectMap<V> map = IntObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            map.put(keyFct.applyAsInt(oldK), valueFct.apply(oldV));
            use(oldK, getTypeString(oldV));
        });
        return map.toImmutable();
    }

    public <V> ImmutableLongObjectMap<V> getAsLongObjectMap(ToLongFunction<String> keyFct, Function<IPdxScript, V> valueFct) {
        MutableLongObjectMap<V> map = LongObjectMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            map.put(keyFct.applyAsLong(oldK), valueFct.apply(oldV));
            use(oldK, getTypeString(oldV));
        });
        return map.toImmutable();
    }

    public <K> ImmutableObjectBooleanMap<K> getAsObjectBooleanMap(Function<String, K> keyFct, Predicate<IPdxScript> valueFct) {
        MutableObjectBooleanMap<K> map = ObjectBooleanMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                map.put(k, valueFct.test(oldV));
                use(oldK, getTypeString(oldV));
            }
        });
        return map.toImmutable();
    }

    public <K> ImmutableObjectIntMap<K> getAsObjectIntMap(Function<String, K> keyFct, ToIntFunction<IPdxScript> valueFct) {
        MutableObjectIntMap<K> map = ObjectIntMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                map.put(k, valueFct.applyAsInt(oldV));
                use(oldK, getTypeString(oldV));
            }
        });
        return map.toImmutable();
    }

    public <K> ImmutableObjectDoubleMap<K> getAsObjectDoubleMap(Function<String, K> keyFct, ToDoubleFunction<IPdxScript> valueFct) {
        MutableObjectDoubleMap<K> map = ObjectDoubleMaps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                map.put(k, valueFct.applyAsDouble(oldV));
                use(oldK, getTypeString(oldV));
            }
        });
        return map.toImmutable();
    }

    public <K, V> ImmutableMap<K, V> getAsMap(Function<String, K> keyFct, Function<IPdxScript, V> valueFct) {
        MutableMap<K, V> map = Maps.mutable.empty();
        this.map.forEachKeyValue((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                map.put(k, valueFct.apply(oldV));
                use(oldK, getTypeString(oldV));
            }
        });
        return map.toImmutable();
    }

    public ImmutableMultimap<String, String> getErrors() {
        MutableMultimap<String, String> errors = Multimaps.mutable.set.empty();
        map.forEachKeyValue((key, s) -> {
            String type = getTypeString(s);
            if (!type.equals(NULL)) {
                MutableCollection<String> toAdd = wronglyUsed != null ? wronglyUsed.get(key) : null;
                if (toAdd != null && !toAdd.isEmpty()) {
                    errors.putAll(key, toAdd.collect(s1 -> "wrongly_used_as=" + s1 + SLASH_CHAR + "was=" + type));
                }
            }
            boolean id = DIGITS_PATTERN.matcher(key).matches();
            MutableCollection<String> usages = used != null ? used.get(key) : null;
            if (!id && (!type.equals(NULL) || (usages != null && !usages.isEmpty())) && (usages == null || (!usages.contains(type) && (!type.equals(INT) || (!usages.contains(UNSIGNED_INT) && !usages.contains(LONG)))))) {
                errors.put(key, "unused=" + type + (usages != null && !usages.isEmpty() ? SLASH_CHAR + "was_used_as=" + usages : EMPTY));
            } else {
                if (s instanceof PdxScriptObject) {
                    ((PdxScriptObject) s).getErrors().forEachKeyMultiValues((k, v) -> errors.putAll((id ? EMPTY : key + DOT_CHAR) + k, v));
                } else if (s instanceof PdxScriptList) {
                    ((PdxScriptList) s).getErrors().forEachKeyMultiValues((k, v) -> errors.putAll(key + DOT_CHAR + k, v));
                }
            }
        });
        return errors.toImmutable();
    }

    public int size() {
        return map.size();
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if ((root && indent != 0) || (root && key != null)) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder();

        IPdxScript.listObjectOpen(indent, root, key, b, relation, map.isEmpty());


        // TODO: Print alphabetically sorted by k
        map.forEachKeyValue((k, v) -> {
            b.append(v.toPdxScript(root ? indent : indent + 1, false, k));
            b.append(LINE_FEED);
        });

        IPdxScript.listObjectClose(indent, root, b, map.isEmpty());

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdxScriptObject)) {
            return false;
        }
        PdxScriptObject that = (PdxScriptObject) o;
        return relation == that.relation && Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, map);
    }

    @Override
    public String toString() {
        return "PdxScriptObject{" +
                "relation=" + relation +
                ", map=" + map +
                '}';
    }

    public static class Builder {

        private static final ImmutableMap<PdxRelation, PdxScriptObject> EMPTY_CACHE;

        static {
            Map<PdxRelation, PdxScriptObject> map = new EnumMap<>(PdxRelation.class);
            Arrays.stream(PdxRelation.values()).forEach(relation -> map.put(relation, new PdxScriptObject(relation, Maps.immutable.empty())));
            EMPTY_CACHE = Maps.adapt(map).toImmutable();
        }

        private final MutableMap<String, IPdxScript> map;

        public Builder() {
            this.map = Maps.mutable.empty();
        }

        public PdxScriptObject build(PdxRelation relation) {
            if (map.isEmpty()) {
                return EMPTY_CACHE.get(relation);
            }
            return new PdxScriptObject(relation, map.toImmutable());
        }

        public Builder add(String key, IPdxScript value) {
            String interned = key.intern();
            IPdxScript object = map.get(interned);
            if (object == null) {
                map.put(interned, value);
            } else if (object.canAppend(value)) {
                map.put(interned, object.append(value));
            } else {
                throw new UnsupportedOperationException("key=" + interned + ", existing=" + object + ", appendix=" + value);
            }
            return this;
        }
    }
}

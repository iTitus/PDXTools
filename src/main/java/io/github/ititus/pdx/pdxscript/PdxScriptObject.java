package io.github.ititus.pdx.pdxscript;

import com.koloboke.collect.map.hash.HashObjObjMap;
import com.koloboke.collect.map.hash.HashObjObjMaps;
import com.koloboke.collect.set.hash.HashObjSets;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PdxScriptObject implements IPdxScript {

    private final Map<String, Set<String>> used;
    private final Map<String, Set<String>> wronglyUsed;

    private final PdxRelation relation;
    private final Map<String, IPdxScript> map;

    public PdxScriptObject(PdxRelation relation, Map<String, IPdxScript> map) {
        this.used = HashObjObjMaps.newUpdatableMap();
        this.wronglyUsed = HashObjObjMaps.newUpdatableMap();

        this.relation = relation;
        this.map = HashObjObjMaps.newImmutableMap(map);
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
        used.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(usage);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(OBJECT);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(IMPLICIT_LIST);
        return PdxScriptList.builder().build(PdxScriptList.Mode.IMPLICIT, PdxRelation.EQUALS);
    }

    public PdxScriptList getList(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptList) {
            use(key, LIST);
            return (PdxScriptList) o;
        }
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(LIST);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(STRING);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(BOOLEAN);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(UNSIGNED_INT);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(INT);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(LONG);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(DOUBLE);
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
        wronglyUsed.computeIfAbsent(key, k -> HashObjSets.newUpdatableSet()).add(DATE);
        return null;
    }

    public <T> T getAs(Function<PdxScriptObject, T> fct) {
        return fct.apply(this);
    }

    public <K, V> Map<K, V> getAsMap(Function<String, K> keyFct, Function<IPdxScript, V> valueFct) {
        HashObjObjMap<K, V> map = HashObjObjMaps.newUpdatableMap();
        this.map.forEach((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                V v = valueFct.apply(oldV);
                map.put(k, v);
                use(oldK, getTypeString(oldV));
            }
        });
        return map;
    }

    public Map<String, Set<String>> getErrors() {
        Map<String, Set<String>> errors = HashObjObjMaps.newUpdatableMap();
        for (Map.Entry<String, IPdxScript> entry : map.entrySet()) {
            String type = getTypeString(entry.getValue());
            Set<String> toAdd = wronglyUsed.get(entry.getKey());
            if (toAdd != null && !toAdd.isEmpty()) {
                errors.computeIfAbsent(entry.getKey(), k -> HashObjSets.newUpdatableSet()).addAll(toAdd.stream().map(s -> "wrongly_used_as=" + s + SLASH_CHAR + "was=" + type).collect(Collectors.toSet()));
            }
            boolean id = DIGITS_PATTERN.matcher(entry.getKey()).matches();
            Set<String> usages = used.getOrDefault(entry.getKey(), null);
            if (!id && (usages == null || (!usages.contains(type) && (!type.equals(INT) || (!usages.contains(UNSIGNED_INT) && !usages.contains(LONG)))))) {
                errors.computeIfAbsent(entry.getKey(), k -> HashObjSets.newUpdatableSet()).add("unused=" + type + (usages != null && !usages.isEmpty() ? SLASH_CHAR + "was_used_as=" + usages : EMPTY));
            } else {
                if (entry.getValue() instanceof PdxScriptObject) {
                    ((PdxScriptObject) entry.getValue()).getErrors().forEach((k, v) -> {
                        if (v != null && !v.isEmpty()) {
                            errors.computeIfAbsent((id ? EMPTY : entry.getKey() + DOT_CHAR) + k, k_ -> HashObjSets.newUpdatableSet()).addAll(v);
                        }
                    });
                } else if (entry.getValue() instanceof PdxScriptList) {
                    Queue<PdxScriptList> lists = new LinkedList<>();
                    lists.offer((PdxScriptList) entry.getValue());
                    while (lists.peek() != null) {
                        PdxScriptList l = lists.poll();
                        for (int i = 0; i < l.size(); i++) {
                            IPdxScript s = l.get(i);
                            if (s instanceof PdxScriptList && !lists.contains(s)) {
                                lists.offer((PdxScriptList) s);
                            } else if (s instanceof PdxScriptObject) {
                                ((PdxScriptObject) s).getErrors().forEach((k, v) -> {
                                    if (v != null && !v.isEmpty()) {
                                        errors.computeIfAbsent(entry.getKey() + DOT_CHAR + k, k_ -> HashObjSets.newUpdatableSet()).addAll(v);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        return errors;
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

        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEachOrdered(entry -> {
            b.append(entry.getValue().toPdxScript(root ? indent : indent + 1, false, entry.getKey()));
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

        private final Map<String, IPdxScript> map;

        public Builder() {
            this.map = HashObjObjMaps.newUpdatableMap();
        }

        public PdxScriptObject build(PdxRelation relation) {
            return new PdxScriptObject(relation, map);
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

package io.github.ititus.stellaris.analyser.pdxscript;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PdxScriptObject implements IPdxScript {

    private static final String OBJECT = "object";
    private static final String LIST = "list";
    private static final String STRING = "value:string";
    private static final String BOOLEAN = "value:boolean";
    private static final String INT = "value:int";
    private static final String LONG = "value:long";
    private static final String DOUBLE = "value:double";
    private static final String DATE = "value:date";
    private static final String NULL = "null";

    private final Map<String, String> used = new HashMap<>();
    private final Map<String, Set<String>> wronglyUsed = new HashMap<>();
    private final Map<String, IPdxScript> map;

    public PdxScriptObject(Map<String, IPdxScript> map) {
        this.map = new HashMap<>(map);
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

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public IPdxScript getRaw(String key) {
        return map.get(key);
    }

    public IPdxScript get(String key) {
        IPdxScript o = getRaw(key);
        /*if (o instanceof PdxScriptList) {
            PdxScriptList l = (PdxScriptList) o;
            if (l.size() == 1) {
                return l.get(0);
            }
        }*/
        return o;
    }

    public PdxScriptObject getObject(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptObject) {
            used.put(key, OBJECT);
            return (PdxScriptObject) o;
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(OBJECT);
        return null;
    }

    public PdxScriptList getList(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptList) {
            used.put(key, LIST);
            return (PdxScriptList) o;
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(LIST);
        return null;
    }

    public String getString(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof String) {
                used.put(key, STRING);
                return (String) v;
            }
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(STRING);
        return null;
    }

    public boolean getBoolean(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Boolean) {
                used.put(key, BOOLEAN);
                return (boolean) v;
            }
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(BOOLEAN);
        return false;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Integer) {
                used.put(key, INT);
                return (int) v;
            }
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(INT);
        return def;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long def) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Long) {
                used.put(key, getTypeString(o));
                return (long) v;
            }
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(LONG);
        return def;
    }

    public double getDouble(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Double) {
                used.put(key, getTypeString(o));
                return (double) v;
            }
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(DOUBLE);
        return 0;
    }

    public Date getDate(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Date) {
                used.put(key, DATE);
                return (Date) v;
            }
        }
        wronglyUsed.computeIfAbsent(key, k -> new HashSet<>()).add(DATE);
        return null;
    }

    public <T> T getAs(Function<PdxScriptObject, T> fct) {
        return fct.apply(this);
    }

    public <K, V> Map<K, V> getAsMap(Function<String, K> keyFct, Function<IPdxScript, V> valueFct) {
        Map<K, V> map = new HashMap<>();
        this.map.forEach((oldK, oldV) -> {
            K k = keyFct.apply(oldK);
            if (k != null) {
                V v = valueFct.apply(oldV);
                map.put(k, v);
                used.put(oldK, NULL);
            }
        });
        return map;
        // return this.map.entrySet().stream().collect(Collectors.toMap(e -> keyFct.apply(e.getKey()), e -> valueFct.apply(e.getValue())));
    }

    public Map<String, Set<String>> getErrors() {
        Map<String, Set<String>> errors = new HashMap<>();
        for (Map.Entry<String, IPdxScript> entry : map.entrySet()) {
            String type = getTypeString(entry.getValue());
            Set<String> toAdd = wronglyUsed.get(entry.getKey());
            if (toAdd != null && !toAdd.isEmpty()) {
                errors.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(toAdd.stream().map(s -> "wrongly_used=" + s + "/" + type).collect(Collectors.toSet()));
            }
            if (entry.getKey().matches("[0-9]+")) {
                continue;
            }
            if (!used.containsKey(entry.getKey())) {
                errors.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).add("unused=" + type);
            } else {
                if (entry.getValue() instanceof PdxScriptObject) {
                    ((PdxScriptObject) entry.getValue()).getErrors().forEach((k, v) -> {
                        if (v != null && !v.isEmpty()) {
                            errors.computeIfAbsent(entry.getKey() + "." + k, k_ -> new HashSet<>()).addAll(v);
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
                                        errors.computeIfAbsent(entry.getKey() + "." + k, k_ -> new HashSet<>()).addAll(v);
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
    public IPdxScript append(IPdxScript object) {
        return PdxScriptList.builder().add(this).add(object).build();
    }

    @Override
    public String toPdxScript(int indent, boolean bound, boolean indentFirst) {
        StringBuilder b = new StringBuilder();

        if (bound) {
            if (indentFirst) {
                b.append(PdxScriptParser.indent(indent));
            }
            b.append('{');
            if (map.size() > 0) {
                b.append('\n');
            }
        }

        map.forEach((s, object) -> {
            if (bound) {
                b.append(PdxScriptParser.indent(indent + 1));
            } else {
                b.append(PdxScriptParser.indent(indent));
            }
            b.append(PdxScriptParser.quoteIfNecessary(s));
            b.append('=');
            b.append(object.toPdxScript(bound ? indent + 1 : indent, true, false));
            b.append('\n');
        });

        if (bound) {
            if (map.size() > 0) {
                b.append(PdxScriptParser.indent(indent));
            }
            b.append('}');
        }
        return b.toString();
    }

    @Override
    public String toString() {
        return "map = [" + map + "]";
    }

    private static String getTypeString(IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return OBJECT;
        }
        if (s instanceof PdxScriptList) {
            return LIST;
        }
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Date) {
                return DATE;
            } else if (v instanceof Double) {
                return DOUBLE;
            } else if (v instanceof Long) {
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

    public static class Builder {

        private final Map<String, IPdxScript> map;

        public Builder() {
            map = new HashMap<>();
        }

        public PdxScriptObject build() {
            return new PdxScriptObject(map);
        }

        public Builder add(String key, IPdxScript value) {
            IPdxScript object = map.get(key);
            if (object == null) {
                map.put(key, value);
            } else if (object.canAppend(value)) {
                map.put(key, object.append(value));
            } else {
                throw new UnsupportedOperationException("existing=" + object + ", appendix=" + value);
            }
            return this;
        }
    }

}

package io.github.ititus.stellaris.analyser.pdxscript;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PdxScriptObject implements IPdxScript {

    private final Map<String, IPdxScript> map;

    public PdxScriptObject(Map<String, IPdxScript> map) {
        this.map = new HashMap<>(map);
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
            return (PdxScriptObject) o;
        }
        return null;
    }

    public PdxScriptList getList(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptList) {
            return (PdxScriptList) o;
        }
        return null;
    }

    public String getString(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof String) {
                return (String) v;
            }
        }
        return null;
    }

    public boolean getBoolean(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Boolean) {
                return (boolean) v;
            }
        }
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
                return (int) v;
            }
        }
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
                return (long) v;
            }
        }
        return def;
    }

    public double getDouble(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Double) {
                return (double) v;
            }
        }
        return 0;
    }

    public Date getDate(String key) {
        IPdxScript o = get(key);
        if (o instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) o).getValue();
            if (v instanceof Date) {
                return (Date) v;
            }
        }
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
            }
        });
        return map;
        //return this.map.entrySet().stream().collect(Collectors.toMap(e -> keyFct.apply(e.getKey()), e -> valueFct.apply(e.getValue())));
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
        StringBuilder sb = new StringBuilder();

        if (bound) {
            if (indentFirst) {
                sb.append(PdxScriptParser.indent(indent));
            }
            sb.append('{');
            if (map.size() > 0) {
                sb.append('\n');
            }
        }

        map.forEach((s, object) -> {
            if (bound) {
                sb.append(PdxScriptParser.indent(indent + 1));
            } else {
                sb.append(PdxScriptParser.indent(indent));
            }
            sb.append(PdxScriptParser.quoteIfNecessary(s));
            sb.append('=');
            sb.append(object.toPdxScript(bound ? indent + 1 : indent, true, false));
            sb.append('\n');
        });

        if (bound) {
            if (map.size() > 0) {
                sb.append(PdxScriptParser.indent(indent));
            }
            sb.append('}');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "map = [" + map + "]";
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

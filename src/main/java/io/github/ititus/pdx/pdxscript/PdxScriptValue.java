package io.github.ititus.pdx.pdxscript;

import java.time.LocalDate;
import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

public final class PdxScriptValue implements IPdxScript {

    private final PdxRelation relation;
    private final Object value;

    private PdxScriptValue(PdxRelation relation, Object value) {
        if (relation == null || (value != null && !(value instanceof Boolean) && !(value instanceof Number) && !(value instanceof LocalDate) && !(value instanceof PdxColor) && !(value instanceof String))) {
            throw new IllegalArgumentException(Objects.toString(value));
        }

        this.relation = relation;
        if (value instanceof String) {
            this.value = value; // ((String) value).intern();
        } else if (value instanceof LocalDate && NULL_DATE.equals(value)) {
            this.value = NULL_DATE;
        } else {
            this.value = value;
        }
    }

    public static PdxScriptValue of(Object value) {
        return of(PdxRelation.EQUALS, value);
    }

    public static PdxScriptValue of(PdxRelation relation, Object value) {
        return new PdxScriptValue(relation, value);
    }

    @Override
    public PdxRelation getRelation() {
        return relation;
    }

    public Object getValue() {
        return value;
    }

    public <T> T expectNull() {
        if (value == null) {
            return null;
        }

        throw new IllegalStateException("expected null but got " + value);
    }

    public boolean expectBoolean() {
        if (value instanceof Boolean) {
            return (boolean) value;
        }

        throw new IllegalStateException("expected boolean but got " + value);
    }

    public int expectInt() {
        if (value instanceof Integer) {
            return (int) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException ignored) {
            }
        }

        throw new IllegalStateException("expected int but got " + value);
    }

    public int expectUnsignedInt() {
        if (value instanceof Integer) {
            return (int) value;
        } else if (value instanceof Long) {
            long l = (long) value;
            if (l >= 0 && l <= UNSIGNED_INT_MAX_LONG) {
                return (int) l;
            }
        } else if (value instanceof String) {
            try {
                return Integer.parseUnsignedInt((String) value);
            } catch (NumberFormatException ignored) {
            }
        }

        throw new IllegalStateException("expected unsigned but got " + value);
    }

    public long expectLong() {
        if (value instanceof Long || value instanceof Integer) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException ignored) {
            }
        }

        throw new IllegalStateException("expected number but got " + value);
    }

    public double expectDouble() {
        if (value instanceof Double || value instanceof Long || value instanceof Integer) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }

        throw new IllegalStateException("expected double but got " + value);
    }

    public Number expectNumber() {
        if (value instanceof Number) {
            return (Number) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException ignored) {
            }
            try {
                return Integer.parseUnsignedInt((String) value);
            } catch (NumberFormatException ignored) {
            }
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException ignored) {
            }
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }

        throw new IllegalStateException("expected number but got " + value);
    }

    public String expectString() {
        if (value instanceof String) {
            return (String) value;
        }

        throw new IllegalStateException("expected string but got " + value);
    }

    public LocalDate expectDate() {
        if (value instanceof LocalDate) {
            return (LocalDate) value;

        }

        throw new IllegalStateException("expected date but got " + value);
    }

    public PdxColor expectColor() {
        if (value instanceof PdxColor) {
            return (PdxColor) value;
        }

        throw new IllegalStateException("expected color but got " + value);
    }

    @Override
    public String toPdxScript(int indent, boolean root, String key) {
        if (root) {
            throw new IllegalArgumentException();
        }

        StringBuilder b = new StringBuilder(PdxHelper.indent(indent));

        if (key != null) {
            b.append(PdxScriptParser.quoteIfNecessary(key));
            b.append(relation.getSign());
        }

        if (value == null) {
            b.append(NONE);
        } else if (value instanceof Boolean) {
            b.append((boolean) value ? YES : NO);
        } else if (value instanceof LocalDate) {
            b.append(PdxScriptParser.quote(((LocalDate) value).format(DTF)));
        } else if (value instanceof String) {
            b.append(PdxScriptParser.quote((String) value));
        } else if (value instanceof PdxColor) {
            b.append(((PdxColor) value).toPdxScript());
        } else if (value instanceof Number) {
            b.append(value);
        } else {
            throw new IllegalArgumentException();
        }

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptValue)) {
            return false;
        }

        PdxScriptValue that = (PdxScriptValue) o;
        return relation == that.relation && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relation, value);
    }

    @Override
    public String toString() {
        return "PdxScriptValue{relation=" + relation + ", value=" + value + '}';
    }
}

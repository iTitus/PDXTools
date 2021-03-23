package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.pdxscript.internal.*;

import java.time.LocalDate;
import java.util.Objects;

public abstract class PdxScriptValue extends BasePdxScript {

    protected PdxScriptValue(PdxRelation relation) {
        super(relation);
    }

    public static PdxScriptValue of(Object value) {
        return of(PdxRelation.EQUALS, value);
    }

    public static PdxScriptValue of(PdxRelation relation, Object value) {
        Objects.requireNonNull(relation);
        if (value == null) {
            return PdxScriptNull.of(relation);
        } else if (value instanceof Boolean) {
            return PdxScriptBoolean.of(relation, (boolean) value);
        } else if (value instanceof Integer) {
            return PdxScriptInt.of(relation, (int) value);
        } else if (value instanceof Long) {
            return PdxScriptLong.of(relation, (long) value);
        } else if (value instanceof Double) {
            return PdxScriptDouble.of(relation, (double) value);
        } else if (value instanceof String) {
            return PdxScriptString.of(relation, (String) value);
        } else if (value instanceof LocalDate) {
            return PdxScriptDate.of(relation, (LocalDate) value);
        } else if (value instanceof PdxColor) {
            return PdxScriptColor.of(relation, (PdxColor) value);
        }

        throw new IllegalArgumentException("unsupported value type: " + value.getClass().getTypeName());
    }

    public abstract Object getValue();

    protected abstract String valueToPdxString();

    public <T> T expectNull() {
        throw new IllegalStateException("expected null but was " + getValue());
    }

    public boolean expectBoolean() {
        throw new IllegalStateException("expected boolean but was " + getValue());
    }

    public int expectInt() {
        throw new IllegalStateException("expected int but was " + getValue());
    }

    public int expectUnsignedInt() {
        throw new IllegalStateException("expected unsigned but was " + getValue());
    }

    public long expectLong() {
        throw new IllegalStateException("expected number but was " + getValue());
    }

    public double expectDouble() {
        throw new IllegalStateException("expected double but was " + getValue());
    }

    public Number expectNumber() {
        try {
            return expectInt();
        } catch (Exception ignored) {
        }
        try {
            return expectUnsignedInt();
        } catch (Exception ignored) {
        }
        try {
            return expectLong();
        } catch (Exception ignored) {
        }
        try {
            return expectDouble();
        } catch (Exception ignored) {
        }

        throw new IllegalStateException("expected number but was " + getValue());
    }

    public String expectString() {
        throw new IllegalStateException("expected string but was " + getValue());
    }

    public LocalDate expectDate() {
        throw new IllegalStateException("expected date but was " + getValue());
    }

    public PdxColor expectColor() {
        throw new IllegalStateException("expected color but was " + getValue());
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

        return b.append(valueToPdxString()).toString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{relation=" + relation + ", value=" + getValue() + '}';
    }
}

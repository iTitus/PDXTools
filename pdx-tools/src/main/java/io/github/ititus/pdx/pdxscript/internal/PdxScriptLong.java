package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

public final class PdxScriptLong extends PdxScriptValue {

    private final long value;

    private PdxScriptLong(PdxRelation relation, long value) {
        super(relation);
        this.value = value;
    }

    public static PdxScriptLong of(PdxRelation relation, long value) {
        return new PdxScriptLong(relation, value);
    }

    @Override
    public String getTypeString() {
        if (value >= Integer.MIN_VALUE && value < 0) {
            return NEG_INT;
        } else if (value >= 0 && value <= Integer.MAX_VALUE) {
            return INT;
        } else if (value > Integer.MAX_VALUE && value <= UNSIGNED_INT_MAX_LONG) {
            return U_INT;
        }

        return LONG;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    protected String valueToPdxString() {
        return Long.toString(value);
    }

    @Override
    public int expectUnsignedInt() {
        if (value >= 0 && value <= UNSIGNED_INT_MAX_LONG) {
            return (int) value;
        }

        return super.expectUnsignedInt();
    }

    @Override
    public long expectLong() {
        return value;
    }

    @Override
    public double expectDouble() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptLong)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }

        PdxScriptLong that = (PdxScriptLong) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}

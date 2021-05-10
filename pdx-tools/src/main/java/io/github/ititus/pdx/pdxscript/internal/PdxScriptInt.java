package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.INT;
import static io.github.ititus.pdx.pdxscript.PdxConstants.NEG_INT;

public final class PdxScriptInt extends PdxScriptValue {

    private final int value;

    private PdxScriptInt(PdxRelation relation, int value) {
        super(relation);
        this.value = value;
    }

    public static PdxScriptInt of(PdxRelation relation, int value) {
        return new PdxScriptInt(relation, value);
    }

    @Override
    public String getTypeString() {
        return value < 0 ? NEG_INT : INT;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    protected String valueToPdxString() {
        return Integer.toString(value);
    }

    @Override
    public int expectInt() {
        return value;
    }

    @Override
    public int expectUnsignedInt() {
        return value;
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
        } else if (!(o instanceof PdxScriptInt)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }

        PdxScriptInt that = (PdxScriptInt) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}

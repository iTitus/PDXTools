package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DOUBLE;

public final class PdxScriptDouble extends PdxScriptValue {

    private final double value;

    private PdxScriptDouble(PdxRelation relation, double value) {
        super(relation);
        this.value = value;
    }

    public static PdxScriptDouble of(PdxRelation relation, double value) {
        return new PdxScriptDouble(relation, value);
    }

    @Override
    public String getTypeString() {
        return DOUBLE;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    protected String valueToPdxString() {
        return Double.toString(value);
    }

    @Override
    public double expectDouble() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptDouble)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }

        PdxScriptDouble that = (PdxScriptDouble) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}

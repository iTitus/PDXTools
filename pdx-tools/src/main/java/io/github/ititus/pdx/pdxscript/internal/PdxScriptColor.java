package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxColor;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.COLOR;

public final class PdxScriptColor extends PdxScriptValue {

    private final PdxColor value;

    private PdxScriptColor(PdxRelation relation, PdxColor value) {
        super(relation);
        this.value = value;
    }

    public static PdxScriptColor of(PdxRelation relation, PdxColor value) {
        return new PdxScriptColor(relation, value);
    }

    @Override
    public String getTypeString() {
        return COLOR;
    }

    @Override
    public PdxColor getValue() {
        return value;
    }

    @Override
    protected String valueToPdxString() {
        return value.toPdxScript();
    }

    @Override
    public PdxColor expectColor() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptColor)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }

        PdxScriptColor that = (PdxScriptColor) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}

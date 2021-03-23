package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.STRING;

public final class PdxScriptString extends PdxScriptValue {

    private final String value;

    private PdxScriptString(PdxRelation relation, String value) {
        super(relation);
        this.value = value;
    }

    public static PdxScriptString of(PdxRelation relation, String value) {
        return new PdxScriptString(relation, value);
    }

    @Override
    public String getTypeString() {
        return STRING;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    protected String valueToPdxString() {
        return PdxScriptParser.quote(value);
    }

    @Override
    public int expectInt() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            return super.expectInt();
        }
    }

    @Override
    public int expectUnsignedInt() {
        try {
            return Integer.parseUnsignedInt(value);
        } catch (NumberFormatException ignored) {
            return super.expectUnsignedInt();
        }
    }

    @Override
    public long expectLong() {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ignored) {
            return super.expectLong();
        }
    }

    @Override
    public double expectDouble() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
            return super.expectDouble();
        }
    }

    @Override
    public String expectString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptString)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }

        PdxScriptString that = (PdxScriptString) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}

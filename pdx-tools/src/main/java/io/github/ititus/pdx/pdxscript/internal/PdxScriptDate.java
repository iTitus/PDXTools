package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.time.LocalDate;
import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DATE;
import static io.github.ititus.pdx.pdxscript.PdxConstants.DTF;

public final class PdxScriptDate extends PdxScriptValue {

    private final LocalDate value;

    private PdxScriptDate(PdxRelation relation, LocalDate value) {
        super(relation);
        this.value = value;
    }

    public static PdxScriptDate of(PdxRelation relation, LocalDate value) {
        return new PdxScriptDate(relation, value);
    }

    @Override
    public String getTypeString() {
        return DATE;
    }

    @Override
    public LocalDate getValue() {
        return value;
    }

    @Override
    protected String valueToPdxString() {
        return PdxScriptParser.quoteUnsafe(value.format(DTF));
    }

    @Override
    public LocalDate expectDate() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptDate)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }

        PdxScriptDate that = (PdxScriptDate) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}

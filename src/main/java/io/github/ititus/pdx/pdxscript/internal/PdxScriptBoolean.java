package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

public final class PdxScriptBoolean extends PdxScriptValue {

    private static final Map<PdxRelation, PdxScriptBoolean> TRUE_CACHE = new EnumMap<>(PdxRelation.class);
    private static final Map<PdxRelation, PdxScriptBoolean> FALSE_CACHE = new EnumMap<>(PdxRelation.class);

    private final boolean value;

    private PdxScriptBoolean(PdxRelation relation, boolean value) {
        super(relation);
        this.value = value;
    }

    public static PdxScriptBoolean of(PdxRelation relation, boolean value) {
        if (value) {
            return TRUE_CACHE.computeIfAbsent(relation, r -> new PdxScriptBoolean(r, true));
        }

        return FALSE_CACHE.computeIfAbsent(relation, r -> new PdxScriptBoolean(r, false));
    }

    @Override
    public String getTypeString() {
        return BOOLEAN;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    protected String valueToPdxString() {
        return value ? YES : NO;
    }

    @Override
    public boolean expectBoolean() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptBoolean)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }

        PdxScriptBoolean that = (PdxScriptBoolean) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}

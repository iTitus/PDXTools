package io.github.ititus.pdx.shared.scope;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

public final class ScopeHelper {

    private ScopeHelper() {
    }

    public static boolean compare(int value, PdxScriptValue v) {
        PdxRelation r = v.getRelation();
        return r.compare(value, v.expectInt());
    }

    public static boolean compare(long value, PdxScriptValue v) {
        PdxRelation r = v.getRelation();
        return r.compare(value, v.expectLong());
    }

    public static boolean compare(double value, PdxScriptValue v) {
        PdxRelation r = v.getRelation();
        return r.compare(value, v.expectDouble());
    }
}

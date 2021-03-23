package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;

import java.util.EnumMap;
import java.util.Map;

import static io.github.ititus.pdx.pdxscript.PdxConstants.NONE;
import static io.github.ititus.pdx.pdxscript.PdxConstants.NULL;

public final class PdxScriptNull extends PdxScriptValue {

    private static final Map<PdxRelation, PdxScriptNull> CACHE = new EnumMap<>(PdxRelation.class);

    private PdxScriptNull(PdxRelation relation) {
        super(relation);
    }

    public static PdxScriptNull of(PdxRelation relation) {
        return CACHE.computeIfAbsent(relation, PdxScriptNull::new);
    }

    @Override
    public String getTypeString() {
        // TODO: maybe use value:null as type
        return NULL;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    protected String valueToPdxString() {
        return NONE;
    }

    @Override
    public <T> T expectNull() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxScriptNull)) {
            return false;
        }

        return super.equals(o);
    }
}

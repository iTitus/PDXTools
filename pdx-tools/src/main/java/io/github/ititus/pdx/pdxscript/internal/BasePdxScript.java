package io.github.ititus.pdx.pdxscript.internal;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;

public abstract class BasePdxScript implements IPdxScript {

    protected final PdxRelation relation;

    protected BasePdxScript(PdxRelation relation) {
        this.relation = relation;
    }

    @Override
    public PdxRelation getRelation() {
        return relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof BasePdxScript)) {
            return false;
        }

        BasePdxScript that = (BasePdxScript) o;
        return relation == that.relation;
    }

    @Override
    public int hashCode() {
        return relation.hashCode();
    }
}

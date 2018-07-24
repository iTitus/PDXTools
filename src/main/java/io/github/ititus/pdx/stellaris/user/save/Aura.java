package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Aura {

    private final AuraId auraId;
    private final AuraSource auraSource;

    public Aura(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.auraId = o.getObject("id").getAs(AuraId::new);
        this.auraSource = o.getObject("source").getAs(AuraSource::new);
    }

    public Aura(AuraId auraId, AuraSource auraSource) {
        this.auraId = auraId;
        this.auraSource = auraSource;
    }

    public AuraId getAuraId() {
        return auraId;
    }

    public AuraSource getAuraSource() {
        return auraSource;
    }
}

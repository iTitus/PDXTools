package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Aura {

    public final AuraId auraId;
    public final AuraSource auraSource;

    public Aura(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.auraId = o.getObjectAs("id", AuraId::new);
        this.auraSource = o.getObjectAs("source", AuraSource::new);
    }
}

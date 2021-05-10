package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Modules {

    public final StandardEconomyModule standardEconomyModule;

    public Modules(PdxScriptObject o) {
        this.standardEconomyModule = o.getObjectAsNullOr("standard_economy_module", StandardEconomyModule::new);
        // TODO: this
    }
}

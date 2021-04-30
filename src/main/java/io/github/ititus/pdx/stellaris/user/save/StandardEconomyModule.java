package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.shared.Resources;

public class StandardEconomyModule {

    public final Resources resources;

    public StandardEconomyModule(PdxScriptObject o) {
        this.resources = o.getObjectAs("resources", Resources::new);
    }
}

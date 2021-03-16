package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Traits {

    public final ImmutableList<String> traits;

    public Traits(PdxScriptObject o) {
        this.traits = o.getImplicitListAsStringList("trait");
    }
}

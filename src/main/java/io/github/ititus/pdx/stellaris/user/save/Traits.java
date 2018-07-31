package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Traits {

    private final ImmutableList<String> traits;

    public Traits(PdxScriptObject o) {
        this.traits = o.getImplicitList("trait").getAsStringList();
    }

    public Traits(ImmutableList<String> traits) {
        this.traits = traits;
    }

    public ImmutableList<String> getTraits() {
        return traits;
    }
}

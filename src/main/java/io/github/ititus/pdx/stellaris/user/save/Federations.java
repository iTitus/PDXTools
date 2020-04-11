package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Federations {

    private final ImmutableIntObjectMap<Federation> federations;

    public Federations(PdxScriptObject o) {
        this.federations = o.getAsIntObjectMap(Federation::new);
    }

    public Federations(ImmutableIntObjectMap<Federation> federations) {
        this.federations = federations;
    }

    public ImmutableIntObjectMap<Federation> getFederations() {
        return federations;
    }
}

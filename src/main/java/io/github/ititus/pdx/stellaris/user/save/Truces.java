package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Truces {

    private final ImmutableIntObjectMap<Truce> truces;

    public Truces(PdxScriptObject o) {
        this.truces = o.getAsIntObjectMap(Truce::new);
    }

    public Truces(ImmutableIntObjectMap<Truce> truces) {
        this.truces = truces;
    }

    public ImmutableIntObjectMap<Truce> getTruces() {
        return truces;
    }
}

package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class Bypasses {

    private final ImmutableIntObjectMap<Bypass> bypasses;

    public Bypasses(PdxScriptObject o) {
        this.bypasses = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(Bypass::new));
    }

    public Bypasses(ImmutableIntObjectMap<Bypass> bypasses) {
        this.bypasses = bypasses;
    }

    public ImmutableIntObjectMap<Bypass> getBypasses() {
        return bypasses;
    }
}

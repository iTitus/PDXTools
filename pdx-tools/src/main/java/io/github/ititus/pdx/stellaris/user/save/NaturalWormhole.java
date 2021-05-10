package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class NaturalWormhole {

    public final Coordinate coordinate;
    public final int bypass;

    public NaturalWormhole(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.bypass = o.getInt("bypass");
    }
}

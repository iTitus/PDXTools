package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class NaturalWormhole {

    public final int id;
    public final Coordinate coordinate;
    public final int bypass;

    public NaturalWormhole(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.bypass = o.getInt("bypass");
    }
}

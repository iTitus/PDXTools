package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Sector {

    public final long id;

    public Sector(long id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        // TODO: this
    }
}

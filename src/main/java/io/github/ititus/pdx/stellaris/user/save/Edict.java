package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class Edict {

    public final String edict;
    public final LocalDate date;

    public Edict(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.edict = o.getString("edict");
        this.date = o.getDate("date");
    }
}

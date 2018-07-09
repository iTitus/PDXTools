package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Date;

public class Edict {

    private final String edict;
    private final Date date;

    public Edict(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.edict = o.getString("edict");
        this.date = o.getDate("date");
    }

    public Edict(String edict, Date date) {
        this.edict = edict;
        this.date = date;
    }

    public String getEdict() {
        return edict;
    }

    public Date getDate() {
        return date;
    }
}

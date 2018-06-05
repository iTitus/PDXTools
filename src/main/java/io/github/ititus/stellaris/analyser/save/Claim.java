package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Date;

public class Claim {

    private final int owner, claims;
    private final Date date;

    public Claim(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.owner = o.getInt("owner");
        this.date = o.getDate("date");
        this.claims = o.getInt("claims");
    }

    public Claim(int owner, int claims, Date date) {
        this.owner = owner;
        this.claims = claims;
        this.date = date;
    }
}

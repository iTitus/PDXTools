package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class Claim {

    public final int owner;
    public final LocalDate date;
    public final int claims;

    public Claim(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.owner = o.getInt("owner");
        this.date = o.getDate("date");
        this.claims = o.getInt("claims");
    }
}

package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class ActivePolicy {

    public final String policy;
    public final String selected;
    public final LocalDate date;

    public ActivePolicy(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.policy = o.getString("policy");
        this.selected = o.getString("selected");
        this.date = o.getDate("date", null);
    }
}

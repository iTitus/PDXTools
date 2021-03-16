package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class AIAttitude {

    public final int country, priority;
    public final int weight;
    public final String attitude;
    public final LocalDate date;

    public AIAttitude(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.country = o.getUnsignedInt("country");
        this.attitude = o.getString("attitude");
        this.weight = o.getInt("weight");
        this.priority = o.getInt("priority");
        this.date = o.getDate("date");
    }
}

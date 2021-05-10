package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TimedModifier {

    public final int days;
    public final double multiplier;
    public final String modifier;

    public TimedModifier(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.multiplier = o.getDouble("multiplier", 1);
        this.modifier = o.getString("modifier");
        this.days = o.getInt("days");
    }
}

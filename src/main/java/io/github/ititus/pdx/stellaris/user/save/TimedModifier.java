package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TimedModifier {

    private final String modifier;
    private final int days;

    public TimedModifier(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.modifier = o.getString("modifier");
        this.days = o.getInt("days");
    }

    public TimedModifier(String modifier, int days) {
        this.modifier = modifier;
        this.days = days;
    }

    public String getModifier() {
        return modifier;
    }

    public int getDays() {
        return days;
    }
}

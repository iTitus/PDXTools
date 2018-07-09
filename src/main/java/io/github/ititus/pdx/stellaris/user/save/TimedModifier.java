package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TimedModifier {

    private final int days;
    private final double multiplier;
    private final String modifier;

    public TimedModifier(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.multiplier = o.getDouble("multiplier", 1);
        this.modifier = o.getString("modifier");
        this.days = o.getInt("days");
    }

    public TimedModifier(int days, double multiplier, String modifier) {
        this.days = days;
        this.multiplier = multiplier;
        this.modifier = modifier;
    }

    public int getDays() {
        return days;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String getModifier() {
        return modifier;
    }
}

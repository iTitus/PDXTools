package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class RelationModifier {

    private final boolean decay;
    private final double value;
    private final String modifier;
    private final LocalDate startDate;


    public RelationModifier(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.modifier = o.getString("modifier");
        this.startDate = o.getDate("start_date");
        this.value = o.getDouble("value");
        this.decay = o.getBoolean("decay");
    }

    public RelationModifier(boolean decay, double value, String modifier, LocalDate startDate) {
        this.decay = decay;
        this.value = value;
        this.modifier = modifier;
        this.startDate = startDate;
    }

    public boolean isDecay() {
        return decay;
    }

    public double getValue() {
        return value;
    }

    public String getModifier() {
        return modifier;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}

package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class RelationModifier {

    public final boolean decay;
    public final double value;
    public final String modifier;
    public final LocalDate startDate;

    public RelationModifier(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.modifier = o.getString("modifier");
        this.startDate = o.getDate("start_date", null);
        this.value = o.getDouble("value");
        this.decay = o.getBoolean("decay", false);
    }
}

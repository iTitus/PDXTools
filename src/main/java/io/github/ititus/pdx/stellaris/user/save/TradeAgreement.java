package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TradeAgreement {

    public final int country;
    public final boolean researchAgreement;
    public final boolean sensorLink;

    public TradeAgreement(PdxScriptObject o) {
        this.country = o.getInt("country");
        this.researchAgreement = o.getBoolean("research_agreement");
        this.sensorLink = o.getBoolean("sensor_link");
    }
}

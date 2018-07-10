package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TradeAgreement {

    private final boolean researchAgreement, sensorLink;
    private final int country;

    public TradeAgreement(PdxScriptObject o) {
        this.country = o.getInt("country");
        this.researchAgreement = o.getBoolean("research_agreement");
        this.sensorLink = o.getBoolean("sensor_link");
    }

    public TradeAgreement(boolean researchAgreement, boolean sensorLink, int country) {
        this.researchAgreement = researchAgreement;
        this.sensorLink = sensorLink;
        this.country = country;
    }

    public boolean isResearchAgreement() {
        return researchAgreement;
    }

    public boolean isSensorLink() {
        return sensorLink;
    }

    public int getCountry() {
        return country;
    }
}

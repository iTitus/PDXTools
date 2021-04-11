package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.shared.Resources;

public class TradeAgreement {

    public final int country;
    public final int recipient;
    public final Resources monthlyResources;
    public final boolean researchAgreement;
    public final boolean sensorLink;

    public TradeAgreement(PdxScriptObject o) {
        this.country = o.getInt("country");
        this.recipient = o.getInt("recipient");
        this.researchAgreement = o.getBoolean("research_agreement", false);
        this.sensorLink = o.getBoolean("sensor_link", false);
        this.monthlyResources = o.getObjectAsNullOr("monthly_resources", Resources::new);
    }
}

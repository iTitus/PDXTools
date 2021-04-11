package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TerraformProcess {

    public final double progress;
    public final int total;
    public final String planetClass;
    public final int energy;
    public final int terraformLink;
    public final int who;

    public TerraformProcess(PdxScriptObject o) {
        this.progress = o.getDouble("progress");
        this.total = o.getInt("total");
        this.planetClass = o.getString("planet_class");
        this.energy = o.getInt("energy");
        // TODO: cost
        this.terraformLink = o.getInt("terraform_link");
        this.who = o.getInt("who");
    }
}

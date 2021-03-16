package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FormationType {

    public final double scale;
    public final String type;
    public final double angle;

    public FormationType(PdxScriptObject o) {
        this.scale = o.getDouble("scale");
        this.angle = o.getDouble("angle");
        this.type = o.getString("type");
    }
}

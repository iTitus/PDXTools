package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FormationType {

    private final double scale, angle;
    private final String type;

    public FormationType(PdxScriptObject o) {
        this.scale = o.getDouble("scale");
        this.angle = o.getDouble("angle");
        this.type = o.getString("type");
    }

    public FormationType(double scale, double angle, String type) {
        this.scale = scale;
        this.angle = angle;
        this.type = type;
    }

    public double getScale() {
        return scale;
    }

    public double getAngle() {
        return angle;
    }

    public String getType() {
        return type;
    }
}

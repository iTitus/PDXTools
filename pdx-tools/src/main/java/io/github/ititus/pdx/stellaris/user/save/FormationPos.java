package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FormationPos {

    public final double x;
    public final double y;
    public final double speed;
    public final double rotation;
    public final double forwardX;
    public final double forwardY;

    public FormationPos(PdxScriptObject o) {
        this.x = o.getDouble("x");
        this.y = o.getDouble("y");
        this.speed = o.getDouble("speed");
        this.rotation = o.getDouble("rotation");
        this.forwardX = o.getDouble("forward_x");
        this.forwardY = o.getDouble("forward_y");
    }
}

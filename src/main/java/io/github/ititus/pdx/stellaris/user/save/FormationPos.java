package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FormationPos {

    private final double x, y, speed, rotation, forwardX, forwardY;

    public FormationPos(PdxScriptObject o) {
        this.x = o.getDouble("x");
        this.y = o.getDouble("y");
        this.speed = o.getDouble("speed");
        this.rotation = o.getDouble("rotation");
        this.forwardX = o.getDouble("forward_x");
        this.forwardY = o.getDouble("forward_y");
    }

    public FormationPos(double x, double y, double speed, double rotation, double forwardX, double forwardY) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.rotation = rotation;
        this.forwardX = forwardX;
        this.forwardY = forwardY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRotation() {
        return rotation;
    }

    public double getForwardX() {
        return forwardX;
    }

    public double getForwardY() {
        return forwardY;
    }
}

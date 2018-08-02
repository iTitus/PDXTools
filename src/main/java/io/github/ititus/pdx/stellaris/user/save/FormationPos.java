package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import io.github.ititus.pdx.util.Util;

public class FormationPos {

    private static final Deduplicator<FormationPos> DEDUPLICATOR = new Deduplicator<>();

    private final double x, y, speed, rotation, forwardX, forwardY;

    private FormationPos(PdxScriptObject o) {
        this.x = o.getDouble("x");
        this.y = o.getDouble("y");
        this.speed = o.getDouble("speed");
        this.rotation = o.getDouble("rotation");
        this.forwardX = o.getDouble("forward_x");
        this.forwardY = o.getDouble("forward_y");
    }

    private FormationPos(double x, double y, double speed, double rotation, double forwardX, double forwardY) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.rotation = rotation;
        this.forwardX = forwardX;
        this.forwardY = forwardY;
    }

    public static FormationPos of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new FormationPos(o));
    }

    public static FormationPos of(double x, double y, double speed, double rotation, double forwardX, double forwardY) {
        return DEDUPLICATOR.deduplicate(new FormationPos(x, y, speed, rotation, forwardX, forwardY));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormationPos)) {
            return false;
        }
        FormationPos that = (FormationPos) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.speed, speed) == 0 && Double.compare(that.rotation, rotation) == 0 && Double.compare(that.forwardX, forwardX) == 0 && Double.compare(that.forwardY, forwardY) == 0;
    }

    @Override
    public int hashCode() {
        return Util.hash(x, y, speed, rotation, forwardX, forwardY);
    }
}

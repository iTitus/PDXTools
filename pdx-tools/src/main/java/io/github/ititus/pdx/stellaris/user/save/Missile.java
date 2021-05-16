package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Missile {

    public final long id;
    public final Coordinate position;
    public final double rotation;
    public final int owner;
    public final int fleet;
    public final int target;
    public final double rotationSpeed;
    public final double forwardX;
    public final double forwardY;
    public final double speed;
    public final double health;
    public final int armor;
    public final int shield;
    public final double rotationRate;
    public final int retargets;
    public final String weaponComponentTemplate;

    public Missile(long id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.position = o.getObjectAs("position", Coordinate::new);
        this.rotation = o.getDouble("rotation");
        this.owner = o.getInt("owner");
        this.fleet = o.getInt("fleet");
        this.target = o.getInt("target");
        this.rotationSpeed = o.getDouble("rotation_speed");
        this.forwardX = o.getDouble("forward_x");
        this.forwardY = o.getDouble("forward_y");
        this.speed = o.getDouble("speed");
        this.health = o.getDouble("health");
        this.armor = o.getInt("armor");
        this.shield = o.getInt("shield");
        this.rotationRate = o.getDouble("rotation_rate");
        this.retargets = o.getInt("retargets");
        this.weaponComponentTemplate = o.getString("weapon_component_template");
    }
}

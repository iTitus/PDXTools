package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Army {

    public final int id;
    public final String name;
    public final String type;
    public final double health;
    public final double maxHealth;
    public final String fleetName;
    public final int homePlanet;
    public final int owner;
    public final int species;
    public final int ship;
    public final int leader;
    public final int planet;
    public final double experience;
    public final double morale;
    public final int pop;

    public Army(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.name = o.getString("name");
        this.type = o.getString("type");
        this.health = o.getDouble("health");
        this.maxHealth = o.getDouble("max_health");
        this.fleetName = o.getString("fleet_name", null);
        this.homePlanet = o.getInt("home_planet");
        this.owner = o.getInt("owner");
        this.species = o.getInt("species", -1);
        this.ship = o.getInt("ship", -1);
        this.leader = o.getInt("leader", -1);
        this.planet = o.getInt("planet", -1);
        this.experience = o.getDouble("experience", 0);
        this.morale = o.getDouble("morale");
        this.pop = o.getInt("pop", -1);
    }
}

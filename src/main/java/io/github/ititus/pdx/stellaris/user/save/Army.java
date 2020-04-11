package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Army {

    private final int homePlanet, owner, speciesIndex, ship, leader, planet, pop;
    private final double health, maxHealth, experience, morale;
    private final String name, type;

    public Army(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.type = o.getString("type");
        this.health = o.getDouble("health");
        this.maxHealth = o.getDouble("max_health");
        this.homePlanet = o.getInt("home_planet");
        this.owner = o.getInt("owner");
        this.speciesIndex = o.getInt("species_index");
        this.ship = o.getInt("ship");
        this.leader = o.getInt("leader");
        this.planet = o.getInt("planet");
        this.experience = o.getDouble("experience");
        this.morale = o.getDouble("morale");
        this.pop = o.getInt("pop");
    }

    public Army(int homePlanet, int owner, int speciesIndex, int ship, int leader, int planet, int pop, double health
            , double maxHealth, double experience, double morale, String name, String type) {
        this.homePlanet = homePlanet;
        this.owner = owner;
        this.speciesIndex = speciesIndex;
        this.ship = ship;
        this.leader = leader;
        this.planet = planet;
        this.pop = pop;
        this.health = health;
        this.maxHealth = maxHealth;
        this.experience = experience;
        this.morale = morale;
        this.name = name;
        this.type = type;
    }

    public int getHomePlanet() {
        return homePlanet;
    }

    public int getOwner() {
        return owner;
    }

    public int getSpeciesIndex() {
        return speciesIndex;
    }

    public int getShip() {
        return ship;
    }

    public int getLeader() {
        return leader;
    }

    public int getPlanet() {
        return planet;
    }

    public int getPop() {
        return pop;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getExperience() {
        return experience;
    }

    public double getMorale() {
        return morale;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

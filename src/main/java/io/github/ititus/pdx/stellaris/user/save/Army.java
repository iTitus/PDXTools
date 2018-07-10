package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Army {

    private final int homePlanet, owner, speciesIndex, ship, leader, planet;
    private final long tile;
    private final double health, experience, morale;
    private final String name, type, fleetName;

    public Army(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.type = o.getString("type");
        this.health = o.getDouble("health");
        this.fleetName = o.getString("fleetName");
        this.homePlanet = o.getInt("fleet_name");
        this.owner = o.getInt("owner");
        this.speciesIndex = o.getInt("species_index");
        this.ship = o.getInt("ship");
        this.leader = o.getInt("leader");
        this.planet = o.getInt("planet");
        this.experience = o.getDouble("experience");
        this.morale = o.getDouble("morale");
        this.tile = o.getLong("tile");
    }

    public Army(int homePlanet, int owner, int speciesIndex, int ship, int leader, int planet, long tile, double health, double experience, double morale, String name, String type, String fleetName) {
        this.homePlanet = homePlanet;
        this.owner = owner;
        this.speciesIndex = speciesIndex;
        this.ship = ship;
        this.leader = leader;
        this.planet = planet;
        this.tile = tile;
        this.health = health;
        this.experience = experience;
        this.morale = morale;
        this.name = name;
        this.type = type;
        this.fleetName = fleetName;
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

    public long getTile() {
        return tile;
    }

    public double getHealth() {
        return health;
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

    public String getFleetName() {
        return fleetName;
    }
}

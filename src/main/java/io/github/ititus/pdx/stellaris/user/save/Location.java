package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Location {

    private final int army, planet, ship, starbase;
    private final long sector;
    private final String techArea;

    public Location(PdxScriptObject o) {
        this.army = o.getInt("army", -1);
        this.planet = o.getInt("planet", -1);
        this.ship = o.getInt("ship", -1);
        this.starbase = o.getInt("starbase", -1);
        this.sector = o.getLong("sector", -1);
        this.techArea = o.getString("tech_area");
    }

    public Location(int army, int planet, int ship, int starbase, long sector, String techArea) {
        this.army = army;
        this.planet = planet;
        this.ship = ship;
        this.starbase = starbase;
        this.sector = sector;
        this.techArea = techArea;
    }

    public int getArmy() {
        return army;
    }

    public int getPlanet() {
        return planet;
    }

    public int getShip() {
        return ship;
    }

    public int getStarbase() {
        return starbase;
    }

    public long getSector() {
        return sector;
    }

    public String getTechArea() {
        return techArea;
    }
}

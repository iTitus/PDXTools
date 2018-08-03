package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class Location {

    private static final Deduplicator<Location> DEDUPLICATOR = new Deduplicator<>();

    private final int army, planet, ship, starbase;
    private final long sector;
    private final String techArea;

    private Location(PdxScriptObject o) {
        this.army = o.getInt("army", -1);
        this.planet = o.getInt("planet", -1);
        this.ship = o.getInt("ship", -1);
        this.starbase = o.getInt("starbase", -1);
        this.sector = o.getLong("sector", -1);
        this.techArea = o.getString("tech_area");
    }

    private Location(int army, int planet, int ship, int starbase, long sector, String techArea) {
        this.army = army;
        this.planet = planet;
        this.ship = ship;
        this.starbase = starbase;
        this.sector = sector;
        this.techArea = techArea;
    }

    public static Location of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Location(o));
    }

    public static Location of(int army, int planet, int ship, int starbase, long sector, String techArea) {
        return DEDUPLICATOR.deduplicate(new Location(army, planet, ship, starbase, sector, techArea));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        Location location = (Location) o;
        return army == location.army && planet == location.planet && ship == location.ship && starbase == location.starbase && sector == location.sector && Objects.equals(techArea, location.techArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(army, planet, ship, starbase, sector, techArea);
    }
}

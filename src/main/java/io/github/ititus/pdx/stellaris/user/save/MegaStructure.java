package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class MegaStructure {

    private final int owner, planet, bypass;
    private final String type, graphicalCulture;
    private final Coordinate coordinate;
    private final Flags flags;

    public MegaStructure(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.type = o.getString("type");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.owner = o.getInt("owner", -1);
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::of) : null;
        this.graphicalCulture = o.getString("graphical_culture");
        this.planet = o.getUnsignedInt("planet");
        this.bypass = o.getInt("bypass", -1);
    }

    public MegaStructure(int owner, int planet, int bypass, String type, String graphicalCulture, Coordinate coordinate, Flags flags) {
        this.owner = owner;
        this.planet = planet;
        this.bypass = bypass;
        this.type = type;
        this.graphicalCulture = graphicalCulture;
        this.coordinate = coordinate;
        this.flags = flags;
    }

    public int getOwner() {
        return owner;
    }

    public int getPlanet() {
        return planet;
    }

    public int getBypass() {
        return bypass;
    }

    public String getType() {
        return type;
    }

    public String getGraphicalCulture() {
        return graphicalCulture;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Flags getFlags() {
        return flags;
    }
}

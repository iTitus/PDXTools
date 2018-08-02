package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class MegaStructure {

    private final int owner, planet, bypass;
    private final String type;
    private final Coordinate coordinate;

    public MegaStructure(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.type = o.getString("type");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.owner = o.getInt("owner", -1);
        this.planet = o.getUnsignedInt("planet");
        this.bypass = o.getInt("bypass", -1);
    }

    public MegaStructure(int owner, int planet, int bypass, String type, Coordinate coordinate) {
        this.owner = owner;
        this.planet = planet;
        this.bypass = bypass;
        this.type = type;
        this.coordinate = coordinate;
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

    public Coordinate getCoordinate() {
        return coordinate;
    }
}

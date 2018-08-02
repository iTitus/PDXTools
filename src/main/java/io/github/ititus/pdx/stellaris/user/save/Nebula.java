package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Nebula {

    private final Coordinate coordinate;
    private final String name;
    private final double radius;
    private final ImmutableIntList galacticObjects;

    public Nebula(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.name = o.getString("name");
        this.radius = o.getDouble("radius");
        this.galacticObjects = o.getImplicitList("galactic_object").getAsIntList();
    }

    public Nebula(Coordinate coordinate, String name, double radius, ImmutableIntList galacticObjects) {
        this.coordinate = coordinate;
        this.name = name;
        this.radius = radius;
        this.galacticObjects = galacticObjects;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getRadius() {
        return radius;
    }

    public ImmutableIntList getGalacticObjects() {
        return galacticObjects;
    }

    public String getName() {
        return name;
    }
}

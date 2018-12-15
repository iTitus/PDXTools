package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Nebula {

    private final double radius;
    private final String name;
    private final ImmutableIntList galacticObjects;
    private final Coordinate coordinate;

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

    public Nebula(double radius, String name, ImmutableIntList galacticObjects, Coordinate coordinate) {
        this.radius = radius;
        this.name = name;
        this.galacticObjects = galacticObjects;
        this.coordinate = coordinate;
    }

    public double getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }

    public ImmutableIntList getGalacticObjects() {
        return galacticObjects;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}

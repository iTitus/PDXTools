package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Nebula {

    private final Coordinate coordinate;
    private final String name;
    private final double radius;
    private final List<Integer> galacticObjects;

    public Nebula(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.name = o.getString("name");
        this.radius = o.getDouble("radius");
        this.galacticObjects = o.getImplicitList("galactic_object").getAsIntegerList();
    }

    public Nebula(Coordinate coordinate, String name, double radius, Collection<Integer> galacticObjects) {
        this.coordinate = coordinate;
        this.name = name;
        this.radius = radius;
        this.galacticObjects = new ArrayList<>(galacticObjects);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getRadius() {
        return radius;
    }

    public List<Integer> getGalacticObjects() {
        return Collections.unmodifiableList(galacticObjects);
    }

    public String getName() {
        return name;
    }
}

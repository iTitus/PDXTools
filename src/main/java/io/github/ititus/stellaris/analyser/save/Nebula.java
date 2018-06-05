package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.ArrayList;
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
        PdxScriptList l = o.getList("galactic_object");
        if (l != null) {
            this.galacticObjects = l.getAsIntegerList();
        } else {
            this.galacticObjects = new ArrayList<>(Collections.singleton(o.getInt("galactic_object")));
        }
    }

    public Nebula(Coordinate coordinate, String name, double radius, List<Integer> galacticObjects) {
        this.coordinate = coordinate;
        this.name = name;
        this.radius = radius;
        this.galacticObjects = galacticObjects;
    }
}

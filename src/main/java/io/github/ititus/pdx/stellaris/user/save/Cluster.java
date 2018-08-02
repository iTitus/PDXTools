package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Cluster {

    private final double radius;
    private final String id;
    private final ImmutableIntList objects;
    private final Coordinate position;

    public Cluster(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.id = o.getString("id");
        this.position = o.getObject("position").getAs(Coordinate::of);
        this.radius = o.getDouble("radius");
        this.objects = o.getList("objects").getAsIntList();
    }

    public Cluster(double radius, String id, ImmutableIntList objects, Coordinate position) {
        this.radius = radius;
        this.id = id;
        this.objects = objects;
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public String getId() {
        return id;
    }

    public ImmutableIntList getObjects() {
        return objects;
    }

    public Coordinate getPosition() {
        return position;
    }
}

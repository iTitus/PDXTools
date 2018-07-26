package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.List;

public class Cluster {

    private final double radius;
    private final String id;
    private final ViewableList<Integer> objects;
    private final Coordinate position;

    public Cluster(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.id = o.getString("id");
        this.position = o.getObject("position").getAs(Coordinate::new);
        this.radius = o.getDouble("radius");
        this.objects = o.getList("objects").getAsIntegerList();
    }

    public Cluster(double radius, String id, Collection<Integer> objects, Coordinate position) {
        this.radius = radius;
        this.id = id;
        this.objects = new ViewableUnmodifiableArrayList<>(objects);
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public String getId() {
        return id;
    }

    public List<Integer> getObjects() {
        return objects.getView();
    }

    public Coordinate getPosition() {
        return position;
    }
}

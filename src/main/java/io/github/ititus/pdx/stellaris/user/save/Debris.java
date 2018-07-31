package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Date;

public class Debris {

    private final int country;
    private final Date date;
    private final ImmutableList<String> components;
    private final Coordinate coordinate;

    public Debris(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.country = o.getInt("country");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.components = o.getImplicitList("component").getAsStringList();
        this.date = o.getDate("date");
    }

    public Debris(int country, Date date, ImmutableList<String> components, Coordinate coordinate) {
        this.country = country;
        this.date = date;
        this.components = components;
        this.coordinate = coordinate;
    }

    public int getCountry() {
        return country;
    }

    public Date getDate() {
        return date;
    }

    public ImmutableList<String> getComponents() {
        return components;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}

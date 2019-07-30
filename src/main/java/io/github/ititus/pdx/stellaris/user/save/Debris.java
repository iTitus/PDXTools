package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.time.LocalDate;

public class Debris {

    private final int country;
    private final LocalDate date;
    private final ImmutableList<String> components;
    private final Coordinate coordinate;

    public Debris(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.country = o.getInt("country");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.components = o.getImplicitList("component").getAsStringList();
        this.date = o.getDate("date");
    }

    public Debris(int country, LocalDate date, ImmutableList<String> components, Coordinate coordinate) {
        this.country = country;
        this.date = date;
        this.components = components;
        this.coordinate = coordinate;
    }

    public int getCountry() {
        return country;
    }

    public LocalDate getDate() {
        return date;
    }

    public ImmutableList<String> getComponents() {
        return components;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}

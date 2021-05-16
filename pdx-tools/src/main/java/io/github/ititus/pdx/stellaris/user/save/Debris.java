package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.time.LocalDate;

public class Debris {

    public final int id;
    public final int country;
    public final LocalDate date;
    public final ImmutableList<String> components;
    public final Coordinate coordinate;

    public Debris(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.country = o.getInt("country");
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.components = o.getImplicitListAsStringList("component");
        this.date = o.getDate("date");
    }
}

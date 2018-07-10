package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;

public class AmbientObject {

    private final String data;
    private final Coordinate coordinate;
    private final Flags flags;
    private final Properties properties;

    public AmbientObject(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.data = o.getString("data");
        PdxScriptObject o1 = o.getObject("flags");
        this.flags = o1 != null ? o1.getAs(Flags::new) : new Flags(Collections.emptyMap(), Collections.emptyMap());
        this.properties = o.getObject("properties").getAs(Properties::new);
    }

    public AmbientObject(String data, Coordinate coordinate, Flags flags, Properties properties) {
        this.data = data;
        this.coordinate = coordinate;
        this.flags = flags;
        this.properties = properties;
    }

    public String getData() {
        return data;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Flags getFlags() {
        return flags;
    }

    public Properties getProperties() {
        return properties;
    }
}

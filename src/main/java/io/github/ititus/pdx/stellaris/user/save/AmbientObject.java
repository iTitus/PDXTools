package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AmbientObject {

    private final String data;
    private final Coordinate coordinate;
    private final Properties properties;

    public AmbientObject(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.data = o.getString("data");
        this.properties = o.getObject("properties").getAs(Properties::new);
    }

    public AmbientObject(String data, Coordinate coordinate, Properties properties) {
        this.data = data;
        this.coordinate = coordinate;
        this.properties = properties;
    }

    public String getData() {
        return data;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Properties getProperties() {
        return properties;
    }
}

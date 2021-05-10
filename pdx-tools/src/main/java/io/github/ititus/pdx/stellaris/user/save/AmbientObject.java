package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

public class AmbientObject {

    public final Coordinate coordinate;
    public final String data;
    public final ImmutableMap<String, FlagData> flags;
    public final boolean killed;
    public final AmbientObjectProperties properties;

    public AmbientObject(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.data = o.getString("data");
        this.flags = o.getObjectAsEmptyOrStringObjectMap("flags", FlagData::of);
        this.killed = o.getBoolean("killed", false);
        this.properties = o.getObjectAs("properties", AmbientObjectProperties::new);
    }
}

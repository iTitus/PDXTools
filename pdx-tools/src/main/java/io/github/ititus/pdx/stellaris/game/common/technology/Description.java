package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

public class Description {

    public final String description;
    public final ImmutableMap<String, Object> descriptionParameters;

    public Description(PdxScriptObject o) {
        this.description = o.getString("description");
        this.descriptionParameters = o.getObjectAsEmptyOrStringObjectMap("description_parameters", (k, v) -> v.expectValue().getValue());
    }

    public static Description createOrNull(PdxScriptObject o) {
        if (o != null && o.hasKey("description")) {
            return new Description(o);
        }

        return null;
    }
}

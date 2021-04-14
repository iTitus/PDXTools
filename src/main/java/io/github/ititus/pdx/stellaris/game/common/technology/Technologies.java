package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

public class Technologies {

    public final ImmutableMap<String, Technology> technologies;

    public Technologies(PdxScriptObject o) {
        this.technologies = o.getAsStringObjectMap(Technology::new);
    }
}

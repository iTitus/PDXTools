package io.github.ititus.pdx.stellaris.game.common.technology.category;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;

public class TechnologyCategories {

    public final ImmutableMap<String, TechnologyCategory> categories;

    public TechnologyCategories(PdxScriptObject o) {
        this.categories = o.getAsStringObjectMap(TechnologyCategory::new);
    }
}

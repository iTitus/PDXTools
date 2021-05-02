package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import static io.github.ititus.pdx.pdxscript.PdxHelper.nullOr;

public class StarbaseManager {

    public final ImmutableIntObjectMap<Starbase> starbases;

    public StarbaseManager(PdxScriptObject o) {
        this.starbases = o.getObjectAsIntObjectMap("starbases", nullOr(Starbase::new));
    }

    public Starbase get(int starbaseId) {
        return starbases.get(starbaseId);
    }
}

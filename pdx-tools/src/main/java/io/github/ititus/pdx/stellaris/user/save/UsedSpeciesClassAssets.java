package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class UsedSpeciesClassAssets {

    public final String speciesClass;
    public final ImmutableIntList values;

    public UsedSpeciesClassAssets(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.speciesClass = o.getString("class");
        this.values = o.getListAsEmptyOrIntList("values");
    }
}

package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class UsedSpeciesClassAssets {

    private final String speciesClass;
    private final ImmutableIntList values;

    public UsedSpeciesClassAssets(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.speciesClass = o.getString("class");
        PdxScriptList l = o.getList("values");
        this.values = l != null ? l.getAsIntList() : IntLists.immutable.empty();
    }

    public UsedSpeciesClassAssets(String speciesClass, ImmutableIntList values) {
        this.speciesClass = speciesClass;
        this.values = values;
    }

    public String getSpeciesClass() {
        return speciesClass;
    }

    public ImmutableIntList getValues() {
        return values;
    }
}

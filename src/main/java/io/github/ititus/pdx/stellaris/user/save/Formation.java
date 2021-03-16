package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Formation {

    public final int root;
    public final ImmutableIntList ships;
    public final ImmutableIntList parents;

    public Formation(PdxScriptObject o) {
        this.root = o.getInt("root");
        this.ships = o.getListAsEmptyOrIntList("ships");
        this.parents = o.getListAsEmptyOrIntList("parent");
    }
}

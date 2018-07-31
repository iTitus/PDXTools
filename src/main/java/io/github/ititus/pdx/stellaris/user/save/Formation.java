package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class Formation {

    private final int root;
    private final ImmutableIntList ships, parents;

    public Formation(PdxScriptObject o) {
        this.root = o.getInt("root");
        PdxScriptList l = o.getList("ships");
        this.ships = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("parent");
        this.parents = l != null ? l.getAsIntList() : IntLists.immutable.empty();
    }

    public Formation(int root, ImmutableIntList ships, ImmutableIntList parents) {
        this.root = root;
        this.ships = ships;
        this.parents = parents;
    }

    public int getRoot() {
        return root;
    }

    public ImmutableIntList getShips() {
        return ships;
    }

    public ImmutableIntList getParents() {
        return parents;
    }
}

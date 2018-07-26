package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.List;

public class Formation {

    private final int root;
    private final ViewableList<Integer> ships, parents;

    public Formation(PdxScriptObject o) {
        this.root = o.getInt("root");
        PdxScriptList l = o.getList("ships");
        this.ships = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
        l = o.getList("parent");
        this.parents = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
    }

    public Formation(int root, Collection<Integer> ships, Collection<Integer> parents) {
        this.root = root;
        this.ships = new ViewableUnmodifiableArrayList<>(ships);
        this.parents = new ViewableUnmodifiableArrayList<>(parents);
    }

    public int getRoot() {
        return root;
    }

    public List<Integer> getShips() {
        return ships.getView();
    }

    public List<Integer> getParents() {
        return parents.getView();
    }
}

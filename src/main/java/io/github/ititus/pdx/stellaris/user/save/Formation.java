package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.util.Objects;

public class Formation {

    private static final Deduplicator<Formation> DEDUPLICATOR =
            new Deduplicator<>(f -> f.getShips().isEmpty() && f.getParents().isEmpty());

    private final int root;
    private final ImmutableIntList ships, parents;

    private Formation(PdxScriptObject o) {
        this.root = o.getInt("root");
        PdxScriptList l = o.getList("ships");
        this.ships = l != null ? l.getAsIntList() : IntLists.immutable.empty();
        l = o.getList("parent");
        this.parents = l != null ? l.getAsIntList() : IntLists.immutable.empty();
    }

    private Formation(int root, ImmutableIntList ships, ImmutableIntList parents) {
        this.root = root;
        this.ships = ships;
        this.parents = parents;
    }

    public static Formation of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Formation(o));
    }

    public static Formation of(int root, ImmutableIntList ships, ImmutableIntList parents) {
        return DEDUPLICATOR.deduplicate(new Formation(root, ships, parents));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        Formation formation = (Formation) o;
        return root == formation.root && Objects.equals(ships, formation.ships) && Objects.equals(parents,
                formation.parents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root, ships, parents);
    }
}

package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class TerraIncognita {

    private final int size;
    private final ImmutableIntList data, systems;

    public TerraIncognita(PdxScriptObject o) {
        this.size = o.getInt("size");
        this.data = o.getList("data").getAsIntList();
        PdxScriptList l = o.getList("systems");
        this.systems = l != null ? l.getAsIntList() : IntLists.immutable.empty();
    }

    public TerraIncognita(int size, ImmutableIntList data, ImmutableIntList systems) {
        this.size = size;
        this.data = data;
        this.systems = systems;
    }

    public int getSize() {
        return size;
    }

    public ImmutableIntList getData() {
        return data;
    }

    public ImmutableIntList getSystems() {
        return systems;
    }
}

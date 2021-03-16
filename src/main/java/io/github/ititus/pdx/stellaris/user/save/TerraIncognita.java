package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class TerraIncognita {

    public final int size;
    public final ImmutableIntList data;
    public final ImmutableIntList systems;

    public TerraIncognita(PdxScriptObject o) {
        this.size = o.getInt("size");
        this.data = o.getListAsIntList("data");
        this.systems = o.getListAsEmptyOrIntList("systems");
    }
}

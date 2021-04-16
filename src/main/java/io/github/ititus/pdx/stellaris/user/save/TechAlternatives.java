package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class TechAlternatives {

    public final ImmutableList<String> physics;
    public final ImmutableList<String> society;
    public final ImmutableList<String> engineering;

    public TechAlternatives(PdxScriptObject o) {
        this.physics = o.getListAsEmptyOrStringList("physics");
        this.society = o.getListAsEmptyOrStringList("society");
        this.engineering = o.getListAsEmptyOrStringList("engineering");
    }
}

package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class GalacticCommunity {

    public final ImmutableIntList members;

    public GalacticCommunity(PdxScriptObject o) {
        this.members = o.getListAsEmptyOrIntList("members");
        // TODO: this
    }
}

package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class FederationProgression {

    public final ImmutableList<FederationPerk> perks;

    public FederationProgression(PdxScriptObject o) {
        this.perks = o.getListAsList("perks", FederationPerk::new);
    }

    public boolean hasPerk(String type) {
        return perks.anySatisfy(p -> type.equals(p.type));
    }
}

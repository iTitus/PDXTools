package io.github.ititus.pdx.stellaris.game.common.deposits;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.shared.Resources;
import org.eclipse.collections.api.list.ImmutableList;

public class DepositResources {

    public final String category;
    public final ImmutableList<Resources> produces;
    public final ImmutableList<Resources> cost;

    public DepositResources(PdxScriptObject o) {
        this.category = o.getString("category", null);
        this.produces = o.getImplicitListAsList("produces", Resources::new);
        this.cost = o.getImplicitListAsList("cost", Resources::new);
    }
}

package io.github.ititus.pdx.stellaris.game.common.deposits;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.PlanetScope;
import io.github.ititus.pdx.stellaris.shared.Resources;
import io.github.ititus.pdx.stellaris.shared.TriggeredResources;
import org.eclipse.collections.api.list.ImmutableList;

public class DepositResources {

    private final StellarisGame game;

    public final String category;
    public final ImmutableList<TriggeredResources> produces;
    public final ImmutableList<TriggeredResources> cost;

    public DepositResources(StellarisGame game, PdxScriptObject o) {
        this.game = game;
        this.category = o.getString("category", null);
        this.produces = o.getImplicitListAsList("produces", s -> new TriggeredResources(game, s));
        this.cost = o.getImplicitListAsList("cost", s -> new TriggeredResources(game, s));
    }

    public Resources getProduced(PlanetScope scope) {
        for (TriggeredResources r : produces) {
            if (r.isActive(scope)) {
                return r.resources;
            }
        }

        return new Resources();
    }

    public Resources getCost(PlanetScope scope) {
        for (TriggeredResources r : cost) {
            if (r.isActive(scope)) {
                return r.resources;
            }
        }

        return new Resources();
    }
}

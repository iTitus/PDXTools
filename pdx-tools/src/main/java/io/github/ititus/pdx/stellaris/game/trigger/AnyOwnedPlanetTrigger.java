package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.HabitablePlanetOwnerScope;

public class AnyOwnedPlanetTrigger extends AnyTrigger {

    public AnyOwnedPlanetTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_owned_planet");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return HabitablePlanetOwnerScope.expect(scope).getOwnedPlanets();
    }
}

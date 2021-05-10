package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.GalacticObjectScope;

public class AnySystemPlanetTrigger extends AnyTrigger {

    public AnySystemPlanetTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_system_planet");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return GalacticObjectScope.expect(scope).getSystemPlanets();
    }
}

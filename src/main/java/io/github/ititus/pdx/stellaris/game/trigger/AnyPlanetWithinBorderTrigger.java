package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import io.github.ititus.pdx.stellaris.game.scope.PlanetScope;

public class AnyPlanetWithinBorderTrigger extends AnyTrigger {

    public AnyPlanetWithinBorderTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_planet_within_border");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return CountryScope.expect(scope).getPlanetsWithinBorder();
    }
}

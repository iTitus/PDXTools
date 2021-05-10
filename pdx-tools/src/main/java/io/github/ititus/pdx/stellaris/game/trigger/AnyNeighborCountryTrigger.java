package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;

public class AnyNeighborCountryTrigger extends AnyTrigger {

    public AnyNeighborCountryTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_neighbor_country");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return CountryScope.expect(scope).getNeighborCountries();
    }
}

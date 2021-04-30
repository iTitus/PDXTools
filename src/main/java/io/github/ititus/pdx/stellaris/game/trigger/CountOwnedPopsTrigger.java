package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.CountTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.PopOwnerScope;

public class CountOwnedPopsTrigger extends CountTrigger {

    public CountOwnedPopsTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "count_owned_pop");
    }

    @Override
    protected int count(Scope scope) {
        return PopOwnerScope.expect(scope).countOwnedPops(ps -> evaluateAnd(ps, children));
    }
}

package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.PopScope;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.PopOwnerScope;

public class AnyOwnedPopTrigger extends AnyTrigger {

    public AnyOwnedPopTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_owned_pop");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return PopOwnerScope.expect(scope).getOwnedPops();
    }
}

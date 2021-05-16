package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.FederationScope;

public class AnyMemberTrigger extends AnyTrigger {

    public AnyMemberTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_member");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return FederationScope.expect(scope).getMembers();
    }
}

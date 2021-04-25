package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;

public class HasTraditionTrigger extends Trigger {

    public final String tradition;

    public HasTraditionTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.tradition = s.expectValue().expectString();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country
        // return scope.getTraditions().contains(tradition)
        throw new UnsupportedOperationException();
    }
}

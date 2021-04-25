package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;

public class HasCivicTrigger extends Trigger {

    public final String civic;

    public HasCivicTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.civic = s.expectValue().expectString();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country
        // return scope.getCivics().contains(civic)
        throw new UnsupportedOperationException();
    }
}

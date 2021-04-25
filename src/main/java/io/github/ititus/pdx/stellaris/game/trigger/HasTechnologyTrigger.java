package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;

public class HasTechnologyTrigger extends Trigger {

    public final String technology;

    public HasTechnologyTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.technology = s.expectValue().expectString();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country
        // return scope.getTechnologies().contains(technology)
        throw new UnsupportedOperationException();
    }
}

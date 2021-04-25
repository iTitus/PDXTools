package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.list.ImmutableList;

public class NotTrigger extends Trigger {

    public final ImmutableList<Trigger> triggers;

    public NotTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.triggers = create(s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        return !evaluateAnd(scope, triggers);
    }
}

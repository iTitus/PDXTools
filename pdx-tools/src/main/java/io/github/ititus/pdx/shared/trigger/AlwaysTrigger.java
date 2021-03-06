package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public final class AlwaysTrigger extends Trigger {

    public final boolean value;

    public AlwaysTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.value = s.expectValue().expectBoolean();
    }

    @Override
    public boolean evaluate(Scope scope) {
        return value;
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("always=" + value);
    }
}

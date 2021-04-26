package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class AlwaysTrigger extends Trigger {

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
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        return Lists.immutable.of("always=" + value);
    }
}

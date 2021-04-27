package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class OrTrigger extends TriggerBasedTrigger {

    public OrTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        return evaluateOr(scope, children);
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        MutableList<String> list = Lists.mutable.of("OR:");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}

package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.TriggerBasedTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class AnyOwnedPopTrigger extends TriggerBasedTrigger {

    public AnyOwnedPopTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: planet country pop_faction sector
        /*
        for (Pop p : scope.getOwnedPops()) {
            if (evaluate(p, children)) {
                return true;
            }
        }
        return false;
        */
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        MutableList<String> list = Lists.mutable.of("any_owned_pop:");
        localiseChildren(list, localisation, language, indent);
        return list.toImmutable();
    }
}

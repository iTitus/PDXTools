package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class HasEthicTrigger extends Trigger {

    public final String ethic;

    public HasEthicTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.ethic = s.expectValue().expectString();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country pop dlc_recommendation
        // return scope.getEthics().contains(ethic)
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        return Lists.immutable.of("has_ethic=" + ethic);
    }
}

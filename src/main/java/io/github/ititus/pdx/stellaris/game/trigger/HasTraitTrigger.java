package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class HasTraitTrigger extends Trigger {

    public final String trait;

    public HasTraitTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.trait = s.expectValue().expectString();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country[the dominant species] pop leader species dlc_recommendation
        // return scope.getTraits().contains(trait)
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("has_trait=" + trait);
    }
}

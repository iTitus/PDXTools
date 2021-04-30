package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.TriggerBasedTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class AnyRelationTrigger extends AnyTrigger {

    public AnyRelationTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_relation");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return CountryScope.expect(scope).getRelations();
    }
}

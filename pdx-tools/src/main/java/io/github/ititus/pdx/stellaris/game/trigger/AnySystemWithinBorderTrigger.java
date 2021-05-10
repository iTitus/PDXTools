package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.AnyTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.GalacticObjectOwnerScope;

public class AnySystemWithinBorderTrigger extends AnyTrigger {

    public AnySystemWithinBorderTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, "any_system_within_border");
    }

    @Override
    protected Iterable<? extends Scope> getScopes(Scope scope) {
        return GalacticObjectOwnerScope.expect(scope).getSystemsWithinBorder();
    }
}

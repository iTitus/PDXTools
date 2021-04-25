package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.list.ImmutableList;

public class AnyOwnedPlanetTrigger extends Trigger {

    public final ImmutableList<Trigger> triggers;

    public AnyOwnedPlanetTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.triggers = create(s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country sector
        /*
        for (Planet p : scope.getOwnedPlanets()) {
            if (evaluate(p, triggers)) {
                return true;
            }
        }
        return false;
        */
        throw new UnsupportedOperationException();
    }
}

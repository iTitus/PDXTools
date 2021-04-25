package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;

public class HostHasDlcTrigger extends Trigger {

    public final String dlc;

    public HostHasDlcTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.dlc = s.expectValue().expectString();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: all
        // return scope.getHostDlcs().contains(dlc)
        throw new UnsupportedOperationException();
    }
}

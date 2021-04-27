package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

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

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("host_has_dlc=" + dlc);
    }
}

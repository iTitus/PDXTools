package io.github.ititus.pdx.stellaris.shared;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.PlanetScope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class TriggeredResources {

    public final ImmutableList<Trigger> triggers;
    public final Resources resources;

    public TriggeredResources(StellarisGame game, IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.resources = o.getAs(Resources::new);
        this.triggers = o.getObjectAs("trigger", game.triggers::create, Lists.immutable.empty());
    }

    public boolean isActive(PlanetScope scope) {
        return Trigger.evaluateAnd(scope, triggers);
    }
}

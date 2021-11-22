package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class HabitabilityTrigger extends Trigger {

    // TODO: make who a reference
    public final String who;
    public final PdxRelation relation;
    public final double count;

    public HabitabilityTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptObject o = s.expectObject();
        this.who = o.getString("who");
        PdxScriptValue v = o.get("value").expectValue();
        this.relation = v.getRelation();
        this.count = v.expectDouble();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // TODO: implement
        return false;
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("habitability" + relation.getSign() + count + " for " + who);
    }
}

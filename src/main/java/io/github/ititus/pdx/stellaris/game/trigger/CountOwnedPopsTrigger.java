package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.list.ImmutableList;

public class CountOwnedPopsTrigger extends Trigger {

    public final ImmutableList<Trigger> limit;
    public final PdxRelation relation;
    public final int count;

    public CountOwnedPopsTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptObject o = s.expectObject();
        this.limit = o.getObjectAs("limit", this::create);
        PdxScriptValue v = o.getRaw("count").expectValue();
        this.relation = v.getRelation();
        this.count = v.expectInt();
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: planet country pop_faction
        // return scope.getOwnedPopsCount(limit) <relation> count;
        throw new UnsupportedOperationException();
    }
}

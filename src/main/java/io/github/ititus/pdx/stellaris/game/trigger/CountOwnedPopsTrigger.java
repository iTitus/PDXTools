package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.TriggerBasedTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class CountOwnedPopsTrigger extends TriggerBasedTrigger {

    public final PdxRelation relation;
    public final int count;

    private CountOwnedPopsTrigger(Triggers triggers, PdxScriptObject limit, PdxRelation relation, int count) {
        super(triggers, limit);
        this.relation = relation;
        this.count = count;
    }

    public static CountOwnedPopsTrigger of(Triggers triggers, IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        PdxScriptObject limit = o.getObject("limit");

        PdxScriptValue v = o.get("count").expectValue();
        PdxRelation relation = v.getRelation();
        int count = v.expectInt();

        return new CountOwnedPopsTrigger(triggers, limit, relation, count);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: planet country pop_faction
        // return scope.getOwnedPopsCount(children) <relation> count;
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        MutableList<String> list = Lists.mutable.of("count_owned_pops" + relation.getSign() + count + ", where the pop matches:");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}

package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.ResourceOwnerScope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class HasResourceTrigger extends Trigger {

    public final String type;
    public final PdxRelation relation;
    public final int amount;

    public HasResourceTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        // TODO: has_resource = yes/no
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        PdxScriptValue v = o.get("count", PdxScriptValue.of(PdxRelation.NOT_EQUALS, 0)).expectValue();
        this.relation = v.getRelation();
        this.amount = v.expectInt();
    }

    @Override
    public boolean evaluate(Scope scope) {
        ResourceOwnerScope ros = ResourceOwnerScope.expect(scope);
        return relation.compare(ros.getResources().get(type), amount);
    }

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("has_resource: type=" + type + " amount" + relation.getSign() + amount);
    }
}

package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.scope.PlanetScope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class NumDistrictsTrigger extends Trigger {

    public final String type;
    public final PdxRelation relation;
    public final int count;

    public NumDistrictsTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        PdxScriptValue v = o.get("value").expectValue();
        this.relation = v.getRelation();
        this.count = v.expectInt();
    }

    @Override
    public boolean evaluate(Scope scope) {
        return relation.compare(PlanetScope.expect(scope).getDistrictCount(type), count);
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("num_districts" + relation.getSign() + count + " with type " + type);
    }
}

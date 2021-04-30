package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public abstract class CountTrigger extends TriggerBasedTrigger {

    protected final String name;
    protected final PdxRelation relation;
    protected final int count;

    protected CountTrigger(Triggers triggers, IPdxScript s, String name) {
        super(triggers, s.expectObject().getObject("limit"));
        this.name = name;
        PdxScriptValue v = s.expectObject().get("count").expectValue();
        this.relation = v.getRelation();
        this.count = v.expectInt();
    }

    protected abstract int count(Scope scope);

    @Override
    public boolean evaluate(Scope scope) {
        return relation.compare(count(scope), count);
    }

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of(name + relation.getSign() + count + ", where:");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}

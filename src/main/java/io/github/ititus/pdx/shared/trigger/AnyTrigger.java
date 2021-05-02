package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public abstract class AnyTrigger extends TriggerBasedTrigger {

    private static final Predicate<String> FILTER = not("count"::equals);
    private static final int ALL = -1;

    protected final String name;
    protected final int count;

    protected AnyTrigger(Triggers triggers, IPdxScript s, String name) {
        super(triggers, s, FILTER);
        this.name = name;
        IPdxScript countScript = s.expectObject().getRaw("count");
        if (countScript == null) {
            this.count = 1;
        } else {
            PdxScriptValue v = countScript.expectValue();
            if (v.isInt()) {
                this.count = v.expectInt();
                if (this.count == ALL || this.count <= 0) {
                    throw new IllegalArgumentException("illegal value for count: " + this.count);
                }
            } else if ("all".equals(v.expectString())) {
                this.count = ALL;
            } else {
                throw new IllegalArgumentException("unknown value for count: " + v);
            }
        }
    }

    protected abstract Iterable<? extends Scope> getScopes(Scope scope);

    @Override
    public boolean evaluate(Scope scope) {
        int n = 0;
        for (Scope s : getScopes(scope)) {
            if (evaluateAnd(s, children)) {
                if (++n >= count && count != ALL) {
                    return true;
                }
            } else if (count == ALL) {
                return false;
            }
        }

        return count == ALL;
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of(name + ":");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}

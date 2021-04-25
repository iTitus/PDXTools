package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.common.technology.Technology;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class ResearchLeaderTrigger extends Trigger {

    private static final Predicate<String> FILTER = not("area"::equals);

    public final Technology.Area area;
    public final ImmutableList<Trigger> triggers;

    public ResearchLeaderTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptObject o = s.expectObject();
        this.area = o.getEnum("area", Technology.Area::of);
        this.triggers = create(o, FILTER);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: country
        // return evaluate(scope.getResearchLeader(area), triggers);
        throw new UnsupportedOperationException();
    }
}

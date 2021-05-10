package io.github.ititus.pdx.stellaris.game.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.TriggerBasedTrigger;
import io.github.ititus.pdx.shared.trigger.Triggers;
import io.github.ititus.pdx.stellaris.game.common.technology.Technology;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import io.github.ititus.pdx.stellaris.game.scope.LeaderScope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class ResearchLeaderTrigger extends TriggerBasedTrigger {

    private static final Predicate<String> FILTER = not("area"::equals);

    public final Technology.Area area;

    public ResearchLeaderTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, FILTER);
        this.area = s.expectObject().getEnum("area", Technology.Area::of);
    }

    @Override
    public boolean evaluate(Scope scope) {
        CountryScope cs = CountryScope.expect(scope);
        LeaderScope ls = cs.getResearchLeader(area);
        return ls != null && evaluateAnd(ls, children);
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of("research_leader in " + area.getName() + ":");
        localiseChildren(list, language, indent + 1);
        return list.toImmutable();
    }
}

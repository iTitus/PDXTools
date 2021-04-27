package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.trigger.Trigger;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public record TechnologyWeightModifier(
        StellarisGame game,
        double factor,
        double add,
        ImmutableList<Trigger> triggers
) {

    private static final ImmutableSet<String> IGNORE = Sets.immutable.of("factor", "add");
    private static final Predicate<String> FILTER = not(IGNORE::contains);

    public static TechnologyWeightModifier of(StellarisGame game, IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        double factor = o.getDouble("factor", 1);
        double add = o.getDouble("add", 0);
        ImmutableList<Trigger> triggers = game.triggers.create(o, FILTER);
        return new TechnologyWeightModifier(game, factor, add, triggers);
    }

    public boolean isActive(Scope scope) {
        return Trigger.evaluateAnd(scope, triggers);
    }

    public double modifyWeight(double currentWeight, double baseWeight, Scope scope) {
        if (isActive(scope)) {
            currentWeight *= factor;
            currentWeight += add * baseWeight;
        }

        return currentWeight;
    }
}

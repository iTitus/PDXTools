package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.trigger.AlwaysTrigger;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import org.eclipse.collections.api.list.ImmutableList;

public record TechnologyWeightModifiers(
        StellarisGame game,
        double factor,
        ImmutableList<TechnologyWeightModifier> modifiers
) {

    // TODO: weight = 9999

    public static TechnologyWeightModifiers of(StellarisGame game, PdxScriptObject o) {
        double factor = o.getDouble("factor", 1);
        ImmutableList<TechnologyWeightModifier> modifiers = o.getImplicitListAsList("modifier", s -> TechnologyWeightModifier.of(game, s));
        return new TechnologyWeightModifiers(game, factor, modifiers);
    }

    double getModifiedWeight(CountryScope scope, double baseWeight) {
        double currentWeight = baseWeight * factor;
        if (scope != null) {
            for (TechnologyWeightModifier m : modifiers) {
                currentWeight = m.modifyWeight(currentWeight, baseWeight, scope);
            }
        } else if (alwaysZero()) {
            return 0;
        }

        return currentWeight;
    }

    private boolean alwaysZero() {
        boolean alwaysZero = false;
        for (TechnologyWeightModifier m : modifiers) {
            if (m.factor() == 0 && m.triggers().allSatisfy(t -> t instanceof AlwaysTrigger at && at.value)) {
                alwaysZero = true;
            }

            if (m.add() != 0) {
                alwaysZero = false;
            }
        }

        return alwaysZero;
    }
}

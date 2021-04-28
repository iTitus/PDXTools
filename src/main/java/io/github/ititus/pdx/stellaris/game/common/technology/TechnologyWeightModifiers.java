package io.github.ititus.pdx.stellaris.game.common.technology;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import org.eclipse.collections.api.list.ImmutableList;

public record TechnologyWeightModifiers(
        StellarisGame game,
        double factor,
        ImmutableList<TechnologyWeightModifier> modifiers
) {

    public static TechnologyWeightModifiers of(StellarisGame game, PdxScriptObject o) {
        double factor = o.getDouble("factor", 1);
        ImmutableList<TechnologyWeightModifier> modifiers = o.getImplicitListAsList("modifier", s -> TechnologyWeightModifier.of(game, s));
        return new TechnologyWeightModifiers(game, factor, modifiers);
    }

    public double modifyWeight(double currentWeight, double baseWeight, CountryScope scope) {
        currentWeight *= factor;
        if (scope != null) {
            for (TechnologyWeightModifier m : modifiers) {
                currentWeight = m.modifyWeight(currentWeight, baseWeight, scope);
            }
        }

        return currentWeight;
    }
}

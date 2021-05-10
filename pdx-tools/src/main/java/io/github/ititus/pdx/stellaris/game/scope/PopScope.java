package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.Pop;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.util.Objects;

public class PopScope extends StellarisScope {

    private final Pop pop;

    public PopScope(StellarisGame game, StellarisSave save, int popId) {
        this(game, save, save.gameState.pops.get(popId));
    }

    public PopScope(StellarisGame game, StellarisSave save, Pop pop) {
        super(game, save, "pop");
        this.pop = Objects.requireNonNull(pop);
    }

    public static PopScope expect(Scope scope) {
        if (scope instanceof PopScope fs) {
            return fs;
        }

        throw new IllegalArgumentException("given scope is not a pop scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_modifier" -> pop.hasModifier(v.expectString());
            case "has_pop_flag" -> pop.flags.containsKey(v.expectString());
            case "has_trait", "pop_has_trait" -> save.gameState.speciesDb.get(pop.species).traits.hasTrait(v.expectString());
            case "is_enslaved" -> v.expectBoolean() == pop.enslaved;
            case "is_sapient" -> v.expectBoolean() == save.gameState.speciesDb.get(pop.species).sapient;
            default -> super.evaluateValueTrigger(name, v);
        };
    }
}

package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.Species;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.util.Objects;

public class SpeciesScope extends BaseScope {

    private final Species species;

    public SpeciesScope(StellarisGame game, StellarisSave save, int speciesId) {
        this(game, save, save.gameState.speciesDb.get(speciesId));
    }

    public SpeciesScope(StellarisGame game, StellarisSave save, Species species) {
        super(game, save, "species");
        this.species = Objects.requireNonNull(species);
    }

    public static SpeciesScope expect(Scope scope) {
        if (scope instanceof SpeciesScope fs) {
            return fs;
        }

        throw new IllegalArgumentException("given scope is not a species scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_species_flag" -> species.flags.containsKey(v.expectString());
            case "has_trait" -> species.traits.hasTrait(v.expectString());
            case "is_archetype" -> v.expectString().equals(game.common.speciesClasses.get(species.speciesClass).archetype());
            case "is_sapient" -> v.expectBoolean() == species.sapient;
            default -> super.evaluateValueTrigger(name, v);
        };
    }
}

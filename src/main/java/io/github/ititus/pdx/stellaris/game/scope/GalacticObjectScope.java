package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import org.eclipse.collections.api.RichIterable;

import java.util.Objects;

public class GalacticObjectScope extends BaseScope {

    private final GalacticObject galacticObject;

    public GalacticObjectScope(StellarisGame game, StellarisSave save, int galacticObjectId) {
        this(game, save, save.gameState.galacticObjects.get(galacticObjectId));
    }

    public GalacticObjectScope(StellarisGame game, StellarisSave save, GalacticObject galacticObject) {
        super(game, save, "galactic_object");
        this.galacticObject = Objects.requireNonNull(galacticObject);
    }

    public static GalacticObjectScope expect(Scope scope) {
        if (scope instanceof GalacticObjectScope fs) {
            return fs;
        }

        throw new IllegalArgumentException("given scope is not a galactic_object scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_megastructure" -> galacticObject.megaStructures.anySatisfy(id -> v.expectString().equals(save.gameState.megaStructures.get(id).type));
            case "has_pop_faction_flag" -> galacticObject.flags.containsKey(v.expectString());
            default -> super.evaluateValueTrigger(name, v);
        };
    }

    public RichIterable<PlanetScope> getSystemPlanets() {
        return galacticObject.planets.collect(id -> new PlanetScope(game, save, id));
    }
}

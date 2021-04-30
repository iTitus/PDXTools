package io.github.ititus.pdx.stellaris.game.scope.interfaces;

import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.scope.PlanetScope;
import org.eclipse.collections.api.RichIterable;

public interface HabitablePlanetOwnerScope extends Scope {

    static HabitablePlanetOwnerScope expect(Scope scope) {
        if (scope instanceof HabitablePlanetOwnerScope pos) {
            return pos;
        }

        throw new IllegalArgumentException("given scope is not a habitable planet owner scope");
    }

    RichIterable<PlanetScope> getOwnedPlanets();

}

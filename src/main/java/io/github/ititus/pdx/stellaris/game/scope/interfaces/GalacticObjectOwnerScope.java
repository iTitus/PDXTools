package io.github.ititus.pdx.stellaris.game.scope.interfaces;

import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.scope.GalacticObjectScope;
import io.github.ititus.pdx.stellaris.game.scope.PlanetScope;
import org.eclipse.collections.api.RichIterable;

public interface GalacticObjectOwnerScope extends Scope {

    static GalacticObjectOwnerScope expect(Scope scope) {
        if (scope instanceof GalacticObjectOwnerScope pos) {
            return pos;
        }

        throw new IllegalArgumentException("given scope is not a galactic_object owner scope");
    }

    RichIterable<GalacticObjectScope> getSystemsWithinBorder();

}

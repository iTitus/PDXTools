package io.github.ititus.pdx.stellaris.game.scope.interfaces;

import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.shared.Resources;

public interface ResourceOwnerScope extends Scope {

    static ResourceOwnerScope expect(Scope scope) {
        if (scope instanceof ResourceOwnerScope pos) {
            return pos;
        }

        throw new IllegalArgumentException("given scope is not a resource owner scope");
    }

    Resources getResources();

}

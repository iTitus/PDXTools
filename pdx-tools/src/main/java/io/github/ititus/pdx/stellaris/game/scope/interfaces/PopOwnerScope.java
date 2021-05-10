package io.github.ititus.pdx.stellaris.game.scope.interfaces;

import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.scope.PopScope;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.predicate.Predicate;

public interface PopOwnerScope extends Scope {

    static PopOwnerScope expect(Scope scope) {
        if (scope instanceof PopOwnerScope pos) {
            return pos;
        }

        throw new IllegalArgumentException("given scope is not a pop owner scope");
    }

    RichIterable<PopScope> getOwnedPops();

    default int countOwnedPops(Predicate<PopScope> filter) {
        return getOwnedPops().count(filter);
    }
}

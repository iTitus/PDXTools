package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.Federation;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.util.Objects;

public class FederationScope extends StellarisScope {

    private final Federation federation;

    public FederationScope(StellarisGame game, StellarisSave save, int federationId) {
        this(game, save, save.gameState.federations.get(federationId));
    }

    public FederationScope(StellarisGame game, StellarisSave save, Federation federation) {
        super(game, save, "federation");
        this.federation = Objects.requireNonNull(federation);
    }

    public static FederationScope expect(Scope scope) {
        if (scope instanceof FederationScope fs) {
            return fs;
        }

        throw new IllegalArgumentException("given scope is not a federation scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_federation_flag" -> federation.flags.containsKey(v.expectString());
            case "has_federation_perk" -> federation.federationProgression.hasPerk(v.expectString());
            case "has_modifier" -> federation.hasModifier(v.expectString());
            default -> super.evaluateValueTrigger(name, v);
        };
    }
}

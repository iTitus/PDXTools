package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.Leader;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

public class LeaderScope extends BaseScope {

    private final Leader leader;

    public LeaderScope(StellarisGame game, StellarisSave save, Leader leader) {
        this(game, save, "leader", leader);
    }

    public LeaderScope(StellarisGame game, StellarisSave save, String name, Leader leader) {
        super(game, save, name);
        this.leader = leader;
    }

    public static LeaderScope expect(Scope scope) {
        if (scope instanceof LeaderScope ls) {
            return ls;
        }

        throw new IllegalArgumentException("given scope is not a leader scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_trait" -> leader.hasTrait(v.expectString());
            default -> super.evaluateValueTrigger(name, v);
        };
    }
}

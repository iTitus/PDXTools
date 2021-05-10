package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.scope.ScopeHelper;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.Leader;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.util.Objects;

public class LeaderScope extends StellarisScope {

    private final Leader leader;

    public LeaderScope(StellarisGame game, StellarisSave save, int leaderId) {
        this(game, save, save.gameState.leaders.get(leaderId));
    }

    public LeaderScope(StellarisGame game, StellarisSave save, Leader leader) {
        super(game, save, "leader");
        this.leader = Objects.requireNonNull(leader);
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
            case "has_level" -> ScopeHelper.compare(leader.level, v);
            case "has_trait" -> leader.hasTrait(v.expectString());
            default -> super.evaluateValueTrigger(name, v);
        };
    }
}

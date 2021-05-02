package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.PopOwnerScope;
import io.github.ititus.pdx.stellaris.user.save.PopFaction;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import org.eclipse.collections.api.RichIterable;

import java.util.Objects;

public class PopFactionScope extends StellarisScope implements PopOwnerScope {

    private final PopFaction popFaction;

    public PopFactionScope(StellarisGame game, StellarisSave save, int popFactionId) {
        this(game, save, save.gameState.popFactions.get(popFactionId));
    }

    public PopFactionScope(StellarisGame game, StellarisSave save, PopFaction popFaction) {
        super(game, save, "pop_faction");
        this.popFaction = Objects.requireNonNull(popFaction);
    }

    public static PopFactionScope expect(Scope scope) {
        if (scope instanceof PopFactionScope fs) {
            return fs;
        }

        throw new IllegalArgumentException("given scope is not a pop_faction scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_modifier" -> popFaction.hasModifier(v.expectString());
            case "has_pop_faction_flag" -> popFaction.flags.containsKey(v.expectString());
            default -> super.evaluateValueTrigger(name, v);
        };
    }


    @Override
    public RichIterable<PopScope> getOwnedPops() {
        return popFaction.members.collect(id -> new PopScope(game, save, id));
    }
}

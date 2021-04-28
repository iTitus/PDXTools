package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.time.LocalDate;
import java.time.Period;

public abstract class BaseScope implements Scope {

    private static final LocalDate START_DATE = LocalDate.of(2200, 1, 1);

    protected final StellarisGame game;
    protected final StellarisSave save;
    protected final String name;

    protected BaseScope(StellarisGame game, StellarisSave save, String name) {
        this.game = game;
        this.save = save;
        this.name = name;
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "years_passed" -> {
                PdxRelation r = v.getRelation();
                int yearsPassed = Period.between(START_DATE, save.gameState.date).getYears();
                yield r.compare(yearsPassed, v.expectInt());
            }
            default -> throw new IllegalArgumentException("unknown trigger " + name + " for scope " + this.name);
        };
    }
}

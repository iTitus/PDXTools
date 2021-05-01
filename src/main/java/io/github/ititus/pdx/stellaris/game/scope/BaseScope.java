package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxRelation;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public abstract class BaseScope implements Scope {

    private static final LocalDate START_DATE = LocalDate.of(2200, 1, 1);

    protected final StellarisGame game;
    protected final StellarisSave save;
    protected final String name;

    protected BaseScope(StellarisGame game, StellarisSave save, String name) {
        this.game = Objects.requireNonNull(game);
        this.save = Objects.requireNonNull(save);
        this.name = Objects.requireNonNull(name);
    }

    public StellarisGame getGame() {
        return game;
    }

    public StellarisSave getSave() {
        return save;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Scope getScope(String name) {
        if (this.name.equals(name)) {
            return this;
        }

        return switch (name) {
            case "this" -> this;
            default -> throw new IllegalArgumentException("scope " + name + " cannot be accessed from scope " + this.name);
        };
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "exists" -> getScope(v.expectString()) != null;
            case "has_global_flag" -> save.gameState.flags.containsKey(v.expectString());
            case "host_has_dlc" -> save.gameState.requiredDLCs.contains(v.expectString());
            case "years_passed" -> {
                PdxRelation r = v.getRelation();
                int yearsPassed = Period.between(START_DATE, save.gameState.date).getYears();
                yield r.compare(yearsPassed, v.expectInt());
            }
            default -> throw new IllegalArgumentException("unknown trigger " + name + " for scope " + this.name);
        };
    }
}
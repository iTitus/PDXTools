package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.common.technology.Technology;
import io.github.ititus.pdx.stellaris.user.save.Country;
import io.github.ititus.pdx.stellaris.user.save.Leader;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.util.Optional;

public class CountryScope extends BaseScope {

    private final Country country;

    public CountryScope(StellarisGame game, StellarisSave save, Country country) {
        this(game, save, "country", country);
    }

    public CountryScope(StellarisGame game, StellarisSave save, String name, Country country) {
        super(game, save, name);
        this.country = country;
    }

    public static CountryScope expect(Scope scope) {
        if (scope instanceof CountryScope cs) {
            return cs;
        }

        throw new IllegalArgumentException("given scope is not a country scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_origin" -> v.expectString().equals(country.government.origin);
            case "has_ethic" -> country.ethos.ethics.contains(v.expectString());
            default -> super.evaluateValueTrigger(name, v);
        };
    }

    public LeaderScope getResearchLeader(Technology.Area area) {
        Optional<Leader> leader = save.gameState.leaders.stream()
                .filter(l -> l.location.id == country.id)
                .filter(l -> l.location.area == area)
                .filter(l -> "tech".equals(l.location.assignment))
                .findAny();

        return leader.map(l -> new LeaderScope(game, save, l)).orElse(null);
    }

    public int getStarbaseCount(String starbaseSize) {
        return (int) save.gameState.starbaseManager.starbases.stream()
                .filter(s -> s.owner == country.id)
                .filter(s -> starbaseSize.equals(s.level))
                .count();
    }
}

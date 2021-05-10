package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.PopOwnerScope;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.ResourceOwnerScope;
import io.github.ititus.pdx.stellaris.shared.Resources;
import io.github.ititus.pdx.stellaris.user.save.Country;
import io.github.ititus.pdx.stellaris.user.save.Planet;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import org.eclipse.collections.api.RichIterable;

import java.util.Objects;

public class PlanetScope extends StellarisScope implements PopOwnerScope, ResourceOwnerScope {

    private final Planet planet;

    public PlanetScope(StellarisGame game, StellarisSave save, int planetId) {
        this(game, save, save.gameState.planets.get(planetId));
    }

    public PlanetScope(StellarisGame game, StellarisSave save, Planet planet) {
        super(game, save, "planet");
        this.planet = Objects.requireNonNull(planet);
    }

    public static PlanetScope expect(Scope scope) {
        if (scope instanceof PlanetScope fs) {
            return fs;
        }

        throw new IllegalArgumentException("given scope is not a planet scope");
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_deposit" -> v.isBoolean() ? v.expectBoolean() == planet.deposits.notEmpty() : planet.deposits.anySatisfy(id -> v.expectString().equals(save.gameState.deposits.get(id).type));
            case "has_district" -> v.isBoolean() ? v.expectBoolean() == planet.districts.notEmpty() : planet.districts.contains(v.expectString());
            case "has_modifier" -> planet.hasModifier(v.expectString());
            case "has_planet_flag" -> planet.flags.containsKey(v.expectString());
            case "has_planet_modifier" -> planet.planetModifiers.contains(v.expectString());
            case "is_capital" -> {
                Country c = save.gameState.countries.get(planet.owner);
                yield v.expectBoolean() == (c != null && c.capital == planet.id);
            }
            case "is_homeworld" -> {
                Country c = save.gameState.countries.get(planet.owner);
                yield v.expectBoolean() == (c != null && c.startingSystem == planet.id);
            }
            case "is_planet_class" -> v.expectString().equals(planet.planetClass);
            default -> super.evaluateValueTrigger(name, v);
        };
    }

    @Override
    public RichIterable<PopScope> getOwnedPops() {
        return planet.pops.collect(id -> new PopScope(game, save, id));
    }

    @Override
    public Resources getResources() {
        Resources resources = new Resources();
        int size = planet.deposits.size();
        for (int i = 0; i < size; i++) {
            resources = resources.add(game.common.deposits.get(save.gameState.deposits.get(planet.deposits.get(i)).type).getProduced(this));
        }

        return resources;
    }

    public int getDistrictCount(String type) {
        if ("any".equals(type)) {
            return planet.districts.size();
        }

        return planet.districts.count(type::equals);
    }
}

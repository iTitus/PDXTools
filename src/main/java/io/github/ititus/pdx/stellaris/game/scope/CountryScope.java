package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.shared.scope.ScopeHelper;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.common.technology.Technology;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.GalacticObjectOwnerScope;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.HabitablePlanetOwnerScope;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.PopOwnerScope;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.ResourceOwnerScope;
import io.github.ititus.pdx.stellaris.shared.Resources;
import io.github.ititus.pdx.stellaris.user.save.Country;
import io.github.ititus.pdx.stellaris.user.save.Starbase;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.impl.collector.Collectors2;

import java.util.Objects;

public class CountryScope extends BaseScope implements HabitablePlanetOwnerScope, PopOwnerScope, GalacticObjectOwnerScope, ResourceOwnerScope {

    private final Country country;

    public CountryScope(StellarisGame game, StellarisSave save, int countryId) {
        this(game, save, save.gameState.countries.get(countryId));
    }

    public CountryScope(StellarisGame game, StellarisSave save, Country country) {
        super(game, save, "country");
        this.country = Objects.requireNonNull(country);
    }

    public static CountryScope expect(Scope scope) {
        if (scope instanceof CountryScope cs) {
            return cs;
        }

        throw new IllegalArgumentException("given scope is not a country scope");
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public Scope getScope(String name) {
        return switch (name) {
            case "federation" -> country.federation != -1 ? new FederationScope(game, save, country.federation) : null;
            case "owner_species" -> country.founderSpecies != -1 ? new SpeciesScope(game, save, country.founderSpecies) : null; // FIXME: dominant species != founder species
            default -> super.getScope(name);
        };
    }

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_ascension_perk" -> country.ascensionPerks.contains(v.expectString());
            case "has_authority" -> v.expectString().equals(country.government.authority);
            case "has_civic" -> country.government.civics.contains(v.expectString());
            case "has_country_flag" -> country.flags.containsKey(v.expectString());
            case "has_ethic" -> country.ethos.ethics.contains(v.expectString());
            case "has_federation" -> (country.federation != -1) == v.expectBoolean();
            case "has_modifier" -> country.timedModifiers.contains(v.expectString());
            case "has_origin" -> v.expectString().equals(country.government.origin);
            case "has_policy_flag" -> country.policyFlags.contains(v.expectString());
            case "has_seen_any_bypass" -> country.seenBypassTypes.contains(v.expectString());
            case "has_technology" -> country.techStatus.hasTech(v.expectString());
            case "has_tradition" -> country.traditions.contains(v.expectString());
            case "has_trait" -> save.gameState.speciesDb.get(country.founderSpecies).traits.hasTrait(v.expectString()); // FIXME: dominant species != founder species
            case "has_valid_civic" -> country.government.civics.contains(v.expectString()) && true; // FIXME: evaluate civic.possible
            case "is_ai" -> v.expectBoolean() != save.gameState.players.anySatisfy(p -> p.country == country.id);
            case "is_country_type" -> v.expectString().equals(country.type);
            case "is_galactic_community_member" -> v.expectBoolean() == save.gameState.galacticCommunity.members.contains(country.id);
            case "num_owned_planets" -> ScopeHelper.compare(country.ownedPlanets.size(), v);
            case "num_communications" -> ScopeHelper.compare(country.relationsManager.relations.count(r_ -> r_.communications), v);
            case "owns_any_bypass" -> save.gameState.bypasses.anySatisfy(b -> b.owner.id == country.id && v.expectString().equals(b.type));
            default -> super.evaluateValueTrigger(name, v);
        };
    }

    public LeaderScope getResearchLeader(Technology.Area area) {
        int leaderId = country.techStatus.leaders.getLeader(area);
        return leaderId != -1 ? new LeaderScope(game, save, leaderId) : null;
    }

    public int getStarbaseCount(String starbaseSize) {
        return (int) save.gameState.starbaseManager.starbases.stream()
                .filter(s -> s.owner == country.id)
                .filter(s -> starbaseSize.equals(s.level))
                .count();
    }

    @Override
    public RichIterable<PlanetScope> getOwnedPlanets() {
        return country.ownedPlanets.collect(id -> new PlanetScope(game, save, id));
    }

    public RichIterable<PlanetScope> getPlanetsWithinBorder() {
        return save.gameState.galacticObjects.stream()
                .filter(go -> {
                    Starbase starbase = save.gameState.starbaseManager.get(go.starbase);
                    return starbase != null && starbase.owner == country.id;
                })
                .flatMapToInt(go -> go.planets.primitiveStream())
                .mapToObj(p -> new PlanetScope(game, save, p))
                .collect(Collectors2.toList());
    }

    @Override
    public RichIterable<PopScope> getOwnedPops() {
        return save.gameState.planets.planets.stream()
                .filter(p -> p.owner == country.id)
                .flatMapToInt(p -> p.pops.primitiveStream())
                .mapToObj(id -> new PopScope(game, save, id))
                .collect(Collectors2.toList());
    }

    @Override
    public RichIterable<GalacticObjectScope> getSystemsWithinBorder() {
        return save.gameState.galacticObjects.stream()
                .filter(go -> {
                    Starbase starbase = save.gameState.starbaseManager.get(go.starbase);
                    return starbase != null && starbase.owner == country.id;
                })
                .map(go -> new GalacticObjectScope(game, save, go))
                .collect(Collectors2.toList());
    }

    public RichIterable<CountryScope> getRelations() {
        return country.relationsManager.relations.collect(r -> new CountryScope(game, save, r.country));
    }

    public Iterable<CountryScope> getNeighborCountries() {
        return country.relationsManager.relations.stream()
                .filter(r -> r.borders)
                .mapToInt(r -> r.country)
                .mapToObj(id -> new CountryScope(game, save, id))
                .collect(Collectors2.toList());
    }

    @Override
    public Resources getResources() {
        return country.modules.standardEconomyModule != null ? country.modules.standardEconomyModule.resources : new Resources();
    }
}

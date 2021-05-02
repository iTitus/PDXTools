package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.GalacticObjectOwnerScope;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.HabitablePlanetOwnerScope;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.PopOwnerScope;
import io.github.ititus.pdx.stellaris.user.save.Sector;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import org.eclipse.collections.api.RichIterable;

import java.util.Objects;

public class SectorScope extends StellarisScope implements HabitablePlanetOwnerScope, PopOwnerScope, GalacticObjectOwnerScope {

    private final Sector sector;

    public SectorScope(StellarisGame game, StellarisSave save, long sectorId) {
        this(game, save, save.gameState.sectors.get(sectorId));
    }

    public SectorScope(StellarisGame game, StellarisSave save, Sector sector) {
        super(game, save, "sector");
        this.sector = Objects.requireNonNull(sector);
    }

    public static SectorScope expect(Scope scope) {
        if (scope instanceof SectorScope cs) {
            return cs;
        }

        throw new IllegalArgumentException("given scope is not a sector scope");
    }

    public Sector getSector() {
        return sector;
    }

    @Override
    public RichIterable<PlanetScope> getOwnedPlanets() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RichIterable<PopScope> getOwnedPops() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RichIterable<GalacticObjectScope> getSystemsWithinBorder() {
        throw new UnsupportedOperationException();
    }
}

package io.github.ititus.pdx.stellaris.game.scope;

import io.github.ititus.pdx.shared.scope.Scope;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.scope.interfaces.ResourceOwnerScope;
import io.github.ititus.pdx.stellaris.shared.Resources;
import io.github.ititus.pdx.stellaris.user.save.Deposit;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;

import java.util.Objects;

public class DepositScope extends BaseScope implements ResourceOwnerScope {

    private final Deposit deposit;

    public DepositScope(StellarisGame game, StellarisSave save, int depositId) {
        this(game, save, save.gameState.deposits.get(depositId));
    }

    public DepositScope(StellarisGame game, StellarisSave save, Deposit deposit) {
        super(game, save, "deposit");
        this.deposit = Objects.requireNonNull(deposit);
    }

    public static DepositScope expect(Scope scope) {
        if (scope instanceof DepositScope cs) {
            return cs;
        }

        throw new IllegalArgumentException("given scope is not a deposit scope");
    }

    public Deposit getDeposit() {
        return deposit;
    }

    @Override
    public Resources getResources() {
        return game.common.deposits.get(deposit.type).getProduced(new PlanetScope(game, save, deposit.planet));
    }
}

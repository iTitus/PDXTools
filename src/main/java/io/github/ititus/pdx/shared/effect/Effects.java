package io.github.ititus.pdx.shared.effect;

import io.github.ititus.pdx.stellaris.game.StellarisGame;

public class Effects {

    private final StellarisGame game;

    public Effects(StellarisGame game) {
        this.game = game;
    }

    public StellarisGame getGame() {
        return game;
    }
}

package io.github.ititus.pdx.stellaris.game;

import java.io.File;

public class StellarisGame {

    private final File installDir;

    public StellarisGame(String installDirPath) {
        this(new File(installDirPath));
    }

    public StellarisGame(File installDir) {
        if (installDir == null || !installDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
    }

}

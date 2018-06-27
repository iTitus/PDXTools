package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxlocalisation.PDXLocalisation;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisationParser;

import java.io.File;

public class StellarisGame {

    private final File installDir;
    private final PDXLocalisation localisation;

    public StellarisGame(String installDirPath) {
        this(new File(installDirPath));
    }

    public StellarisGame(File installDir) {
        if (installDir == null || !installDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;

        this.localisation = PdxLocalisationParser.parse(installDir);
    }

    public File getInstallDir() {
        return installDir;
    }

    public PDXLocalisation getLocalisation() {
        return localisation;
    }
}

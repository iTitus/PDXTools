package io.github.ititus.pdx.stellaris.game.common;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.File;

public class Common {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "HOW_TO_MAKE_NEW_SHIPS.txt", "common/edicts/README.txt",
            // V value of HSV color is between 1.0 and 2.0
            "planet_classes/00_planet_classes.txt",
            // Missing relation sign in object
            "map_modes/00_map_modes.txt", "random_names/00_empire_names.txt", "random_names/00_war_names.txt", "solar_system_initializers/hostile_system_initializers.txt"
    );
    private static final IFileFilter FILTER = new FileExtensionFilter("txt");

    private final File installDir, commonDir;
    private final PdxRawDataLoader commonDataLoader;

    private final PlanetClasses planetClasses;

    public Common(File installDir, File commonDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !installDir.isDirectory() || commonDir == null || !commonDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.commonDir = commonDir;

        this.commonDataLoader = new PdxRawDataLoader(commonDir, BLACKLIST, FILTER, index, progressMessageUpdater);
        this.planetClasses = this.commonDataLoader.getRawData().getObject("planet_classes").getAs(PlanetClasses::new);
    }

    public File getInstallDir() {
        return installDir;
    }

    public File getCommonDir() {
        return commonDir;
    }

    public PdxRawDataLoader getCommonDataLoader() {
        return commonDataLoader;
    }

    public PlanetClasses getPlanetClasses() {
        return planetClasses;
    }
}

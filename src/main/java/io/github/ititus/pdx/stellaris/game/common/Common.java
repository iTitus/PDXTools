package io.github.ititus.pdx.stellaris.game.common;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.nio.file.Files;
import java.nio.file.Path;

public class Common {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "component_templates/", "edicts/README.txt", "HOW_TO_MAKE_NEW_SHIPS.txt", "README_weapon_component_stat_docs.txt",
            // V value of HSV color is between 1.0 and 2.0
            "planet_classes/00_planet_classes.txt",
            // Error in script parsing
            "random_names/00_empire_names.txt", "random_names/00_war_names.txt",
            "scripted_effects/archaeology_event_effects.txt"
    );
    private static final IPathFilter FILTER = new FileExtensionFilter("txt");

    private final Path installDir, commonDir;
    private final PdxRawDataLoader commonDataLoader;

    private final PlanetClasses planetClasses;

    public Common(Path installDir, Path commonDir, int index,
                  StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir) || commonDir == null || !Files.isDirectory(commonDir)) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.commonDir = commonDir;

        this.commonDataLoader = new PdxRawDataLoader(commonDir, BLACKLIST, FILTER, index, progressMessageUpdater);
        this.planetClasses = this.commonDataLoader.getRawData().getObjectAs("planet_classes", PlanetClasses::new);
    }

    public Path getInstallDir() {
        return installDir;
    }

    public Path getCommonDir() {
        return commonDir;
    }

    public PdxRawDataLoader getCommonDataLoader() {
        return commonDataLoader;
    }

    public PlanetClasses getPlanetClasses() {
        return planetClasses;
    }
}

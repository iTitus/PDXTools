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
            "anomalies/readme.txt", "anomalies/TODO_commented_out_missing_category.txt",
            "archaeological_site_types/example.txt", "component_templates/README_weapon_component_stat_docs.txt",
            "edicts/README.txt", "governments/readme_requirements.txt", "HOW_TO_MAKE_NEW_SHIPS.txt",
            "megastructures/README.txt", "tradition_categories/README.txt", "traditions/README.txt",
            // Error in script parsing
            "random_names/00_empire_names.txt", "random_names/00_war_names.txt",
            "scripted_effects/archaeology_event_effects.txt",
            // Handled separately
            "planet_classes"
    );
    private static final IPathFilter FILTER = new FileExtensionFilter("txt");

    private final Path installDir;
    private final Path commonDir;

    private final PlanetClasses planetClasses;

    private final PdxRawDataLoader commonDataLoader;

    public Common(Path installDir, Path commonDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir) || commonDir == null || !Files.isDirectory(commonDir)) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.commonDir = commonDir;

        int steps = 2;

        progressMessageUpdater.updateProgressMessage(index, true, 0, steps, "Loading planet_classes");
        this.planetClasses = new PlanetClasses(commonDir.resolve("planet_classes"));

        progressMessageUpdater.updateProgressMessage(index, true, 1, steps, "Loading Raw Common Data");
        this.commonDataLoader = new PdxRawDataLoader(commonDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 2, steps, "Done");
    }

    public Path getInstallDir() {
        return installDir;
    }

    public Path getCommonDir() {
        return commonDir;
    }

    public PlanetClasses getPlanetClasses() {
        return planetClasses;
    }

    public PdxRawDataLoader getCommonDataLoader() {
        return commonDataLoader;
    }
}

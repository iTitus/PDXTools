package io.github.ititus.pdx.stellaris.game.common;

import io.github.ititus.data.Lazy;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.common.deposits.Deposits;
import io.github.ititus.pdx.stellaris.game.common.planet_classes.PlanetClasses;
import io.github.ititus.pdx.stellaris.game.common.technology.Technologies;
import io.github.ititus.pdx.stellaris.game.common.technology.category.TechnologyCategories;
import io.github.ititus.pdx.stellaris.game.common.technology.tier.TechnologyTiers;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Stream.concat;

public class Common {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "achievements", "anomalies/readme.txt", "anomalies/TODO_commented_out_missing_category.txt",
            "archaeological_site_types/example.txt", "buildings/00_example.txt", "button_effects/example.txt",
            "component_templates/README_weapon_component_stat_docs.txt", "dynamic_text/example.txt",
            "economic_plans/00_example.txt", "edicts/README.txt", "federation_law_categories/00_example.txt",
            "federation_laws/00_example.txt", "galactic_focuses/00_example.txt", "governments/readme_requirements.txt",
            "HOW_TO_MAKE_NEW_SHIPS.txt", "lawsuits/00_example.txt", "megastructures/README.txt",
            "resolution_categories/00_example.txt", "resolutions/00_example.txt",
            "solar_system_initializers/example.txt", "tradition_categories/README.txt", "traditions/README.txt",
            "war_goals/wg_example.txt",
            // Handled separately
            "scripted_variables",
            // TODO: needs "scripted_variables" (for variables)
            "artifact_actions", "buildings", "component_templates", "decisions", "edicts", "pop_categories", "pop_jobs",
            "scripted_effects", "ship_sizes", "special_projects",
            // TODO: uses math with constants, syntax: @\[<math expression>]
            "scripted_effects/archaeology_event_effects.txt"
    );
    private static final IPathFilter FILTER = new FileExtensionFilter("txt");

    public final PlanetClasses planetClasses;
    public final Deposits deposits;
    public final Technologies technologies;
    public final TechnologyCategories technologyCategories;
    public final TechnologyTiers technologyTiers;

    private final Path installDir;
    private final Path commonDir;
    private final Lazy<Path[]> scriptedVariableFiles;
    private final PdxRawDataLoader commonDataLoader;

    public Common(Path installDir, Path commonDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir) || commonDir == null || !Files.isDirectory(commonDir)) {
            throw new IllegalArgumentException();
        }

        this.installDir = installDir;
        this.commonDir = commonDir;
        this.scriptedVariableFiles = Lazy.of(() -> {
            try (Stream<Path> stream = Files.list(commonDir.resolve("scripted_variables"))) {
                return stream
                        .filter(Files::isRegularFile)
                        .sorted(IOUtil.ASCIIBETICAL)
                        .toArray(Path[]::new);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

        int steps = 6;

        progressMessageUpdater.updateProgressMessage(index, true, 0, steps, "Loading planet_classes");
        this.planetClasses = loadObject("planet_classes").getAs(PlanetClasses::new);

        progressMessageUpdater.updateProgressMessage(index, true, 1, steps, "Loading deposits");
        this.deposits = loadObject("deposits").getAs(Deposits::new);

        progressMessageUpdater.updateProgressMessage(index, true, 2, steps, "Loading technology");
        this.technologies = loadObject("technology").getAs(Technologies::new);

        progressMessageUpdater.updateProgressMessage(index, true, 3, steps, "Loading technology/category");
        this.technologyCategories = loadObject("technology/category").getAs(TechnologyCategories::new);

        progressMessageUpdater.updateProgressMessage(index, true, 4, steps, "Loading technology/tier");
        this.technologyTiers = loadObject("technology/tier").getAs(TechnologyTiers::new);

        progressMessageUpdater.updateProgressMessage(index, true, 5, steps, "Loading Raw Common Data");
        // FIXME: disabled because it is slow
        this.commonDataLoader = null; // new PdxRawDataLoader(commonDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 6, steps, "Done");
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

    private PdxScriptObject loadObject(String dir) {
        Path[] files;
        try (Stream<Path> stream = Files.list(commonDir.resolve(dir))) {
            files = concat(
                    stream(scriptedVariableFiles.get()),
                    stream
                            .filter(Files::isRegularFile)
                            .filter(FILTER)
                            .filter(p -> {
                                Path r = commonDir.relativize(p);
                                return BLACKLIST.stream().noneMatch(r::startsWith);
                            })
                            .sorted(IOUtil.ASCIIBETICAL)
            )
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return PdxScriptParser.parseWithDefaultPatches(files).expectObject();
    }
}

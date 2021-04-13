package io.github.ititus.pdx.stellaris.game.common;

import io.github.ititus.data.Lazy;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.common.deposits.Deposit;
import io.github.ititus.pdx.stellaris.game.common.planet_classes.PlanetClasses;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
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
            // Error in script parsing
            "random_names/00_empire_names.txt", "random_names/00_war_names.txt",
            "scripted_effects/archaeology_event_effects.txt",
            // Handled separately
            "scripted_variables"
    );
    private static final IPathFilter FILTER = new FileExtensionFilter("txt");

    public final PlanetClasses planetClasses;
    public final ImmutableMap<String, Deposit> deposits;

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
                        .filter(Objects::nonNull)
                        .filter(Files::isRegularFile)
                        .sorted(IOUtil.ASCIIBETICAL)
                        .toArray(Path[]::new);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

        int steps = 2;

        progressMessageUpdater.updateProgressMessage(index, true, 0, steps, "Loading planet_classes");
        this.planetClasses = loadObject("planet_classes").getAs(PlanetClasses::new);

        progressMessageUpdater.updateProgressMessage(index, true, 0, steps, "Loading deposits");
        PdxScriptObject o = loadObject("deposits");
        this.deposits = o.getAsStringObjectMap(Deposit::new);
        // o.getUsageStatistic().getCustomErrorStrings().forEach(System.out::println);

        progressMessageUpdater.updateProgressMessage(index, true, 1, steps, "Loading Raw Common Data");
        // TODO: re-enable once there is a plan for the global variables in "scripted_variables"
        this.commonDataLoader = null; // new PdxRawDataLoader(commonDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 2, steps, "Done");
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
                            .filter(Objects::nonNull)
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

        return PdxScriptParser.parse(files).expectObject();
    }
}

package io.github.ititus.pdx.stellaris.game.common;

import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.common.deposits.Deposits;
import io.github.ititus.pdx.stellaris.game.common.planet_classes.PlanetClasses;
import io.github.ititus.pdx.stellaris.game.common.species_classes.SpeciesClass;
import io.github.ititus.pdx.stellaris.game.common.technology.Technologies;
import io.github.ititus.pdx.stellaris.game.common.technology.category.TechnologyCategories;
import io.github.ititus.pdx.stellaris.game.common.technology.tier.TechnologyTiers;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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
            // TODO: uses math with constants, syntax: @\[<math expression>]
            "scripted_effects/archaeology_event_effects.txt"
    );
    // TODO: needs "scripted_variables" (for variables) "artifact_actions", "buildings", "component_templates", "decisions", "edicts", "pop_categories", "pop_jobs","scripted_effects", "ship_sizes", "special_projects",
    private static final PathFilter FILTER = new FileExtensionFilter("txt");

    public final Deposits deposits;
    public final PlanetClasses planetClasses;
    public final ImmutableMap<String, SpeciesClass> speciesClasses;
    public final Technologies technologies;
    public final TechnologyCategories technologyCategories;
    public final TechnologyTiers technologyTiers;

    private final StellarisGame game;
    private final Path commonDir;
    private final PdxRawDataLoader commonDataLoader;

    public Common(StellarisGame game, Path commonDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (commonDir == null || !Files.isDirectory(commonDir)) {
            throw new IllegalArgumentException();
        }

        this.game = game;
        this.commonDir = commonDir;

        int steps = 6;
        int progress = 0;

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading deposits");
        this.deposits = loadObject("deposits").getAs(o_ -> new Deposits(game, o_));

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading planet_classes");
        this.planetClasses = loadObject("planet_classes").getAs(PlanetClasses::new);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading species_classes");
        this.speciesClasses = loadObject("species_classes").getAsStringObjectMap(SpeciesClass::of);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading technology");
        this.technologies = loadObject("technology").getAs(o -> new Technologies(game, o));

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading technology/category");
        this.technologyCategories = loadObject("technology/category").getAs(TechnologyCategories::new);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading technology/tier");
        this.technologyTiers = loadObject("technology/tier").getAs(TechnologyTiers::new);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading Raw Common Data");
        // FIXME: disabled because it is slow
        this.commonDataLoader = null; // new PdxRawDataLoader(commonDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, progress, steps, "Done");
    }

    public StellarisGame getGame() {
        return game;
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
            files = stream
                    .filter(Files::isRegularFile)
                    .filter(FILTER)
                    .filter(p -> {
                        Path r = commonDir.relativize(p);
                        return BLACKLIST.stream().noneMatch(r::startsWith);
                    })
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return PdxScriptParser.parseWithDefaultPatches(game.scriptedVariables, files).expectObject();
    }
}

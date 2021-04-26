package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxasset.PdxAssets;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.shared.trigger.*;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.common.Common;
import io.github.ititus.pdx.stellaris.game.dlc.StellarisDLCs;
import io.github.ititus.pdx.stellaris.game.gfx.Gfx;
import io.github.ititus.pdx.stellaris.game.trigger.*;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class StellarisGame {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "ChangeLog.txt", "ChangeLogBlank.txt", "checksum_manifest.txt", "console_history.txt",
            "interface/credits.txt", "interface/credits_l_simp_chinese.txt", "interface/reference.txt",
            "interface/startup_info.txt", "licenses", "locales", "pdx_browser", "pdx_launcher/game/motd.txt",
            "previewer_assets/previewer_filefilter.txt", "steam_appid.txt", "ThirdPartyLicenses.txt",
            // Handled separately
            "common", "dlc", "gfx", "localisation", "localisation_synced", "pdx_launcher/common/localisation",
            "pdx_online_assets/localisation",
            // TODO: uses math with constants, syntax: @[<math expression>]
            "interface/outliner.gfx",
            // TODO: needs "scripted_variables" (for variables)
            "events",
            // TODO: needs "interface" (variables)
            "interface/planet_view.gui"
    );
    private static final IPathFilter SCRIPT = new FileExtensionFilter("txt");
    // private static final IPathFilter FILTER = new FileExtensionFilter("asset", "dlc", "gfx", "gui", "settings", "sfx", "txt");

    public final Triggers triggers;
    public final ImmutableMap<String, IPdxScript> scriptedVariables;
    public final Common common;
    public final Gfx gfx;
    public final StellarisDLCs dlcs;
    public final PdxLocalisation localisation;
    public final PdxAssets assets;

    private final Path installDir;
    private final PdxRawDataLoader rawDataLoader;

    public StellarisGame(Path installDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir)) {
            throw new IllegalArgumentException();
        }

        this.installDir = installDir;

        int steps = 9;
        int progress = 0;

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Creating triggers");
        this.triggers = new Triggers();
        addEngineTriggers();

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading common/scripted_variables");
        MutableMap<String, IPdxScript> variables = Maps.mutable.empty();
        PdxScriptParser.parseWithDefaultPatches(variables, findScriptFiles("common/scripted_variables")).expectEmpty();
        this.scriptedVariables = variables.toImmutable();

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading common/scripted_triggers");
        IPdxScript s = PdxScriptParser.parseWithDefaultPatches(this.scriptedVariables, findScriptFiles("common/scripted_triggers"));
        this.triggers.addScriptedTriggers(s);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading common");
        this.common = new Common(this, installDir.resolve("common"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading gfx");
        this.gfx = new Gfx(installDir, installDir.resolve("gfx"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading dlc");
        // FIXME: disabled because it is slow
        this.dlcs = null; // new StellarisDLCs(installDir, installDir.resolve("dlc"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading Localisation");
        // FIXME: disabled because it is slow
        this.localisation = null; // PdxLocalisationParser.parse(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading Raw Game Data");
        // FIXME: disabled because it is slow
        this.rawDataLoader = null; // new PdxRawDataLoader(installDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading Assets");
        // FIXME: disabled because it is slow
        this.assets = null; // new PdxAssets(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, progress, steps, "Done");
    }

    public static Path[] findScriptFiles(Path parent, String dir) {
        try (Stream<Path> stream = Files.list(parent.resolve(dir))) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(SCRIPT)
                    .sorted(IOUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void addEngineTriggers() {
        // always
        triggers.addEngineTrigger("always", AlwaysTrigger::new);

        // logical operators
        triggers.addEngineTrigger("NOT", NotTrigger::new);
        triggers.addEngineTrigger("OR", OrTrigger::new);
        triggers.addEngineTrigger("AND", AndTrigger::new);
        triggers.addEngineTrigger("NOR", NorTrigger::new);
        triggers.addEngineTrigger("NAND", NandTrigger::new);

        // branching
        addDummyTrigger("if");
        addDummyTrigger("else_if");
        addDummyTrigger("else");

        // scopes
        addScopeTrigger("target");
        addScopeTrigger("orbit");
        addScopeTrigger("planet");
        addScopeTrigger("star");
        addScopeTrigger("branch_office_owner");
        addScopeTrigger("pop");
        addScopeTrigger("species");
        addScopeTrigger("assembling_species");
        addScopeTrigger("built_species");
        addScopeTrigger("declining_species");
        addScopeTrigger("fleet");
        addScopeTrigger("from");
        addScopeTrigger("this");
        addScopeTrigger("root");
        addScopeTrigger("prev");
        addScopeTrigger("capital_scope");
        addScopeTrigger("owner");
        addScopeTrigger("controller");
        addScopeTrigger("fromfrom");
        addScopeTrigger("prevprev");
        addScopeTrigger("prevprevprev");
        addScopeTrigger("prevprevprevprev");
        addScopeTrigger("home_planet");
        addScopeTrigger("last_created_fleet");
        addScopeTrigger("owner_main_species");
        addScopeTrigger("last_created_country");
        addScopeTrigger("last_created_species");
        addScopeTrigger("alliance");
        addScopeTrigger("overlord");
        addScopeTrigger("federation");
        addScopeTrigger("research_station");
        addScopeTrigger("mining_station");
        addScopeTrigger("last_created_pop");
        addScopeTrigger("spaceport");
        addScopeTrigger("last_created_system");
        addScopeTrigger("planet_owner");
        addScopeTrigger("last_created_ambient_object");
        addScopeTrigger("last_created_ship");
        addScopeTrigger("orbital_station");
        addScopeTrigger("last_created_leader");
        addScopeTrigger("owner_species");
        addScopeTrigger("fromfromfrom");
        addScopeTrigger("last_created_army");
        addScopeTrigger("envoy_location_country");
        addScopeTrigger("contact_country");
        addScopeTrigger("sector_capital");
        addScopeTrigger("observation_outpost_owner");
        addScopeTrigger("federation_leader");
        addScopeTrigger("associated_federation");
        addScopeTrigger("sector");
        addScopeTrigger("pop_faction");
        addScopeTrigger("last_created_pop_faction");
        addScopeTrigger("unhappiest_pop");
        addScopeTrigger("heir");
        addScopeTrigger("default_pop_faction");
        addScopeTrigger("fromfromfromfrom");
        addScopeTrigger("megastructure");
        addScopeTrigger("last_refugee_country");
        addScopeTrigger("starbase");
        addScopeTrigger("capital_star");
        addScopeTrigger("system_star");
        addScopeTrigger("no_scope");
        addScopeTrigger("archaeological_site");
        addScopeTrigger("excavator_fleet");
        addScopeTrigger("reverse_first_contact");
        addScopeTrigger("spynetwork");
        addScopeTrigger("growing_species");
        addScopeTrigger("attacker");
        addScopeTrigger("defender");
        addScopeTrigger("leader");
        addScopeTrigger("solar_system");
        addScopeTrigger("space_owner");
        addScopeTrigger("ruler");

        // Engine Triggers for Stellaris
        triggers.addEngineTrigger("any_member", AnyMemberTrigger::new);
        triggers.addEngineTrigger("any_neighbor_country", AnyNeighborCountryTrigger::new);
        triggers.addEngineTrigger("any_owned_planet", AnyOwnedPlanetTrigger::new);
        triggers.addEngineTrigger("any_owned_pop", AnyOwnedPopTrigger::new);
        triggers.addEngineTrigger("any_planet_within_border", AnyPlanetWithinBorderTrigger::new);
        triggers.addEngineTrigger("any_relation", AnyRelationTrigger::new);
        triggers.addEngineTrigger("any_system_planet", AnySystemPlanetTrigger::new);
        triggers.addEngineTrigger("any_system_within_border", AnySystemWithinBorderTrigger::new);
        triggers.addEngineTrigger("count_owned_pop", CountOwnedPopsTrigger::of);
        triggers.addEngineTrigger("count_starbase_sizes", CountStarbaseSizesTrigger::new);
        triggers.addEngineTrigger("research_leader", ResearchLeaderTrigger::new);

        triggers.addEngineTrigger("is_planet_class", IsPlanetClassTrigger::new);
        triggers.addEngineTrigger("has_origin", HasOriginTrigger::new);
        triggers.addEngineTrigger("has_trait", HasTraitTrigger::new);
        triggers.addEngineTrigger("years_passed", YearsPassedTrigger::new);
        triggers.addEngineTrigger("has_technology", HasTechnologyTrigger::new);
        triggers.addEngineTrigger("has_tradition", HasTraditionTrigger::new);
        triggers.addEngineTrigger("has_ethic", HasEthicTrigger::new);
        triggers.addEngineTrigger("host_has_dlc", HostHasDlcTrigger::new);
        triggers.addEngineTrigger("has_country_flag", HasCountryFlagTrigger::new);
        triggers.addEngineTrigger("has_civic", HasCivicTrigger::new);
        triggers.addEngineTrigger("has_valid_civic", HasValidCivicTrigger::new);

        addDummyTrigger("has_ascension_perk");
        addDummyTrigger("has_federation");
        addDummyTrigger("has_deposit");
        addDummyTrigger("has_authority");
        addDummyTrigger("has_modifier");
        addDummyTrigger("has_policy_flag");
        addDummyTrigger("has_ai_personality_behaviour");
        addDummyTrigger("num_owned_planets");
        addDummyTrigger("num_communications");
        addDummyTrigger("is_country_type");
        addDummyTrigger("has_level");
        addDummyTrigger("num_districts");
        addDummyTrigger("is_ai");
        addDummyTrigger("has_resource");
        addDummyTrigger("has_global_flag");
        addDummyTrigger("has_communications");
        addDummyTrigger("has_seen_any_bypass");
        addDummyTrigger("owns_any_bypass");
        addDummyTrigger("is_galactic_community_member");
        addDummyTrigger("is_enslaved");
        addDummyTrigger("has_megastructure");
        addDummyTrigger("pop_has_trait");
        addDummyTrigger("is_sapient");
        addDummyTrigger("has_federation_perk");
        addDummyTrigger("is_archetype");
    }

    private void addDummyTrigger(String name) {
        triggers.addEngineTrigger(name, DummyTrigger.factory(name));
    }

    private void addScopeTrigger(String name) {
        triggers.addEngineTrigger(name, ScopeTrigger.factory(name));
    }

    public Path[] findScriptFiles(String dir) {
        return findScriptFiles(installDir, dir);
    }

    public Path getInstallDir() {
        return installDir;
    }

    public Common getCommon() {
        return common;
    }

    public Gfx getGfx() {
        return gfx;
    }

    public StellarisDLCs getDLCs() {
        return dlcs;
    }

    public PdxLocalisation getLocalisation() {
        return localisation;
    }

    public PdxRawDataLoader getRawDataLoader() {
        return rawDataLoader;
    }

    public PdxAssets getAssets() {
        return assets;
    }
}

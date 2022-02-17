package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.commons.io.FileExtensionFilter;
import io.github.ititus.commons.io.PathFilter;
import io.github.ititus.commons.io.PathUtil;
import io.github.ititus.pdx.pdxasset.PdxAssets;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisationParser;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.shared.ProgressMessageUpdater;
import io.github.ititus.pdx.shared.effect.Effects;
import io.github.ititus.pdx.shared.trigger.*;
import io.github.ititus.pdx.stellaris.game.common.Common;
import io.github.ititus.pdx.stellaris.game.dlc.StellarisDLCs;
import io.github.ititus.pdx.stellaris.game.gfx.Gfx;
import io.github.ititus.pdx.stellaris.game.trigger.*;
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
    private static final PathFilter SCRIPT = new FileExtensionFilter("txt");
    private static final PathFilter FILTER = new FileExtensionFilter("asset", "dlc", "gfx", "gui", "settings", "sfx", "txt");

    public final Triggers triggers;
    public final Effects effects;
    public final ImmutableMap<String, IPdxScript> scriptedVariables;
    public final Common common;
    public final Gfx gfx;
    public final StellarisDLCs dlcs;
    public final PdxLocalisation localisation;
    public final PdxAssets assets;

    private final Path installDir;
    private final PdxRawDataLoader rawDataLoader;

    public StellarisGame(Path installDir, int index, ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir)) {
            throw new IllegalArgumentException();
        }

        this.installDir = installDir;

        int steps = 10;
        int progress = 0;

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading common/scripted_variables");
        MutableMap<String, IPdxScript> variables = Maps.mutable.empty();
        PdxScriptParser.parseWithDefaultPatchesAndCommonVariables(variables, findScriptFiles("common/scripted_variables")).expectEmpty();
        this.scriptedVariables = variables.toImmutable();

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Creating triggers");
        this.triggers = new Triggers(this);
        addEngineTriggers();

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading common/scripted_triggers");
        IPdxScript s = PdxScriptParser.parseWithDefaultPatches(this.scriptedVariables, findScriptFiles("common/scripted_triggers"));
        this.triggers.addScriptedTriggers(s);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Creating effects");
        this.effects = new Effects(this);
        addEngineEffects();

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading common");
        this.common = new Common(this, installDir.resolve("common"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading gfx");
        this.gfx = new Gfx(installDir, installDir.resolve("gfx"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading dlc");
        this.dlcs = new StellarisDLCs(installDir, installDir.resolve("dlc"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading Localisation");
        this.localisation = PdxLocalisationParser.parse(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading Raw Game Data");
        // FIXME: disabled because it is slow
        this.rawDataLoader = null; // new PdxRawDataLoader(installDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, progress++, steps, "Loading Assets");
        // FIXME: disabled because it is slow
        this.assets = /*null; //*/ new PdxAssets(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, progress, steps, "Done");
    }

    public static Path[] findScriptFiles(Path parent, String dir) {
        try (Stream<Path> stream = Files.list(parent.resolve(dir))) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(SCRIPT)
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void addEngineTriggers() {
        // always
        triggers.addEngineTrigger("always", AlwaysTrigger::new);

        // logical operators
        triggers.addEngineTrigger("not", LogicTrigger.not());
        triggers.addEngineTrigger("or", LogicTrigger.or());
        triggers.addEngineTrigger("and", LogicTrigger.and());
        triggers.addEngineTrigger("nor", LogicTrigger.nor());
        triggers.addEngineTrigger("nand", LogicTrigger.nand());

        // branching
        triggers.addEngineTrigger("if", IfElseTrigger::dummy);
        triggers.addEngineTrigger("else_if", IfElseTrigger::dummy);
        triggers.addEngineTrigger("else", IfElseTrigger::dummy);
        triggers.addEngineTrigger("switch", SwitchTrigger::new);
        triggers.addEngineTrigger("inverted_switch", InvertedSwitchTrigger::new);

        // tooltip
        triggers.addEngineTrigger("hidden_trigger", HiddenTrigger::new);
        triggers.addEngineTrigger("custom_tooltip", CustomTooltipTrigger::new);
        triggers.addEngineTrigger("custom_tooltip_fail", CustomTooltipFailTrigger::new);
        triggers.addEngineTrigger("custom_tooltip_success", CustomTooltipSuccessTrigger::new);
        triggers.addEngineTrigger("conditional_tooltip", ConditionalTooltipTrigger::new);

        // debug
        triggers.addEngineTrigger("log", LogTrigger::new);
        triggers.addEngineTrigger("debug_break", DebugBreakTrigger::new);

        // TODO: find out if 'text' is really a valid trigger

        // scopes
        triggers.addEngineValueTrigger("exists");
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
        triggers.addEngineValueTrigger("is_planet_class");
        triggers.addEngineValueTrigger("is_colonizable");
        triggers.addEngineValueTrigger("has_origin");
        triggers.addEngineValueTrigger("has_trait");
        triggers.addEngineValueTrigger("years_passed");
        triggers.addEngineValueTrigger("has_technology");
        triggers.addEngineValueTrigger("has_tradition");
        triggers.addEngineValueTrigger("has_ethic");
        triggers.addEngineValueTrigger("host_has_dlc");
        triggers.addEngineValueTrigger("has_country_flag");
        triggers.addEngineValueTrigger("has_civic");
        triggers.addEngineValueTrigger("has_valid_civic");
        triggers.addEngineValueTrigger("has_ascension_perk");
        triggers.addEngineValueTrigger("has_federation");
        triggers.addEngineValueTrigger("has_deposit");
        triggers.addEngineValueTrigger("has_authority");
        triggers.addEngineValueTrigger("has_modifier");
        triggers.addEngineValueTrigger("has_policy_flag");
        triggers.addEngineValueTrigger("has_ai_personality_behaviour");
        triggers.addEngineValueTrigger("num_owned_planets");
        triggers.addEngineValueTrigger("num_communications");
        triggers.addEngineValueTrigger("is_country_type");
        triggers.addEngineValueTrigger("has_level");
        triggers.addEngineValueTrigger("is_ai");
        triggers.addEngineValueTrigger("has_global_flag");
        triggers.addEngineValueTrigger("has_communications");
        triggers.addEngineValueTrigger("has_seen_any_bypass");
        triggers.addEngineValueTrigger("owns_any_bypass");
        triggers.addEngineValueTrigger("is_galactic_community_member");
        triggers.addEngineValueTrigger("is_enslaved");
        triggers.addEngineValueTrigger("has_megastructure");
        triggers.addEngineValueTrigger("pop_has_trait");
        triggers.addEngineValueTrigger("is_sapient");
        triggers.addEngineValueTrigger("has_federation_perk");
        triggers.addEngineValueTrigger("is_archetype");
        triggers.addEngineValueTrigger("is_homeworld");
        triggers.addEngineValueTrigger("is_capital");
        triggers.addEngineValueTrigger("has_district");
        triggers.addEngineValueTrigger("is_active_resolution");

        triggers.addEngineTrigger("any_member", AnyMemberTrigger::new);
        triggers.addEngineTrigger("any_neighbor_country", AnyNeighborCountryTrigger::new);
        triggers.addEngineTrigger("any_owned_planet", AnyOwnedPlanetTrigger::new);
        triggers.addEngineTrigger("any_owned_pop", AnyOwnedPopTrigger::new);
        triggers.addEngineTrigger("any_planet_within_border", AnyPlanetWithinBorderTrigger::new);
        triggers.addEngineTrigger("any_relation", AnyRelationTrigger::new);
        triggers.addEngineTrigger("any_system_planet", AnySystemPlanetTrigger::new);
        triggers.addEngineTrigger("any_system_within_border", AnySystemWithinBorderTrigger::new);
        triggers.addEngineTrigger("any_playable_country", AnyPlayableCountryTrigger::new);
        triggers.addEngineTrigger("count_owned_pop", CountOwnedPopsTrigger::new);
        triggers.addEngineTrigger("count_starbase_sizes", CountStarbaseSizesTrigger::new);
        triggers.addEngineTrigger("research_leader", ResearchLeaderTrigger::new);
        triggers.addEngineTrigger("has_resource", HasResourceTrigger::new);
        triggers.addEngineTrigger("num_districts", NumDistrictsTrigger::new);
        triggers.addEngineTrigger("habitability", HabitabilityTrigger::new);
    }

    private void addEngineEffects() {
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

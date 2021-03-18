package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxasset.PdxAssets;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisationParser;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.common.Common;
import io.github.ititus.pdx.stellaris.game.dlc.StellarisDLCs;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.nio.file.Files;
import java.nio.file.Path;

public class StellarisGame {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "ChangeLog.txt", "ChangeLogBlank.txt", "checksum_manifest.txt", "console_history.txt",
            "gfx/fonts/arimo/LICENSE.txt", "interface/credits.txt", "interface/credits_l_simphinese.txt",
            "interface/reference.txt", "interface/startup_info.txt", "licenses", "pdx_launcher/game/motd.txt",
            "previewer_assets/previewer_filefilter.txt", "steam_appid.txt", "ThirdPartyLicenses.txt",
            // Handled separately
            "common", "dlc", "localisation", "localisation_synced", "pdx_launcher/common/localisation",
            "pdx_online_assets/localisation",
            // V value of HSV color is between 1.0 and 2.0
            "flags/colors.txt", "gfx/advisorwindow/advisorwindow_environment.txt",
            "gfx/worldgfx/customization_view_planet.txt", "gfx/worldgfx/customization_view_ringworld.txt",
            "gfx/worldgfx/ship_design_icon.txt", "gfx/worldgfx/ship_details_view.txt", "gfx/worldgfx/system_view.txt",
            // Error in script parsing
            "dlc_metadata/dlc_info.txt", "events/fed_vote_events.txt", "gfx/models/planets/_planetary_entities.asset",
            "gfx/models/ships/titans/mammalian_01/_mammalian_01_titan_meshes.gfx",
            "gfx/models/ships/titans/molluscoid_01/_molluscoid_titan_meshes.gfx", "gfx/particles/_necroid_portrait.gfx",
            "interface/outliner.gfx", "sound/soundeffects.asset"
    );
    private static final IPathFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx");

    private final Path installDir;

    private final Common common;
    private final StellarisDLCs dlcs;
    private final PdxLocalisation localisation;

    private final PdxRawDataLoader rawDataLoader;

    private final PdxAssets assets;

    public StellarisGame(Path installDir, int index,
                         StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir)) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;

        int steps = 5;

        progressMessageUpdater.updateProgressMessage(index, true, 0, steps, "Loading common");
        this.common = new Common(installDir, installDir.resolve("common"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 1, steps, "Loading DLCs");
        this.dlcs = new StellarisDLCs(installDir, installDir.resolve("dlc"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 2, steps, "Loading Localisation");
        this.localisation = PdxLocalisationParser.parse(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 3, steps, "Loading Raw Game Data");
        this.rawDataLoader = new PdxRawDataLoader(installDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 4, steps, "Loading Assets");
        this.assets = new PdxAssets(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 5, steps, "Done");
    }

    public Path getInstallDir() {
        return installDir;
    }

    public Common getCommon() {
        return common;
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

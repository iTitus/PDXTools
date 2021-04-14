package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxasset.PdxAssets;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.common.Common;
import io.github.ititus.pdx.stellaris.game.dlc.StellarisDLCs;
import io.github.ititus.pdx.stellaris.game.gfx.Gfx;
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
    private static final IPathFilter FILTER = new FileExtensionFilter("asset", "dlc", "gfx", "gui", "settings", "sfx", "txt");

    private final Path installDir;

    private final Common common;
    private final Gfx gfx;
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

        int steps = 6;

        progressMessageUpdater.updateProgressMessage(index, true, 0, steps, "Loading common");
        this.common = new Common(installDir, installDir.resolve("common"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 1, steps, "Loading gfx");
        this.gfx = new Gfx(installDir, installDir.resolve("gfx"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 2, steps, "Loading dlc");
        this.dlcs = new StellarisDLCs(installDir, installDir.resolve("dlc"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 3, steps, "Loading Localisation");
        // FIXME: disabled because it is slow
        this.localisation = null; // PdxLocalisationParser.parse(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 4, steps, "Loading Raw Game Data");
        // FIXME: disabled because it is slow
        this.rawDataLoader = null; // new PdxRawDataLoader(installDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 5, steps, "Loading Assets");
        // FIXME: disabled because it is slow
        this.assets = null; // new PdxAssets(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 6, steps, "Done");
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

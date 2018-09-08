package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxlocalisation.PDXLocalisation;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisationParser;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.stellaris.game.common.Common;
import io.github.ititus.pdx.stellaris.game.dlc.StellarisDLCs;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.File;

public class StellarisGame {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "licenses", "ChangeLog.txt", "ChangeLogBlank.txt", "checksum_manifest.txt", "console_history.txt", "interface/credits.txt", "interface/reference.txt", "interface/startup_info.txt", "pdx_launcher/game/motd.txt",
            // Handled separately
            "common", "dlc", "localisation", "localisation_synced", "pdx_launcher/common/localisation", "pdx_online_assets/localisation",
            // Missing curly bracket at the end
            "gfx/models/add_ons/_add_ons_meshes.gfx",
            // V value of HSV color is between 1.0 and 2.0
            "flags/colors.txt", "gfx/advisorwindow/advisorwindow_environment.txt", "gfx/worldgfx/customization_view_planet.txt", "gfx/worldgfx/ship_design_icon.txt", "gfx/worldgfx/ship_details_view.txt", "gfx/worldgfx/system_view.txt",
            // Missing relation sign in object
            "sound/soundeffects.asset",
            // Using '/' as file path
            "previewer_assets/previewer_filefilter.txt"
    );
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx");

    private final File installDir;

    private final Common common;
    private final StellarisDLCs dlcs;
    private final PDXLocalisation localisation;

    private final PdxRawDataLoader rawDataLoader;

    public StellarisGame(String installDirPath, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        this(new File(installDirPath), index, progressMessageUpdater);
    }

    public StellarisGame(File installDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !installDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;

        int STEPS = 4;

        progressMessageUpdater.updateProgressMessage(index, true, 0, STEPS, "Loading common");
        this.common = new Common(installDir, new File(installDir, "common"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 0, STEPS, "Loading DLCs");
        this.dlcs = new StellarisDLCs(installDir, new File(installDir, "dlc"), index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 1, STEPS, "Loading Localisation");
        this.localisation = PdxLocalisationParser.parse(installDir, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, true, 2, STEPS, "Loading Game Data");
        this.rawDataLoader = new PdxRawDataLoader(installDir, BLACKLIST, FILTER, index + 1, progressMessageUpdater);

        progressMessageUpdater.updateProgressMessage(index, false, 3, STEPS, "Done");
    }

    public File getInstallDir() {
        return installDir;
    }

    public Common getCommon() {
        return common;
    }

    public StellarisDLCs getDLCs() {
        return dlcs;
    }

    public PDXLocalisation getLocalisation() {
        return localisation;
    }

    public PdxRawDataLoader getRawDataLoader() {
        return rawDataLoader;
    }
}

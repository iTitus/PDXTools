package io.github.ititus.pdx.stellaris.game;

import io.github.ititus.pdx.pdxlocalisation.PDXLocalisation;
import io.github.ititus.pdx.pdxlocalisation.PdxLocalisationParser;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.stellaris.game.dlc.StellarisDLCs;
import io.github.ititus.pdx.util.CollectionUtil;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;

import java.io.File;
import java.util.Set;

public class StellarisGame {

    private static final Set<String> BLACKLIST = CollectionUtil.setOf(
            // Not PDXScript
            "licenses", "ChangeLog.txt", "ChangeLogBlank.txt", "checksum_manifest.txt", "console_history.txt", "common/HOW_TO_MAKE_NEW_SHIPS.txt", "common/edicts/README.txt", "interface/credits.txt", "interface/reference.txt", "interface/startup_info.txt", "pdx_launcher/game/motd.txt",
            // Handled separately
            "dlc", "localisation", "localisation_synced", "pdx_launcher/common/localisation", "pdx_online_assets/localisation",
            // Missing curly bracket at the end
            "gfx/models/add_ons/_add_ons_meshes.gfx",
            // V value of HSV color is between 1.0 and 2.0
            "common/planet_classes/00_planet_classes.txt", "flags/colors.txt", "gfx/advisorwindow/advisorwindow_environment.txt", "gfx/worldgfx/customization_view_planet.txt", "gfx/worldgfx/ship_design_icon.txt", "gfx/worldgfx/ship_details_view.txt", "gfx/worldgfx/system_view.txt",
            // Missing relation sign in object
            "common/map_modes/00_map_modes.txt", "common/random_names/00_empire_names.txt", "common/random_names/00_war_names.txt", "common/solar_system_initializers/hostile_system_initializers.txt", "sound/soundeffects.asset",
            // Using '/' as file path
            "previewer_assets/previewer_filefilter.txt"
    );
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx");

    private final File installDir;

    private final StellarisDLCs dlcs;
    private final PDXLocalisation localisation;

    private final PdxRawDataLoader rawDataLoader;

    public StellarisGame(String installDirPath) {
        this(new File(installDirPath));
    }

    public StellarisGame(File installDir) {
        if (installDir == null || !installDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;

        this.dlcs = new StellarisDLCs(installDir, new File(installDir, "dlc"));
        this.localisation = PdxLocalisationParser.parse(installDir);

        this.rawDataLoader = new PdxRawDataLoader(installDir, BLACKLIST, FILTER);
    }

    public File getInstallDir() {
        return installDir;
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

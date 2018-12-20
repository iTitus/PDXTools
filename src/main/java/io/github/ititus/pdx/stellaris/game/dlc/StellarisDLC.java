package io.github.ititus.pdx.stellaris.game.dlc;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.File;
import java.io.FileFilter;

public class StellarisDLC {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Missing relation sign in object
            "sound/megacorp_vo.asset"
    );
    private static final FileFilter DLC = new FileExtensionFilter("dlc");
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx", "mod");

    private final File installDir, dlcDir;

    private final String name, localizableName, archivePath, popsId, checksum, category;
    private final int steamId, railId;
    private final boolean affectsChecksum, affectsCompatability, thirdPartyContent;

    private final PdxRawDataLoader dlcArchive;

    public StellarisDLC(File installDir, File dlcDir) {
        if (installDir == null || !installDir.isDirectory() || dlcDir == null || !dlcDir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.installDir = installDir;
        this.dlcDir = dlcDir;

        IPdxScript s = PdxScriptParser.parse(dlcDir.listFiles(DLC)[0]);
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }

        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.localizableName = o.getString("localizable_name");
        this.archivePath = o.getString("archive");
        this.steamId = o.getInt("steam_id");
        this.railId = o.getInt("rail_id");
        this.popsId = o.getString("steam_id");
        this.affectsChecksum = o.getBoolean("affects_checksum");
        this.affectsCompatability = o.getBoolean("affects_compatability");
        this.checksum = o.getString("checksum");
        this.thirdPartyContent = o.getBoolean("third_party_content");
        this.category = o.getString("category");

        this.dlcArchive = new PdxRawDataLoader(new File(installDir, archivePath), BLACKLIST, FILTER, -1, null);
    }

    public File getInstallDir() {
        return installDir;
    }

    public File getDlcDir() {
        return dlcDir;
    }

    public String getName() {
        return name;
    }

    public String getLocalizableName() {
        return localizableName;
    }

    public String getPopsId() {
        return popsId;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getCategory() {
        return category;
    }

    public String getArchivePath() {
        return archivePath;
    }

    public int getSteamId() {
        return steamId;
    }

    public int getRailId() {
        return railId;
    }

    public boolean isAffectsChecksum() {
        return affectsChecksum;
    }

    public boolean isAffectsCompatability() {
        return affectsCompatability;
    }

    public boolean isThirdPartyContent() {
        return thirdPartyContent;
    }

    public PdxRawDataLoader getDlcArchive() {
        return dlcArchive;
    }
}

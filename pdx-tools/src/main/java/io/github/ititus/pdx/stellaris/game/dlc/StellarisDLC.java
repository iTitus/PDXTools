package io.github.ititus.pdx.stellaris.game.dlc;

import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class StellarisDLC {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // TODO: error in script parsing, use patches to load this; problem: file is in a zip archive
            "sound/megacorp_vo.asset"
    );
    private static final PathFilter DLC = new FileExtensionFilter("dlc");
    private static final PathFilter FILTER = new FileExtensionFilter("asset", "dlc", "gfx", "gui", "mod", "settings", "sfx", "txt");

    private final Path installDir, dlcDir;

    private final String name, localizableName, archivePath, popsId, checksum, category;
    private final int steamId, railId;
    private final boolean affectsChecksum, affectsCompatibility, thirdPartyContent;

    private final PdxRawDataLoader dlcArchive;

    public StellarisDLC(Path installDir, Path dlcDir) {
        if (installDir == null || !Files.isDirectory(installDir) || dlcDir == null || !Files.isDirectory(dlcDir)) {
            throw new IllegalArgumentException();
        }

        this.installDir = installDir;
        this.dlcDir = dlcDir;

        Path[] paths;
        try (Stream<Path> stream = Files.list(dlcDir)) {
            paths = stream
                    .filter(DLC)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        if (paths.length != 1) {
            throw new RuntimeException("expected exactly one dlc file but found " + paths.length);
        }

        PdxScriptObject o = PdxScriptParser.parseWithDefaultPatches(paths[0]).expectObject();
        this.name = o.getString("name");
        this.localizableName = o.getString("localizable_name");
        this.archivePath = o.getString("archive");
        this.steamId = o.getInt("steam_id");
        this.railId = o.getInt("rail_id");
        this.popsId = o.getString("pops_id");
        this.affectsChecksum = o.getBoolean("affects_checksum");
        this.affectsCompatibility = o.getBoolean("affects_compatability"); // spelling intentional
        this.checksum = o.getString("checksum");
        this.thirdPartyContent = o.getBoolean("third_party_content");
        this.category = o.getString("category");

        // FIXME: disabled because it is slow
        this.dlcArchive = null; // new PdxRawDataLoader(installDir.resolve(archivePath), BLACKLIST, FILTER);
    }

    public Path getInstallDir() {
        return installDir;
    }

    public Path getDlcDir() {
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

    public boolean isAffectsCompatibility() {
        return affectsCompatibility;
    }

    public boolean isThirdPartyContent() {
        return thirdPartyContent;
    }

    public PdxRawDataLoader getDlcArchive() {
        return dlcArchive;
    }
}

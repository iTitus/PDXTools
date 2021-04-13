package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.nio.file.Files;
import java.nio.file.Path;

public class StellarisMod {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of();
    private static final IPathFilter FILTER = new FileExtensionFilter("asset", "dlc", "gfx", "gui", "mod", "settings", "sfx", "txt");

    private final Path userDataDir, modDescriptorFile;

    private final String name, supportedVersion;
    private final Path modFile;
    private final ImmutableList<String> tags;
    private final int remoteFileId;

    private final PdxRawDataLoader modArchive;

    public StellarisMod(Path userDataDir, Path modDescriptorFile) {
        if (userDataDir == null || !Files.isDirectory(userDataDir) || modDescriptorFile == null || !Files.isRegularFile(modDescriptorFile) || !IOUtil.getExtension(modDescriptorFile).equals("mod")) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;
        this.modDescriptorFile = modDescriptorFile;

        PdxScriptObject o = PdxScriptParser.parse(modDescriptorFile).expectObject();
        this.name = o.getString("name");
        String str = o.getString("archive", null);
        this.modFile = str != null ? Path.of(str) : userDataDir.resolve(o.getString("path"));
        this.tags = o.getListAsEmptyOrStringList("tags");
        str = o.getString("remote_file_id", null);
        this.remoteFileId = str != null ? Integer.parseInt(str) : -1;
        this.supportedVersion = o.getString("supported_version");

        // TODO: Fix this
        this.modArchive = Files.isDirectory(modFile) ? new PdxRawDataLoader(modFile, BLACKLIST, FILTER) : null;
    }

    public Path getUserDataDir() {
        return userDataDir;
    }

    public Path getModDescriptorFile() {
        return modDescriptorFile;
    }

    public String getName() {
        return name;
    }

    public Path getModFile() {
        return modFile;
    }

    public ImmutableList<String> getTags() {
        return tags;
    }

    public int getRemoteFileId() {
        return remoteFileId;
    }

    public String getSupportedVersion() {
        return supportedVersion;
    }

    public PdxRawDataLoader getModArchive() {
        return modArchive;
    }
}

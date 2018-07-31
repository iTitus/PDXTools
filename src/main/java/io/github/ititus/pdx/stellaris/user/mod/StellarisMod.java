package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;

import java.io.File;

public class StellarisMod {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of();
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx", "mod");

    private final File userDataDir, modDescriptorFile;

    private final String name, supportedVersion;
    private final File modFile;
    private final ImmutableList<String> tags;
    private final int remoteFileId;

    private final PdxRawDataLoader modArchive;

    public StellarisMod(File userDataDir, File modDescriptorFile) {
        if (userDataDir == null || !userDataDir.isDirectory() || modDescriptorFile == null || !modDescriptorFile.isFile() || !IOUtil.getExtension(modDescriptorFile).equals("mod")) {
            throw new IllegalArgumentException();
        }
        this.userDataDir = userDataDir;
        this.modDescriptorFile = modDescriptorFile;

        IPdxScript s = PdxScriptParser.parse(modDescriptorFile);
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException();
        }

        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        String str = o.getString("archive");
        this.modFile = str != null ? new File(str) : new File(userDataDir, o.getString("path"));
        PdxScriptList l = o.getList("tags");
        this.tags = l != null ? l.getAsStringList() : Lists.immutable.empty();
        str = o.getString("remote_file_id");
        this.remoteFileId = str != null ? Integer.parseInt(str) : -1;
        this.supportedVersion = o.getString("supported_version");

        // TODO: Fix this
        this.modArchive = modFile.isDirectory() ? new PdxRawDataLoader(modFile, BLACKLIST, FILTER) : null;
    }

    public File getUserDataDir() {
        return userDataDir;
    }

    public File getModDescriptorFile() {
        return modDescriptorFile;
    }

    public String getName() {
        return name;
    }

    public File getModFile() {
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

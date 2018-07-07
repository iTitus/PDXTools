package io.github.ititus.pdx.stellaris.user.mod;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.CollectionUtil;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IFileFilter;
import io.github.ititus.pdx.util.io.IOUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class StellarisMod {

    private static final Set<String> BLACKLIST = CollectionUtil.setOf();
    private static final IFileFilter FILTER = new FileExtensionFilter("txt", "dlc", "asset", "gui", "gfx", "mod");

    private final File modFile;

    private final String name, supportedVersion;
    private final File archiveFile;
    private final List<String> tags;
    private final int remoteFileId;

    private final PdxRawDataLoader modArchive;

    public StellarisMod(File modFile) {
        if (modFile == null || !modFile.exists() || (!modFile.isDirectory() && !IOUtil.getExtension(modFile).equals("mod"))) {
            throw new IllegalArgumentException();
        }
        this.modFile = modFile;

        if (modFile.isDirectory()) {
            // TODO: Parse folder mods
            this.name = null;
            this.archiveFile = null;
            this.tags = null;
            this.remoteFileId = -1;
            this.supportedVersion = null;
        } else {
            IPdxScript s = PdxScriptParser.parse(modFile);
            if (!(s instanceof PdxScriptObject)) {
                throw new IllegalArgumentException();
            }

            PdxScriptObject o = (PdxScriptObject) s;

            this.name = o.getString("name");
            this.archiveFile = new File(o.getString("archive"));
            PdxScriptList l = o.getList("tags");
            this.tags = l != null ? l.getAsStringList() : new ArrayList<>();
            this.remoteFileId = Integer.parseInt(o.getString("remote_file_id"));
            this.supportedVersion = o.getString("supported_version");
        }

        // TODO: Fix this
        this.modArchive = null; // new PdxRawDataLoader(archiveFile, BLACKLIST, FILTER);
    }

    public File getModFile() {
        return modFile;
    }

    public String getName() {
        return name;
    }

    public File getArchiveFile() {
        return archiveFile;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
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

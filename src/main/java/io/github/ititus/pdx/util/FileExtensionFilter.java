package io.github.ititus.pdx.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Locale;

public class FileExtensionFilter implements FileFilter, FilenameFilter {

    private final String[] extensions;

    public FileExtensionFilter(String... extensions) {
        if (extensions == null || extensions.length == 0) {
            throw new IllegalArgumentException();
        }
        this.extensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            String ext = extensions[i];
            if (ext == null || ext.length() == 0) {
                throw new IllegalArgumentException("Each extension must be non-null and not empty");
            }
            this.extensions[i] = ext.toLowerCase(Locale.ENGLISH);
        }
    }

    @Override
    public boolean accept(File dir, String name) {
        return accept(new File(dir, name));
    }

    public boolean accept(File f) {
        if (f != null && !f.isDirectory()) {
            String name = f.getName();
            int i = name.lastIndexOf('.');
            return i > 0 && i < name.length() - 1 && Arrays.stream(extensions).anyMatch(name.substring(i + 1).toLowerCase(Locale.ENGLISH)::equals);
        }
        return false;
    }

    public String[] getExtensions() {
        return Arrays.copyOf(extensions, extensions.length);
    }
}

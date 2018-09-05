package io.github.ititus.pdx.util.io;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

public class FileExtensionFilter implements IFileFilter {

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
    public boolean accept(File f) {
        if (f != null && !f.isDirectory()) {
            String ext = IOUtil.getExtension(f);
            return !ext.isEmpty() && Arrays.asList(extensions).contains(ext);
        }
        return false;
    }

    public String[] getExtensions() {
        return Arrays.copyOf(extensions, extensions.length);
    }
}

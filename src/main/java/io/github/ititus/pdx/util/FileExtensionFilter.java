package io.github.ititus.pdx.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Locale;

public class FileExtensionFilter implements FileFilter, FilenameFilter {

    private final String[] extensions, lowerCaseExtensions;

    public FileExtensionFilter(String... extensions) {
        if (extensions == null || extensions.length == 0) {
            throw new IllegalArgumentException();
        }
        this.extensions = new String[extensions.length];
        this.lowerCaseExtensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            if (extensions[i] == null || extensions[i].length() == 0) {
                throw new IllegalArgumentException(
                        "Each extension must be non-null and not empty");
            }
            this.extensions[i] = extensions[i];
            lowerCaseExtensions[i] = extensions[i].toLowerCase(Locale.ENGLISH);
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
            if (i > 0 && i < name.length() - 1) {
                String desiredExtension = name.substring(i + 1).toLowerCase(Locale.ENGLISH);
                for (String extension : lowerCaseExtensions) {
                    if (desiredExtension.equals(extension)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

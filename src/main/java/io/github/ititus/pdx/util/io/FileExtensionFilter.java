package io.github.ititus.pdx.util.io;

import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.nio.file.Path;
import java.util.Locale;

public class FileExtensionFilter implements IPathFilter {

    private final ImmutableSet<String> extensions;

    public FileExtensionFilter(String... extensions) {
        if (extensions == null || extensions.length == 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < extensions.length; i++) {
            String ext = extensions[i];
            if (ext == null || ext.length() == 0) {
                throw new IllegalArgumentException("Each extension must be non-null and not empty");
            }
            extensions[i] = ext.toLowerCase(Locale.ROOT);
        }
        this.extensions = Sets.immutable.of(extensions);
    }

    @Override
    public boolean test(Path p) {
        if (p != null) {
            String ext = IOUtil.getExtension(p);
            return !ext.isEmpty() && extensions.contains(ext);
        }
        return false;
    }

    public ImmutableSet<String> getExtensions() {
        return extensions;
    }
}

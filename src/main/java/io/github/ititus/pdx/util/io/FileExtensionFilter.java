package io.github.ititus.pdx.util.io;

import io.github.ititus.pdx.util.IOUtil;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class FileExtensionFilter implements PathFilter {

    private final Set<String> extensions;

    public FileExtensionFilter(Collection<? extends String> extensions) {
        this.extensions = extensions.stream()
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableSet());
    }

    public FileExtensionFilter(String... extensions) {
        this.extensions = Arrays.stream(extensions)
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean test(Path p) {
        if (p != null) {
            Optional<String> ext = IOUtil.getExtension(p);
            return ext.isPresent() && extensions.contains(ext.get());
        }

        return false;
    }

    public Set<String> getExtensions() {
        return extensions;
    }
}

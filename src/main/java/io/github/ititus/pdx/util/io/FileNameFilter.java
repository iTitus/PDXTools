package io.github.ititus.pdx.util.io;

import java.nio.file.Path;
import java.util.function.Predicate;

public class FileNameFilter implements IPathFilter {

    private final Predicate<String> nameFilter;

    public FileNameFilter(Predicate<String> nameFilter) {
        this.nameFilter = nameFilter;
    }

    @Override
    public boolean test(Path p) {
        return nameFilter.test(p.getFileName().toString());
    }
}

package io.github.ititus.pdx.util;

import java.io.File;
import java.util.function.Predicate;

public class FileNameFilter implements IFileFilter {

    private final Predicate<String> nameFilter;

    public FileNameFilter(Predicate<String> nameFilter) {
        this.nameFilter = nameFilter;
    }

    @Override
    public boolean accept(File f) {
        return nameFilter.test(f.getName());
    }
}

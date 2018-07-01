package io.github.ititus.pdx.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

@FunctionalInterface
public interface IFileFilter extends FileFilter, FilenameFilter {

    @Override
    boolean accept(File f);

    @Override
    default boolean accept(File dir, String name) {
        return accept(new File(dir, name));
    }

    default IFileFilter and(IFileFilter filter) {
        return f -> accept(f) && filter.accept(f);
    }

    default IFileFilter or(IFileFilter filter) {
        return f -> accept(f) || filter.accept(f);
    }

    default IFileFilter xor(IFileFilter filter) {
        return f -> accept(f) ^ filter.accept(f);
    }
}

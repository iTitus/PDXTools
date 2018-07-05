package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.Pair;
import io.github.ititus.pdx.util.io.IFileFilter;
import io.github.ititus.pdx.util.io.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PdxRawDataLoader implements PdxConstants {

    private final File dir;
    private final Set<String> blacklist;
    private final IFileFilter filter;
    private final List<Pair<String, Throwable>> errors;

    private PdxScriptObject rawData;

    public PdxRawDataLoader(File dir, Collection<String> blacklist, IFileFilter filter) {
        if (dir == null || !dir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.dir = dir;
        this.blacklist = new HashSet<>(blacklist);
        this.filter = filter;
        this.errors = new ArrayList<>();
    }

    public File getDir() {
        return dir;
    }

    public PdxScriptObject getRawData() {
        return rawData;
    }

    public List<Pair<String, Throwable>> getErrors() {
        return Collections.unmodifiableList(errors.stream().sorted(Comparator.comparing((Function<Pair<String, Throwable>, String>) String::valueOf).thenComparing(Pair::getKey)).collect(Collectors.toList()));
    }

    public PdxRawDataLoader load() {
        try {
            this.rawData = parseFolder(dir.getCanonicalPath(), dir);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return this;
    }

    private PdxScriptObject parseFolder(String installDirPath, File dir) {
        if (dir != null && dir.isDirectory() && !blacklist.contains(IOUtil.getRelativePath(installDirPath, dir))) {
            File[] files = dir.listFiles();
            if (files != null) {
                PdxScriptObject.Builder b = PdxScriptObject.builder();
                Arrays.stream(files).filter(Objects::nonNull).sorted(IOUtil.asciibetical(installDirPath)).forEachOrdered(f -> {
                    if (f.isDirectory()) {
                        PdxScriptObject o = parseFolder(installDirPath, f);
                        if (o != null) {
                            b.add(f.getName(), o);
                        }
                    } else {
                        IPdxScript s = parseFile(installDirPath, f);
                        if (s != null) {
                            b.add(f.getName(), s);
                        }
                    }
                });
                PdxScriptObject o = b.build(PdxRelation.EQUALS);
                return o.size() > 0 ? o : null;
            }
        }
        return null;
    }

    private IPdxScript parseFile(String installDirPath, File f) {
        if (f != null && f.isFile()) {
            String path = IOUtil.getRelativePath(installDirPath, f);
            if (!blacklist.contains(path) && filter.accept(f)) {
                IPdxScript s;
                try {
                    s = PdxScriptParser.parse(f);
                } catch (Exception e) {
                    Throwable t = e.getCause() != null ? e.getCause() : e;
                    Throwable[] suppressed = t.getSuppressed();
                    Throwable cause = t.getCause();
                    System.out.println("Error while parsing " + path + ": " + t + (suppressed != null && suppressed.length > 0 ? ", Supressed: " + Arrays.toString(suppressed) : EMPTY) + (cause != null ? ", Caused By: " + cause : EMPTY));
                    errors.add(Pair.of(path, t));
                    s = null;
                }
                if (s != null) {
                    boolean success = false;
                    if (s instanceof PdxScriptObject) {
                        if (((PdxScriptObject) s).size() > 0) {
                            success = true;
                        }
                    } else if (s instanceof PdxScriptList) {
                        if (((PdxScriptList) s).size() > 0) {
                            success = true;
                        }
                    } else {
                        throw new RuntimeException("Unexpected return value from parsing: " + s.getClass().getTypeName());
                    }
                    if (success) {
                        return s;
                    }
                }
            }
        }
        return null;
    }
}

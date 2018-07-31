package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.io.IFileFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.ZipUtil;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.tuple.Tuples;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PdxRawDataLoader implements PdxConstants {

    private final MutableSet<Pair<String, Throwable>> errors;

    private final File file;
    private final ImmutableSet<String> blacklist;
    private final IFileFilter filter;

    private final PdxScriptObject rawData;

    public PdxRawDataLoader(File file, ImmutableSet<String> blacklist, IFileFilter filter) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException();
        }
        this.errors = Sets.mutable.empty();
        this.file = file;
        this.blacklist = Sets.immutable.ofAll(blacklist);
        this.filter = filter;
        this.rawData = load();
    }

    private static IPdxScript parse(ZipFile zipFile, ZipEntry zipEntry) throws IOException {
        return PdxScriptParser.parse(IOUtil.getCharacterStream(new InputStreamReader(zipFile.getInputStream(zipEntry))));
    }

    private static boolean containsParent(ImmutableSet<String> blacklist, String name) {
        if (blacklist != null && !blacklist.isEmpty() && name != null && !name.isEmpty()) {
            int index = -1;
            while (true) {
                index = name.indexOf(PdxConstants.SLASH_CHAR, index + 1);
                if (index == -1) {
                    break;
                } else if (blacklist.contains(name.substring(0, index))) {
                    return true;
                }
            }
        }
        return false;
    }

    public File getFile() {
        return file;
    }

    public PdxScriptObject getRawData() {
        return rawData;
    }

    public ImmutableList<Pair<String, Throwable>> getErrors() {
        return errors.stream().sorted(Comparator.comparing((Pair<String, Throwable> p) -> p.getTwo().toString()).thenComparing(Pair::getOne)).collect(Collectors2.toImmutableList());
    }

    private PdxScriptObject load() {
        if (file.isDirectory()) {
            try {
                return parseFolder(file.getCanonicalPath(), file);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        } else {
            return parseZippedFile(file);
        }
    }

    private PdxScriptObject parseZippedFile(File file) {
        PdxScriptObject.Builder b = PdxScriptObject.builder();
        try {
            ZipUtil.readZipContents(file, ((zipFile, zipEntry) -> {
                if (zipEntry.isDirectory()) {
                    return;
                }
                if (blacklist.contains(zipEntry.getName()) || containsParent(blacklist, zipEntry.getName())) {
                    return;
                }
                if (!filter.accept(new File(zipFile.getName(), zipEntry.getName()))) {
                    return;
                }

                IPdxScript s;
                try {
                    s = parse(zipFile, zipEntry);
                } catch (Exception e) {
                    Throwable t = e.getCause() != null ? e.getCause() : e;
                    Throwable[] suppressed = t.getSuppressed();
                    Throwable cause = t.getCause();
                    System.out.println("Error while parsing " + new File(zipFile.getName(), zipEntry.getName()) + ": " + t + (suppressed != null && suppressed.length > 0 ? ", Supressed: " + Arrays.toString(suppressed) : EMPTY) + (cause != null ? ", Caused By: " + cause : EMPTY));
                    errors.add(Tuples.pair(zipEntry.getName(), t));
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
                        b.add(zipEntry.getName(), s);
                    }
                }
            }));
        } catch (UncheckedIOException e) {
            // TODO: revisit this exception handling
            e.printStackTrace();
        }
        return b.build(PdxRelation.EQUALS);
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
                    errors.add(Tuples.pair(path, t));
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

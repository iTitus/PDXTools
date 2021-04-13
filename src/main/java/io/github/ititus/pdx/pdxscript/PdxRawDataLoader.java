package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import io.github.ititus.pdx.util.mutable.MutableInt;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.tuple.Tuples;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

import static io.github.ititus.pdx.pdxscript.PdxConstants.EMPTY;

public final class PdxRawDataLoader {

    private final Path path;
    private final ImmutableSet<String> blacklist;
    private final IPathFilter filter;

    private final PdxScriptObject rawData;

    private MutableSet<Pair<String, Throwable>> errors;

    public PdxRawDataLoader(Path path, ImmutableSet<String> blacklist, IPathFilter filter) {
        this(path, blacklist, filter, -1, null);
    }

    public PdxRawDataLoader(Path path, ImmutableSet<String> blacklist, IPathFilter filter, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (path == null || !Files.exists(path) || blacklist == null) {
            throw new IllegalArgumentException();
        }

        this.path = path;
        this.blacklist = blacklist;
        this.filter = filter;
        this.rawData = load(path, index, progressMessageUpdater);
    }

    public Path getPath() {
        return path;
    }

    public PdxScriptObject getRawData() {
        return rawData;
    }

    public ImmutableList<Pair<String, Throwable>> getErrors() {
        return errors != null ?
                errors.stream()
                        .sorted(
                                Comparator.comparing((Pair<String, Throwable> p) -> p.getTwo().toString())
                                        .thenComparing(Pair::getOne)
                        )
                        .collect(Collectors2.toImmutableList())
                : Lists.immutable.empty();
    }

    private PdxScriptObject load(Path path, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (Files.isDirectory(path)) {
            int fileCount = progressMessageUpdater != null ? countFiles(path) : 0;
            PdxScriptObject o = parseFolder(path, path, index, new MutableInt(), fileCount, progressMessageUpdater);
            if (progressMessageUpdater != null) {
                progressMessageUpdater.updateProgressMessage(index, false, fileCount, fileCount, "Done");
            }

            return o;
        } else {
            try (FileSystem fs = IOUtil.openZip(path)) {
                Iterator<Path> rootIt = fs.getRootDirectories().iterator();
                if (!rootIt.hasNext()) {
                    throw new RuntimeException("expected at least one root dir");
                }

                Path root = rootIt.next();
                if (rootIt.hasNext()) {
                    throw new RuntimeException("expected at most one root dir");
                }

                return load(root, index, progressMessageUpdater);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private int countFiles(Path root) {
        try (Stream<Path> stream = Files.walk(root)) {
            return Math.toIntExact(
                    stream
                            .filter(Files::isRegularFile)
                            .filter(p -> isAllowed(root, p))
                            .count()
            );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private PdxScriptObject parseFolder(Path root, Path dir, int index, MutableInt progress, int fileCount,
                                        StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        PdxScriptObject.Builder b = PdxScriptObject.builder();

        try (Stream<Path> stream = Files.list(dir)) {
            stream
                    .filter(p -> isAllowed(root, p))
                    .sorted(IOUtil.ASCIIBETICAL)
                    .forEachOrdered(p -> {
                        IPdxScript s = Files.isDirectory(p) ? parseFolder(root, p, index, progress, fileCount,
                                progressMessageUpdater) : parseFile(root, p, index, progress, fileCount,
                                progressMessageUpdater);
                        if (s != null) {
                            b.add(p.getFileName().toString(), s);
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        PdxScriptObject o = b.build(PdxRelation.EQUALS);
        return o.size() > 0 ? o : null;
    }

    private IPdxScript parseFile(Path root, Path file, int index, MutableInt progress, int fileCount,
                                 StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        Path path = root.relativize(file);
        if (progressMessageUpdater != null) {
            progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), fileCount, "Loading File " + path);
        }
        IPdxScript s;
        try {
            s = PdxScriptParser.parse(file);
        } catch (Exception e) {
            Throwable t = e.getCause() != null ? e.getCause() : e;
            Throwable[] suppressed = t.getSuppressed();
            Throwable cause = t.getCause();
            System.out.println("Error while parsing " + path + ": " + t + (suppressed != null && suppressed.length > 0 ? ", Suppressed: " + Arrays.toString(suppressed) : EMPTY) + (cause != null ? ", Caused By: " + cause : EMPTY));
            if (errors == null) {
                errors = Sets.mutable.empty();
            }
            errors.add(Tuples.pair(path.toString(), t));
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

        return null;
    }

    private boolean isAllowed(Path root, Path p) {
        if (p == null) {
            return false;
        }
        boolean f = Files.isRegularFile(p);
        boolean d = Files.isDirectory(p);

        p = root.relativize(p);

        if ((!f && !d) || (f && d)) {
            return false;
        }

        return (d || filter == null || filter.test(p)) && blacklist.stream().noneMatch(p::startsWith);
    }
}

package io.github.ititus.pdx.util;

import io.github.ititus.io.IO;
import io.github.ititus.pdx.pdxscript.PdxPatch;
import io.github.ititus.pdx.pdxscript.PdxPatchDatabase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.PrimitiveIterator;

public final class IOUtil {

    @Deprecated(forRemoval = true)
    public static Path resolveRealDir(Path p) {
        return resolveRealPath(p, true);
    }

    @Deprecated(forRemoval = true)
    public static Path resolveRealFile(Path p) {
        return resolveRealPath(p, false);
    }

    @Deprecated(forRemoval = true)
    public static Path resolveRealPath(Path p, boolean isDir) {
        try {
            Files.createDirectories(isDir ? p : p.normalize().getParent());
            p = p.toRealPath();

            if (isDir && !Files.isDirectory(p)) {
                throw new IllegalStateException("expected " + p + " to be a dir");
            } else if (!isDir && !Files.isRegularFile(p)) {
                throw new IllegalStateException("expected " + p + " to be a regular file");
            }

            return p;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static PrimitiveIterator.OfInt getCharacterIterator(PdxPatchDatabase patchDatabase, Path... files) {
        return new PrimitiveIterator.OfInt() {

            int currentFile = -1;
            Reader reader = null;
            long read = 0;
            int next = -1;
            boolean hasNextCalled = false;

            @Override
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                int n = next;
                hasNextCalled = false;
                next = -1;
                return n;
            }

            private Reader openCurrent() {
                Path p = files[currentFile];
                if (patchDatabase != null) {
                    Optional<PdxPatch> patch = patchDatabase.findPatch(p);
                    if (patch.isPresent()) {
                        List<String> original;
                        try {
                            original = Files.readAllLines(p);
                        } catch (IOException e1) {
                            try {
                                original = Files.readAllLines(p, StandardCharsets.ISO_8859_1);
                            } catch (IOException e2) {
                                UncheckedIOException e = new UncheckedIOException("unable to read file", e2);
                                e.addSuppressed(e1);
                                throw e;
                            }
                        }

                        List<String> patched = patch.get().apply(original);
                        String joined = String.join("\n", patched);
                        return new StringReader(joined);
                    }
                }

                try {
                    return Files.newBufferedReader(p);
                } catch (IOException e) {
                    throw new UncheckedIOException("unable to open file", e);
                }
            }

            private Reader reopenCurrent() {
                try {
                    Reader r = Files.newBufferedReader(files[currentFile], StandardCharsets.ISO_8859_1);
                    if (r.skip(read) != read) {
                        throw new IOException("unable to skip given number of characters");
                    }

                    return r;
                } catch (IOException e) {
                    throw new UncheckedIOException("unable to reopen current file", e);
                }
            }

            private int readNext() {
                int c;
                while (true) {
                    if (reader != null) {
                        try {
                            c = reader.read();
                        } catch (IOException e1) {
                            IO.closeSilently(reader);
                            if (reader instanceof StringReader) {
                                throw new UncheckedIOException("error while reading next char", e1);
                            } else {
                                reader = reopenCurrent();
                                try {
                                    c = reader.read();
                                } catch (IOException e2) {
                                    UncheckedIOException e = new UncheckedIOException("error while reading next char after reopening", e2);
                                    e.addSuppressed(e1);
                                    throw e;
                                }
                            }
                        }

                        if (c != -1) {
                            break;
                        }

                        IO.closeSilently(reader);
                        reader = null;
                        return '\n';
                    }

                    read = 0;
                    currentFile++;
                    if (currentFile < files.length) {
                        reader = openCurrent();
                    } else {
                        reader = null;
                        return -1;
                    }
                }

                read++;
                return c;
            }

            @Override
            public boolean hasNext() {
                if (!hasNextCalled) {
                    next = readNext();
                    hasNextCalled = true;
                }

                return next != -1;
            }
        };
    }
}

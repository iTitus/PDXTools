package io.github.ititus.pdx.util.io;

import io.github.ititus.pdx.pdxscript.PdxPatch;
import io.github.ititus.pdx.pdxscript.PdxPatchDatabase;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class IOUtil {

    public static final Comparator<Path> ASCIIBETICAL = (p1, p2) -> {
        try {
            p1 = p1.toRealPath();
            p2 = p2.toRealPath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int c1 = p1.getNameCount();
        int c2 = p2.getNameCount();

        boolean sameLength = c1 == c2;
        int min = Math.min(c1, c2);

        for (int i = 0; i < min; i++) {
            String n1 = p1.getName(i).toString();
            String n2 = p2.getName(i).toString();

            if (sameLength && i == min - 1) {
                boolean d1 = Files.isDirectory(p1);
                boolean d2 = Files.isDirectory(p2);
                if (d1 != d2) {
                    return d1 ? -1 : 1;
                }
            }

            int c = n1.compareTo(n2);
            if (c != 0) {
                return c;
            }
        }

        return c1 - c2;
    };

    private IOUtil() {
    }

    public static String getExtension(Path p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return getExtension(p.getFileName().toString());
    }

    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            return fileName.substring(i + 1).toLowerCase(Locale.ROOT);
        }
        return "";
    }

    public static String getNameWithoutExtension(Path p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return getNameWithoutExtension(p.getFileName().toString());
    }

    public static String getNameWithoutExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            return fileName.substring(0, i);
        }
        return fileName;
    }

    private static void closeSilently(Closeable c) {
        if (c == null) {
            return;
        }

        try {
            c.close();
        } catch (IOException ignored) {
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
                            closeSilently(reader);
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

                        closeSilently(reader);
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

    public static FileSystem openZip(Path zip) throws IOException {
        return openZip(zip, false);
    }

    public static FileSystem openZip(Path zip, boolean create) throws IOException {
        URI uri = URI.create("jar:file:" + zip.toUri().getRawPath());
        Map<String, ?> env = Map.of("create", create);
        return FileSystems.newFileSystem(uri, env);
    }
}

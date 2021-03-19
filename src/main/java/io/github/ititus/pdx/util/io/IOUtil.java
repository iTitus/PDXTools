package io.github.ititus.pdx.util.io;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class IOUtil {

    private IOUtil() {
    }

    public static Comparator<Path> asciibetical(Path root) {
        Path normRoot = root.normalize();
        return Comparator.comparingInt((Path p) -> Files.isDirectory(p) ? 1 : 0)
                .thenComparing(p -> normRoot.relativize(p.normalize()).toString());
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

    public static PrimitiveIterator.OfInt getCharacterIterator(Path... files) {
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
                try {
                    return Files.newBufferedReader(files[currentFile]);
                } catch (IOException e) {
                    throw new UncheckedIOException("unable to open next file", e);
                }
            }

            private Reader reopenCurrent() {
                try {
                    BufferedReader r = Files.newBufferedReader(files[currentFile], StandardCharsets.ISO_8859_1);
                    if (r.skip(read) != read) {
                        throw new IOException("unable to skip given number of characters");
                    }

                    return r;
                } catch (IOException e) {
                    throw new UncheckedIOException("unable to reopen current file", e);
                }
            }

            private int readNext() {
                if (reader == null) {
                    read = 0;
                    currentFile = 0;
                    if (currentFile < files.length) {
                        reader = openCurrent();
                    } else {
                        return -1;
                    }
                }

                while (true) {
                    try {
                        if (reader.ready()) {
                            break;
                        }
                    } catch (IOException e) {
                        throw new UncheckedIOException("cannot query ready status on reader", e);
                    }

                    closeSilently(reader);

                    read = 0;
                    currentFile++;
                    if (currentFile < files.length) {
                        reader = openCurrent();
                    } else {
                        reader = null;
                        return -1;
                    }
                }

                try {
                    int c = reader.read();
                    read++;
                    return c;
                } catch (IOException e1) {
                    reader = reopenCurrent();
                    try {
                        int c = reader.read();
                        read++;
                        return c;
                    } catch (IOException e2) {
                        UncheckedIOException e = new UncheckedIOException("error while ready next char", e2);
                        e.addSuppressed(e1);
                        throw e;
                    }
                }
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

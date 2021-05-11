package io.github.ititus.pdx;

import io.github.ititus.dds.DdsFile;
import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReadADdsFile {

    private static final Path LOG_FILE = toRealPath(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds.log"), false);
    private static final Path INSTALL_DIR = toRealPath(Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris"), true);
    private static final PathFilter FILTER = new FileExtensionFilter("dds");

    private final List<String> log = new ArrayList<>();

    public static void main(String[] args) {
        new ReadADdsFile().run();
    }

    private static String pathToString(Path p) {
        return INSTALL_DIR.relativize(p).toString().replace('\\', '/');
    }

    private static Path toRealPath(Path p, boolean isDir) {
        try {
            Files.createDirectories(isDir ? p : p.normalize().getParent());
            return p.toRealPath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void run() {
        List<Path> files;
        try (Stream<Path> stream = Files.walk(INSTALL_DIR)) {
            files = stream
                    .filter(Files::isRegularFile)
                    .filter(FILTER)
                    .map(p -> toRealPath(p, false))
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        log("Found " + files.size() + " dds files");
        for (Path p : files) {
            read(p);
        }

        dumpLog();

        /*Path p = INSTALL_DIR.resolve("gfx/interface/icons/achievements/1999_ad.dds");
        read(p);*/
        /*BufferedImage img = ImageIO.read(p.toFile());
        System.out.println(img);*/
    }

    private void dumpLog() {
        try {
            Files.write(LOG_FILE, log);
            log.clear();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void read(Path p) {
        DdsFile dds = DdsFile.load(p);
        /*if ((dds.header().dwCaps() & DDSCAPS_TEXTURE) != DDSCAPS_TEXTURE) {
            System.out.println(pathToString(p) + ": dwCaps (0x" + Integer.toHexString(dds.header().dwCaps()) + ") doesnt contain DDSCAPS_TEXTURE (0x" + Integer.toHexString(DDSCAPS_TEXTURE) + ")");
        }*/
        log(pathToString(p) + ": " + dds);
    }

    private void log(String msg) {
        log.add(msg);
        // System.out.println(msg);
    }
}

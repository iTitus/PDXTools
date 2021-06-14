package io.github.ititus.pdx;

import io.github.ititus.dds.D3dFormat;
import io.github.ititus.dds.DdsFile;
import io.github.ititus.dds.DdsHeader;
import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;
import io.github.ititus.math.time.DurationFormatter;
import io.github.ititus.math.time.StopWatch;
import io.github.ititus.pdx.util.IOUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadADdsFile {

    private static final Path LOG_FILE = PathUtil.createParentsAndResolveFile(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds.log"));
    private static final Path OUT_DIR = PathUtil.createOrResolveRealDir(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds_out"));
    private static final Path INSTALL_DIR = PathUtil.resolveRealDir(Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris"));
    private static final PathFilter FILTER = new FileExtensionFilter("dds");

    private final List<String> log = new ArrayList<>();
    private final List<Path> files;

    private ReadADdsFile() {
        StopWatch s = StopWatch.createRunning();
        try (Stream<Path> stream = Files.walk(INSTALL_DIR)) {
            files = stream
                    .filter(Files::isRegularFile)
                    .filter(FILTER)
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        logWithPrint("Found " + files.size() + " dds files in " + DurationFormatter.format(s.stop()));
    }

    public static void main(String[] args) {
        new ReadADdsFile().run();
    }

    private static String pathToString(Path p) {
        return INSTALL_DIR.relativize(p).toString().replace(File.separatorChar, '/');
    }

    private void run() {
        try {
            StopWatch s = StopWatch.createRunning();
            categorizeAllDds();
            logWithPrint("Analysed all dds files in " + DurationFormatter.format(s.stop()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            StopWatch s = StopWatch.createRunning();
            convertSampleImages();
            logWithPrint("Converted some dds files in " + DurationFormatter.format(s.stop()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*try {
            StopWatch s = StopWatch.createRunning();
            convertAllImages();
            logWithPrint("Converted all dds files in " + DurationFormatter.format(s.stop()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        StopWatch s = StopWatch.createRunning();
        dumpLog();
        logWithPrint("Dumped log in " + DurationFormatter.format(s.stop()));
    }

    private void categorizeAllDds() {
        enum Type {
            FLAT, FLAT_MIPPED, CUBEMAP, CUBEMAP_MIPPED, VOLUME, VOLUME_MIPPED
        }

        Map<DdsHeader, List<Path>> headers = new LinkedHashMap<>();
        Map<D3dFormat, List<Path>> formats = new LinkedHashMap<>();
        Map<Type, List<Path>> types = new LinkedHashMap<>();
        for (Path p : files) {
            DdsFile dds;
            try {
                dds = read(p);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            if (dds.isDx10()) {
                throw new RuntimeException("dds file contains unexpected dx10 header");
            }

            headers.computeIfAbsent(dds.header(), k -> new ArrayList<>()).add(p);
            formats.computeIfAbsent(dds.d3dFormat(), k -> new ArrayList<>()).add(p);
            types.computeIfAbsent(dds.isCubemap() ? (dds.hasMipmaps() ? Type.CUBEMAP_MIPPED : Type.CUBEMAP) : dds.isVolumeTexture() ? (dds.hasMipmaps() ? Type.VOLUME_MIPPED : Type.VOLUME) : (dds.hasMipmaps() ? Type.FLAT_MIPPED : Type.FLAT), k -> new ArrayList<>()).add(p);
        }

        log("\n\n");
        log("#".repeat(120));
        log("Headers");
        log("#".repeat(120));
        log("");
        headers.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<DdsHeader, List<Path>> e) -> e.getValue().size()).reversed())
                .forEachOrdered(e -> {
                    log("");
                    log("(" + e.getValue().size() + "): " + e.getKey());
                    log(e.getValue().stream().map(ReadADdsFile::pathToString).collect(Collectors.joining(", ")));
                });

        log("\n\n");
        log("#".repeat(120));
        log("Formats");
        log("#".repeat(120));
        log("");
        formats.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<D3dFormat, List<Path>> e) -> e.getValue().size()).reversed())
                .forEachOrdered(e -> {
                    log("");
                    log("(" + e.getValue().size() + "): " + e.getKey());
                    log(e.getValue().stream().map(ReadADdsFile::pathToString).collect(Collectors.joining(", ")));
                });

        log("\n\n");
        log("#".repeat(120));
        log("Types");
        log("#".repeat(120));
        log("");
        types.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<Type, List<Path>> e) -> e.getValue().size()).reversed())
                .forEachOrdered(e -> {
                    log("");
                    log("(" + e.getValue().size() + "): " + e.getKey());
                    log(e.getValue().stream().map(ReadADdsFile::pathToString).collect(Collectors.joining(", ")));
                });
    }

    private void convertSampleImages() throws IOException {
        log("\n\nConverting sample images...");

        log("\nA8R8G8B8:");
        convertImageFlat("flags/backgrounds/diagonal_stripe");

        log("\nR8G8B8:");
        convertImageFlat("flags/backgrounds/circle");

        log("\nA1R5G5B5:");
        convertImageFlat("gfx/interface/icons/dlc/ancient_relics_big");

        log("\nA8B8G8R8:");
        convertImageFlat("gfx/interface/buttons/standard_button_200_24_dlc_overlay_animated");

        log("\nDXT5:");
        convertImageFlat("flags/human/flag_human_1");
        convertImageFlat("gfx/models/combat_items/shatter_planet_laser");
        convertImageFlat("gfx/interface/icons/districts/district_city");

        log("\nDXT3:");
        convertImageFlat("gfx/interface/fleet_view/fleet_view_upgradable_design");
        convertImageFlat("gfx/interface/frontend/weapon_type_randomized");
        convertImageFlat("gfx/interface/galacticCommunity/old/vote_freeze_resolution");
        convertImageFlat("gfx/interface/galacticCommunity/unknown_empire_support");
        convertImageFlat("gfx/interface/icons/dlc/green_underlay");
        convertImageFlat("gfx/interface/icons/dlc/silver_underlay");
        convertImageFlat("gfx/interface/ship_designer/ship_designer_item_selected");
        convertImageFlat("gfx/interface/system/icon_1");
        convertImageFlat("gfx/interface/system/icon_2");
        convertImageFlat("gfx/interface/system/icon_4");
        convertImageFlat("gfx/interface/system/icon_8");
        convertImageFlat("gfx/interface/tiles/small_tiles_dialog2");

        log("\nDXT1:");
        convertImageFlat("gfx/event_pictures/space_dragon_blue");
    }

    private void convertAllImages() throws IOException {
        for (Path file : files) {
            Path relative = INSTALL_DIR.relativize(file);
            String newName = PathUtil.getNameWithoutExtension(relative) + ".png";
            convertImage(file, OUT_DIR.resolve(relative).resolveSibling(newName));
        }
    }

    private void convertImageFlat(String path) throws IOException {
        convertImage(INSTALL_DIR.resolve(path + ".dds"), OUT_DIR.resolve(path + ".png"));
        convertImage(INSTALL_DIR.resolve(path + ".dds"), OUT_DIR.resolve(path.replace('/', '_') + ".png"));
    }

    private void convertImage(Path inPath, Path outPath) throws IOException {
        log("converting " + inPath);

        outPath = outPath.toAbsolutePath().normalize();
        Files.createDirectories(outPath.getParent());

        StopWatch s = StopWatch.createRunning();
        BufferedImage img = ImageIO.read(inPath.toFile());
        log("read: " + DurationFormatter.format(s.stop()));

        s.start();
        ImageIO.write(img, "png", outPath.toFile());
        log("write: " + DurationFormatter.format(s.stop()));
    }

    private void dumpLog() {
        try {
            Files.write(LOG_FILE, log);
            log.clear();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private DdsFile read(Path p) throws IOException {
        DdsFile dds = DdsFile.load(p);
        log(pathToString(p) + ": " + dds);
        return dds;
    }

    private void log(String msg, boolean print) {
        log.add(msg);
        if (print) {
            System.out.println(msg);
        }
    }

    private void log(String msg) {
        log(msg, false);
    }

    private void logWithPrint(String msg) {
        log(msg, true);
    }
}

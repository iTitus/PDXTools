package io.github.ititus.pdx.util;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {

    private static final int BUFFER_SIZE = 4096;

    public static void unzip(File zipFile, File extractDir) {
        if (!zipFile.exists() || !zipFile.isFile()) {
            throw new IllegalArgumentException("zipFile: " + zipFile);
        }

        if (!extractDir.exists() && !extractDir.mkdirs()) {
            throw new IllegalArgumentException();
        } else if (!extractDir.isDirectory()) {
            throw new IllegalArgumentException("extractDir: " + extractDir);
        }

        try (ZipFile f = new ZipFile(zipFile)) {
            for (Enumeration<? extends ZipEntry> entries = f.entries(); entries.hasMoreElements(); ) {
                ZipEntry e = entries.nextElement();

                File extractFile = new File(extractDir, e.getName());
                if (e.isDirectory() && !extractFile.exists()) {
                    if (!extractFile.mkdirs()) {
                        throw new IOException();
                    }
                    continue;
                }

                File parent = extractFile.getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IOException();
                }

                try (
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(extractFile));
                        InputStream is = f.getInputStream(e)
                ) {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int length;
                    while ((length = is.read(buffer)) >= 0) {
                        os.write(buffer, 0, length);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}

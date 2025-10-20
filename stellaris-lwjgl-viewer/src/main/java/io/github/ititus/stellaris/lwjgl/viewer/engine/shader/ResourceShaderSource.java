package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public record ResourceShaderSource(String resourcePath) implements ShaderSource {

    public ResourceShaderSource {
        Objects.requireNonNull(resourcePath);
    }

    @Override
    public String getShaderSource() {
        String path = this.resourcePath();
        InputStream is = ResourceShaderSource.class.getResourceAsStream(path);
        if (is != null) {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return r.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        throw new RuntimeException("could not find shader file " + path);
    }
}

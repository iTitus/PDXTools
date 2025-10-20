package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import java.util.Objects;

public record StringShaderSource(String source) implements ShaderSource {

    public StringShaderSource {
        Objects.requireNonNull(source);
    }

    @Override
    public String getShaderSource() {
        return this.source();
    }
}

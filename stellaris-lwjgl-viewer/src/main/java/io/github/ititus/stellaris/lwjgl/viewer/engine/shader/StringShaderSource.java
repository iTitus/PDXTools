package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import java.util.Objects;

public class StringShaderSource implements ShaderSource {

    private final String source;

    public StringShaderSource(String source) {
        this.source = Objects.requireNonNull(source);
    }

    @Override
    public String getShaderSource() {
        return source;
    }
}

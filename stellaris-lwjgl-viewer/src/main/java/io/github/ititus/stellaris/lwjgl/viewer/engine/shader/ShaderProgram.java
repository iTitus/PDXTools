package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import io.github.ititus.math.matrix.Mat2f;
import io.github.ititus.math.matrix.Mat3f;
import io.github.ititus.math.matrix.Mat4f;
import io.github.ititus.math.vector.Vec2f;
import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.math.vector.Vec4f;
import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;
import org.joml.Matrix4fc;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL32C.*;

public abstract class ShaderProgram extends GlObject {

    private final List<Shader> shaders;

    protected ShaderProgram(Shader... shaders) {
        this.shaders = new ArrayList<>(Arrays.asList(shaders));
    }

    public void use() {
        glUseProgram(id);
    }

    @Override
    protected int create() {
        return glCreateProgram();
    }

    @Override
    public void load() {
        link();
        findLocations();
    }

    @Override
    protected void delete() {
        freeShaders();
        glDeleteProgram(id);
    }

    protected abstract void findLocations();

    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    public int getAttributeLocation(CharSequence name) {
        return glGetAttribLocation(id, name);
    }

    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    public void setUniform(int location, float value) {
        glUniform1f(location, value);
    }

    public void setUniform(int location, Vec2f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(2);
            value.write(buffer);
            buffer.flip();
            glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Vec3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(3);
            value.write(buffer);
            buffer.flip();
            glUniform3fv(location, buffer);
        }
    }

    public void setUniform(int location, Vec4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.write(buffer);
            buffer.flip();
            glUniform4fv(location, buffer);
        }
    }

    public void setUniform(int location, Mat2f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.write(buffer);
            buffer.flip();
            glUniformMatrix2fv(location, false, buffer);
        }
    }

    public void setUniform(int location, Mat3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(9);
            value.write(buffer);
            buffer.flip();
            glUniformMatrix3fv(location, false, buffer);
        }
    }

    public void setUniform(int location, Mat4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.write(buffer);
            buffer.flip();
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    public void setUniform(int location, Matrix4fc value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    private void link() {
        for (Shader shader : shaders) {
            shader.load();
            glAttachShader(id, shader.id());
        }

        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) != GL_TRUE) {
            String infoLog = glGetProgramInfoLog(id);
            free();
            throw new RuntimeException("Shader program linking failed: " + infoLog);
        }

        freeShaders();
    }

    private void freeShaders() {
        for (Shader shader : shaders) {
            glDetachShader(id, shader.id());
            shader.free();
        }

        shaders.clear();
    }
}

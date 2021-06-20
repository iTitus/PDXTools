package io.github.ititus.stellaris.lwjgl.viewer.engine.mesh;

import io.github.ititus.math.matrix.Mat4f;
import io.github.ititus.math.vector.Vec2f;
import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.stellaris.lwjgl.viewer.engine.buffer.ArrayBuffer;
import io.github.ititus.stellaris.lwjgl.viewer.engine.buffer.ElementArrayBuffer;
import io.github.ititus.stellaris.lwjgl.viewer.engine.shader.DefaultShaderProgram;
import io.github.ititus.stellaris.lwjgl.viewer.engine.texture.Texture2d;
import io.github.ititus.stellaris.lwjgl.viewer.engine.vertex.VertexArray;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.util.List;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;

public class Mesh {

    private static final List<Vec3f> CUBE_VERTICES = List.of(
            new Vec3f(-0.5f, -0.5f, -0.5f),
            new Vec3f(0.5f, -0.5f, -0.5f),
            new Vec3f(0.5f, 0.5f, -0.5f),
            new Vec3f(-0.5f, 0.5f, -0.5f),

            new Vec3f(-0.5f, -0.5f, 0.5f),
            new Vec3f(0.5f, -0.5f, 0.5f),
            new Vec3f(0.5f, 0.5f, 0.5f),
            new Vec3f(-0.5f, 0.5f, 0.5f),

            new Vec3f(-0.5f, 0.5f, 0.5f),
            new Vec3f(-0.5f, 0.5f, -0.5f),
            new Vec3f(-0.5f, -0.5f, -0.5f),
            new Vec3f(-0.5f, -0.5f, 0.5f),

            new Vec3f(0.5f, 0.5f, 0.5f),
            new Vec3f(0.5f, 0.5f, -0.5f),
            new Vec3f(0.5f, -0.5f, -0.5f),
            new Vec3f(0.5f, -0.5f, 0.5f),

            new Vec3f(-0.5f, -0.5f, -0.5f),
            new Vec3f(0.5f, -0.5f, -0.5f),
            new Vec3f(0.5f, -0.5f, 0.5f),
            new Vec3f(-0.5f, -0.5f, 0.5f),

            new Vec3f(-0.5f, 0.5f, -0.5f),
            new Vec3f(0.5f, 0.5f, -0.5f),
            new Vec3f(0.5f, 0.5f, 0.5f),
            new Vec3f(-0.5f, 0.5f, 0.5f)
    );
    private static final List<Vec2f> CUBE_TEXCOORDS = List.of(
            new Vec2f(0.0f, 0.0f),
            new Vec2f(1.0f, 0.0f),
            new Vec2f(1.0f, 1.0f),
            new Vec2f(0.0f, 1.0f),

            new Vec2f(0.0f, 0.0f),
            new Vec2f(1.0f, 0.0f),
            new Vec2f(1.0f, 1.0f),
            new Vec2f(0.0f, 1.0f),

            new Vec2f(1.0f, 0.0f),
            new Vec2f(1.0f, 1.0f),
            new Vec2f(0.0f, 1.0f),
            new Vec2f(0.0f, 0.0f),

            new Vec2f(1.0f, 0.0f),
            new Vec2f(1.0f, 1.0f),
            new Vec2f(0.0f, 1.0f),
            new Vec2f(0.0f, 0.0f),

            new Vec2f(0.0f, 1.0f),
            new Vec2f(1.0f, 1.0f),
            new Vec2f(1.0f, 0.0f),
            new Vec2f(0.0f, 0.0f),

            new Vec2f(0.0f, 1.0f),
            new Vec2f(1.0f, 1.0f),
            new Vec2f(1.0f, 0.0f),
            new Vec2f(0.0f, 0.0f)
    );
    private static final ImmutableIntList CUBE_INDICES = IntLists.immutable.of(
            0, 1, 2, 2, 3, 0,
            4, 5, 6, 6, 7, 4,
            8, 9, 10, 10, 11, 8,
            12, 13, 14, 14, 15, 12,
            16, 17, 18, 18, 19, 16,
            20, 21, 22, 22, 23, 20
    );

    private final VertexArray vao;
    private final ArrayBuffer vbo;
    private final ElementArrayBuffer ibo;
    private final Texture2d texture;
    private final DefaultShaderProgram shader;
    private final int indexCount;

    private Mesh(VertexArray vao, ArrayBuffer vbo, ElementArrayBuffer ibo, Texture2d texture, DefaultShaderProgram shader, int indexCount) {
        this.vao = vao;
        this.vbo = vbo;
        this.ibo = ibo;
        this.texture = texture;
        this.shader = shader;
        this.indexCount = indexCount;
    }

    public static Mesh cube(Texture2d texture, DefaultShaderProgram shader) {
        return create(
                CUBE_VERTICES,
                CUBE_TEXCOORDS,
                CUBE_INDICES,
                texture,
                shader
        );
    }

    public static Mesh create(
            List<Vec3f> vertices,
            List<Vec2f> texcoords,
            ImmutableIntList indices,
            Texture2d texture,
            DefaultShaderProgram shader
    ) {
        int vertexCount = vertices.size();
        if (vertexCount == 0 || vertexCount != texcoords.size()) {
            throw new IllegalArgumentException();
        }

        int indexCount = indices.size();
        if (indexCount == 0) {
            throw new IllegalArgumentException();
        }

        VertexArray vao = new VertexArray().load();
        ArrayBuffer vbo = new ArrayBuffer().load();
        ElementArrayBuffer ibo = new ElementArrayBuffer().load();

        vao.bind();

        vbo.bind();
        float[] vertexData = new float[vertexCount * 5];
        for (int i = 0; i < vertexCount; i++) {
            Vec3f v = vertices.get(i);
            vertexData[5 * i] = v.x();
            vertexData[5 * i + 1] = v.y();
            vertexData[5 * i + 2] = v.z();

            Vec2f t = texcoords.get(i);
            vertexData[5 * i + 3] = t.x();
            vertexData[5 * i + 4] = t.y();
        }
        vbo.bufferData(vertexData, GL_STATIC_DRAW);

        ibo.bind();
        ibo.bufferData(indices.toArray(), GL_STATIC_DRAW);

        // position attribute
        vao.enableVertexAttribArray(shader.getPosLocation());
        vao.vertexAttribPointer(shader.getPosLocation(), 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        // texture coord attribute
        vao.enableVertexAttribArray(shader.getTexposLocation());
        vao.vertexAttribPointer(shader.getTexposLocation(), 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);

        return new Mesh(vao, vbo, ibo, texture, shader, indexCount);
    }

    public void render(Mat4f model) {
        shader.use();
        texture.bind();
        vao.bind();
        shader.setModel(model);
        vao.drawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
    }

    public void cleanup() {
        ibo.free();
        vbo.free();
        vao.free();
    }
}

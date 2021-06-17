package io.github.ititus.stellaris.lwjgl.viewer.engine.camera;

import io.github.ititus.math.matrix.Mat4f;
import io.github.ititus.math.vector.Vec3f;

public class Camera {

    private Vec3f pos;
    private Vec3f dir;
    private Vec3f up;

    public Camera() {
        pos = new Vec3f(0, 0, 4);
        dir = new Vec3f(0, 0, -1);
        up = new Vec3f(0, 1, 0);
    }

    public Mat4f getViewMatrix() {
        return Mat4f.lookAt(pos, getTarget(), up);
    }

    public Vec3f getTarget() {
        return pos.add(dir);
    }

    public Vec3f getPos() {
        return pos;
    }

    public Vec3f getDir() {
        return dir;
    }

    public Vec3f getUp() {
        return up;
    }
}

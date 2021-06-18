package io.github.ititus.stellaris.lwjgl.viewer.engine.camera;

import io.github.ititus.math.matrix.Mat4f;
import io.github.ititus.math.vector.Vec3f;

public class Camera {

    private Vec3f pos;
    private Vec3f speed;
    private Vec3f dir;
    private Vec3f up;

    public Camera() {
        this.pos = new Vec3f(0, 0, 0);
        this.speed = new Vec3f(0, 0, 0);
        this.dir = new Vec3f(1, 0, 0);
        this.up = new Vec3f(0, 1, 0);
    }

    public Mat4f getViewMatrix() {
        return getViewMatrix(0);
    }

    public Mat4f getViewMatrix(float delta) {
        return Mat4f.lookAt(getPos(delta), getTarget(delta), up);
    }

    public Vec3f getTarget() {
        return pos.add(dir);
    }

    public Vec3f getTarget(float delta) {
        return getPos(delta).add(dir);
    }

    public Vec3f getPos() {
        return pos;
    }

    public void setPos(Vec3f pos) {
        this.pos = pos;
    }

    public Vec3f getPos(float delta) {
        return pos.add(speed.multiply(delta));
    }

    public Vec3f getSpeed() {
        return speed;
    }

    public void setSpeed(Vec3f speed) {
        this.speed = speed;
    }

    public Vec3f getDir() {
        return dir;
    }

    public void setDir(Vec3f dir) {
        this.dir = dir;
    }

    public Vec3f getUp() {
        return up;
    }

    public void setUp(Vec3f up) {
        this.up = up;
    }
}

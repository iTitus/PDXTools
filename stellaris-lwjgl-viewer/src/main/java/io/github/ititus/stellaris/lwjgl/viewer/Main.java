package io.github.ititus.stellaris.lwjgl.viewer;

import io.github.ititus.math.matrix.Mat4f;
import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.stellaris.lwjgl.viewer.engine.Game;
import io.github.ititus.stellaris.lwjgl.viewer.engine.GameEngine;
import io.github.ititus.stellaris.lwjgl.viewer.engine.camera.Camera;
import io.github.ititus.stellaris.lwjgl.viewer.engine.mesh.Mesh;
import io.github.ititus.stellaris.lwjgl.viewer.engine.shader.DefaultShaderProgram;
import io.github.ititus.stellaris.lwjgl.viewer.engine.texture.FileImageSource;
import io.github.ititus.stellaris.lwjgl.viewer.engine.texture.Texture;
import io.github.ititus.stellaris.lwjgl.viewer.engine.texture.Texture2d;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL32C.*;

public class Main implements Game {

    private static final double UPS = 60;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final double MOUSE_SENSITIVITY = 0.075;
    private static final float CAMERA_SPEED = 0.05f;

    // world space positions of our cubes
    private static final Vec3f[] CUBE_POSITIONS = {
            new Vec3f(0.0f, 0.0f, 0.0f),
            new Vec3f(2.0f, 5.0f, -15.0f),
            new Vec3f(-1.5f, -2.2f, -2.5f),
            new Vec3f(-3.8f, -2.0f, -12.3f),
            new Vec3f(2.4f, -0.4f, -3.5f),
            new Vec3f(-1.7f, 3.0f, -7.5f),
            new Vec3f(1.3f, -2.0f, -2.5f),
            new Vec3f(1.5f, 2.0f, -2.5f),
            new Vec3f(1.5f, 0.2f, -1.5f),
            new Vec3f(-1.3f, 1.0f, -1.5f)
    };

    private GameEngine gameEngine;
    private Camera camera;
    private DefaultShaderProgram shader;
    private Texture2d texture;
    private Mesh mesh;

    private boolean firstMouse = true;
    private double lastMouseX;
    private double lastMouseY;

    private double yaw;
    private double pitch;

    public static void main(String[] args) {
        new GameEngine(UPS, WIDTH, HEIGHT, "Hello World!", new Main()).run();
    }

    @Override
    public void init(GameEngine gameEngine) {
        this.gameEngine = gameEngine;

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        camera = new Camera();
        shader = new DefaultShaderProgram().load();

        // tell opengl for each sampler to which texture unit it belongs to (only has to be done once)
        shader.use();
        shader.setTex(0);

        // load and create a texture
        texture = new Texture2d().load();
        texture.bind();

        // set the texture wrapping parameters
        texture.parameter(GL_TEXTURE_WRAP_S, GL_REPEAT);
        texture.parameter(GL_TEXTURE_WRAP_T, GL_REPEAT);
        // set texture filtering parameters
        texture.parameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        texture.parameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // load image, create texture and generate mipmaps
        texture.texImage2d(0, 0, new FileImageSource("/textures/container.jpg"));
        texture.generateMipmap();

        mesh = Mesh.cube(texture, shader);
    }

    @Override
    public void processInput() {
        Vec3f cameraSpeed = new Vec3f(0.0f, 0.0f, 0.0f);

        Vec3f up = camera.getUp().normalize();
        Vec3f projectedFront = camera.getDir().subtract(up.multiply(camera.getDir().dot(up))).normalize();
        Vec3f projectedLeft = up.cross(projectedFront).normalize();

        if (gameEngine.getWindow().isKeyPressed(GLFW_KEY_W)) {
            cameraSpeed = cameraSpeed.add(projectedFront);
        }
        if (gameEngine.getWindow().isKeyPressed(GLFW_KEY_S)) {
            cameraSpeed = cameraSpeed.add(projectedFront.negate());
        }
        if (gameEngine.getWindow().isKeyPressed(GLFW_KEY_A)) {
            cameraSpeed = cameraSpeed.add(projectedLeft);
        }
        if (gameEngine.getWindow().isKeyPressed(GLFW_KEY_D)) {
            cameraSpeed = cameraSpeed.add(projectedLeft.negate());
        }
        if (gameEngine.getWindow().isKeyPressed(GLFW_KEY_SPACE)) {
            cameraSpeed = cameraSpeed.add(up);
        }
        if (gameEngine.getWindow().isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            cameraSpeed = cameraSpeed.add(up.negate());
        }

        if (cameraSpeed.lengthSquared() > 1e-3f) {
            cameraSpeed = cameraSpeed.normalize().multiply(CAMERA_SPEED);
            camera.setSpeed(cameraSpeed);
        } else {
            camera.setSpeed(new Vec3f(0.0f, 0.0f, 0.0f));
        }
    }

    @Override
    public void update() {
        camera.setPos(camera.getPos().add(camera.getSpeed()));
    }

    @Override
    public void render(float delta) {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  // clear the framebuffer

        // bind textures on corresponding texture units
        Texture.activeTexture(0);

        // activate shader
        shader.use();

        // pass projection matrix to shader (note that in this case it could change every frame)
        Mat4f projection = Mat4f.perspective((float) Math.toRadians(45), (float) gameEngine.getWindow().getWidth() / gameEngine.getWindow().getHeight(), 0.1f, 100.0f);
        shader.setProjection(projection);

        // camera/view transformation
        Mat4f view = camera.getViewMatrix(delta);
        shader.setView(view);

        // render boxes
        for (int i = 0; i < CUBE_POSITIONS.length; i++) {
            // calculate the model matrix for each object and pass it to shader before drawing
            Mat4f model = Mat4f.identity();
            model = model.multiply(Mat4f.translate(CUBE_POSITIONS[i]));
            model = model.multiply(Mat4f.rotate(new Vec3f(1.0f, 0.3f, 0.5f), (float) Math.toRadians(20.0f * i)));
            model = model.multiply(Mat4f.scale(i >= 5 ? 1.0f + 0.2f * (i - 4) : 1.0f / (1.0f + 0.2f * (5 - i))));

            mesh.render(model);
        }

        gameEngine.getWindow().swap();
    }

    @Override
    public void cleanup() {
        texture.free();
        mesh.cleanup();
        shader.free();
    }

    @Override
    public void onKey(int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            gameEngine.getWindow().setShouldClose(true);
        }
    }

    @Override
    public void onMouseMove(double xpos, double ypos) {
        if (firstMouse) {
            lastMouseX = xpos;
            lastMouseY = ypos;
            firstMouse = false;
        }

        double offsetX = xpos - lastMouseX;
        double offsetY = lastMouseY - ypos; // reversed since y-coordinates go from bottom to top
        lastMouseX = xpos;
        lastMouseY = ypos;

        offsetX *= MOUSE_SENSITIVITY;
        offsetY *= MOUSE_SENSITIVITY;

        yaw += offsetX;
        pitch += offsetY;

        // make sure that when pitch is out of bounds, screen doesn't get flipped
        if (pitch > 89.0) {
            pitch = 89.0;
        } else if (pitch < -89.0) {
            pitch = -89.0;
        }

        Vec3f front = new Vec3f(
                (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
                (float) Math.sin(Math.toRadians(pitch)),
                (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))
        );
        camera.setDir(front.normalize());
    }

    @Override
    public void onScroll(double xoffset, double yoffset) {
    }
}

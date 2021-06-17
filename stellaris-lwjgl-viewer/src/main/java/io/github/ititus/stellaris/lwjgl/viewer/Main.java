package io.github.ititus.stellaris.lwjgl.viewer;

import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.stellaris.lwjgl.viewer.engine.camera.Camera;
import io.github.ititus.stellaris.lwjgl.viewer.engine.shader.DefaultShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL32C.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // The window handle
    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        // glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        // glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        glfwSetFramebufferSizeCallback(window, this::onResize);
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, this::onKey);
        glfwSetCursorPosCallback(window, this::onMouseMove);
        glfwSetScrollCallback(window, this::onScroll);

        // capture mouse
        // glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void onResize(long window, int width, int height) {
        System.out.println("onResize: width=" + width + " height=" + height);
        glViewport(0, 0, width, height);
    }

    private void onKey(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        }
    }

    private void onMouseMove(long window, double xPos, double yPos) {
    }

    private void onScroll(long window, double xOffset, double yOffset) {
    }

    private void loop() {
        Camera camera = new Camera();

        DefaultShaderProgram s = new DefaultShaderProgram();
        s.load();

        float[] vertices = {
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,

                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

                -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f
        };
        // world space positions of our cubes
        Vec3f[] cubePositions = {
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

        int vao;
        int vbo;
        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // position attribute
        glEnableVertexAttribArray(s.getPosLocation());
        glVertexAttribPointer(s.getPosLocation(), 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        // texture coord attribute
        glEnableVertexAttribArray(s.getTexposLocation());
        glVertexAttribPointer(s.getTexposLocation(), 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);

        // load and create a texture
        int texture;
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        // set the texture wrapping parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // set texture filtering parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        // load image, create texture and generate mipmaps

        BufferedImage img;
        try (InputStream is = Main.class.getResourceAsStream("/textures/container.jpg")) {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        ByteBuffer data = getData(img);
        // glPixelStorei(GL_PACK_ALIGNMENT, 1);
        // glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, img.getWidth(), img.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);

        // tell opengl for each sampler to which texture unit it belongs to (only has to be done once)
        s.use();
        s.setTex(0);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  // clear the framebuffer

            // bind textures on corresponding texture units
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture);

            // activate shader
            s.use();

            // pass projection matrix to shader (note that in this case it could change every frame)
            // Mat4f projection = Mat4f.perspective((float) Math.toRadians(45), (float) WIDTH / HEIGHT, 0.1f, 100.0f);
            // s.setProjection(projection);
            Matrix4f p = new Matrix4f();
            p.perspective((float) Math.toRadians(45), (float) WIDTH / HEIGHT, 0.1f, 100.0f);
            s.setUniform(s.getProjectionLocation(), p);

            // camera/view transformation
            // Mat4f view = camera.getViewMatrix();
            // s.setView(view);
            Matrix4f v = new Matrix4f();
            v.lookAt(camera.getPos().x(), camera.getPos().y(), camera.getPos().z(), camera.getTarget().x(), camera.getTarget().y(), camera.getTarget().z(), camera.getUp().x(), camera.getUp().y(), camera.getUp().z());
            s.setUniform(s.getViewLocation(), v);

            // render boxes
            glBindVertexArray(vao);
            for (int i = 0; i < 10; i++) {
                // calculate the model matrix for each object and pass it to shader before drawing
                // Mat4f model = Mat4f.identity();
                // model = model.multiply(Mat4f.translate(cubePositions[i]));
                // model = model.multiply(Mat4f.rotate(new Vec3f(1.0f, 0.3f, 0.5f), (float) Math.toRadians(20.0f * i)));
                // s.setModel(model);

                Matrix4f m = new Matrix4f();
                m.translate(cubePositions[i].x(), cubePositions[i].y(), cubePositions[i].z());
                m.rotate((float) Math.toRadians(20.0f * i), new Vector3f(1.0f, 0.3f, 0.5f));
                s.setUniform(s.getModelLocation(), m);

                glDrawArrays(GL_TRIANGLES, 0, 36);
            }

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
    }

    private ByteBuffer getData(BufferedImage img) {
        ByteBuffer bb = ByteBuffer.allocateDirect(img.getWidth() * img.getHeight() * 3);
        for (int y = img.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < img.getHeight(); x++) {
                int argb = img.getRGB(x, y);
                bb.put((byte) ((argb >> 16) & 0xff));
                bb.put((byte) ((argb >> 8) & 0xff));
                bb.put((byte) (argb & 0xff));
            }
        }

        bb.flip();
        return bb;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

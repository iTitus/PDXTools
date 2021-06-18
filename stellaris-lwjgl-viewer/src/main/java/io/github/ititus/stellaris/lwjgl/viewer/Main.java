package io.github.ititus.stellaris.lwjgl.viewer;

import io.github.ititus.math.matrix.Mat4f;
import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.stellaris.lwjgl.viewer.engine.buffer.ArrayBuffer;
import io.github.ititus.stellaris.lwjgl.viewer.engine.buffer.ElementArrayBuffer;
import io.github.ititus.stellaris.lwjgl.viewer.engine.camera.Camera;
import io.github.ititus.stellaris.lwjgl.viewer.engine.shader.DefaultShaderProgram;
import io.github.ititus.stellaris.lwjgl.viewer.engine.texture.FileImageSource;
import io.github.ititus.stellaris.lwjgl.viewer.engine.texture.Texture;
import io.github.ititus.stellaris.lwjgl.viewer.engine.texture.Texture2d;
import io.github.ititus.stellaris.lwjgl.viewer.engine.vertex.VertexArray;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.ARBDebugOutput;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.KHRDebug;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.Objects;

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

    public static void main(String[] args) {
        new Main().run();
    }

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
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
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
            GLFWVidMode vidmode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()), "no video mode");

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
        GLCapabilities caps = GL.createCapabilities();

        System.out.println("OpenGL:");
        System.out.println("  Version: " + glGetString(GL_VERSION));
        System.out.println("  Renderer: " + glGetString(GL_RENDERER));
        System.out.println("  Vendor: " + glGetString(GL_VENDOR));
        System.out.println("  Extensions: " + glGetString(GL_EXTENSIONS));

        if (caps.GL_KHR_debug) {
            System.out.println("Debug Logging supported: KHR_debug");
            glEnable(KHRDebug.GL_DEBUG_OUTPUT);
            glEnable(KHRDebug.GL_DEBUG_OUTPUT_SYNCHRONOUS);
            KHRDebug.glDebugMessageCallback(this::debugMessage, 0);
        } else if (caps.GL_ARB_debug_output) {
            System.out.println("Debug Logging supported: ARB_debug_output");
            glEnable(ARBDebugOutput.GL_DEBUG_OUTPUT_SYNCHRONOUS_ARB);
            ARBDebugOutput.glDebugMessageCallbackARB(this::debugMessage, 0);
        } else {
            System.out.println("Debug Logging not supported");
        }

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void debugMessage(int source, int type, int id, int severity, int length, long message, long userParam) {
        String strSource = switch (source) {
            case KHRDebug.GL_DEBUG_SOURCE_API -> "API";
            case KHRDebug.GL_DEBUG_SOURCE_WINDOW_SYSTEM -> "Window System";
            case KHRDebug.GL_DEBUG_SOURCE_SHADER_COMPILER -> "Shader Compiler";
            case KHRDebug.GL_DEBUG_SOURCE_THIRD_PARTY -> "Third Party";
            case KHRDebug.GL_DEBUG_SOURCE_APPLICATION -> "Application";
            case KHRDebug.GL_DEBUG_SOURCE_OTHER -> "Other";
            default -> "Unknown";
        };
        String strType = switch (type) {
            case KHRDebug.GL_DEBUG_TYPE_ERROR -> "Error";
            case KHRDebug.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR -> "Deprecated Behaviour";
            case KHRDebug.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR -> "Undefined Behaviour";
            case KHRDebug.GL_DEBUG_TYPE_PORTABILITY -> "Portability";
            case KHRDebug.GL_DEBUG_TYPE_PERFORMANCE -> "Performance";
            case KHRDebug.GL_DEBUG_TYPE_OTHER -> "Other";
            case KHRDebug.GL_DEBUG_TYPE_MARKER -> "Marker";
            case KHRDebug.GL_DEBUG_TYPE_PUSH_GROUP -> "Push Group";
            case KHRDebug.GL_DEBUG_TYPE_POP_GROUP -> "Pop Group";
            default -> "Unknown";
        };
        String strSeverity = switch (severity) {
            case KHRDebug.GL_DEBUG_SEVERITY_HIGH -> "High";
            case KHRDebug.GL_DEBUG_SEVERITY_MEDIUM -> "Medium";
            case KHRDebug.GL_DEBUG_SEVERITY_LOW -> "Low";
            case KHRDebug.GL_DEBUG_SEVERITY_NOTIFICATION -> "Notification";
            default -> "Unknown";
        };
        String strMessage = MemoryUtil.memUTF8Safe(message, length);
        System.out.println("debugMessage(" + id + "): src=" + strSource + " type=" + strType + " sev=" + strSeverity + " msg=" + strMessage);
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

        DefaultShaderProgram s = new DefaultShaderProgram().load();

        /*float[] vertices = {
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
        };*/
        float[] vertices = {
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,

                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,

                -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f
        };
        short[] indices = {
                0, 1, 2, 2, 3, 0,
                4, 5, 6, 6, 7, 4,
                8, 9, 10, 10, 11, 8,
                12, 13, 14, 14, 15, 12,
                16, 17, 18, 18, 19, 16,
                20, 21, 22, 22, 23, 20
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

        VertexArray vao = new VertexArray().load();
        ArrayBuffer vbo = new ArrayBuffer().load();
        ElementArrayBuffer ibo = new ElementArrayBuffer().load();

        vao.bind();

        vbo.bind();
        vbo.bufferData(vertices, GL_STATIC_DRAW);

        ibo.bind();
        ibo.bufferData(indices, GL_STATIC_DRAW);

        // position attribute
        vao.enableVertexAttribArray(s.getPosLocation());
        vao.vertexAttribPointer(s.getPosLocation(), 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        // texture coord attribute
        vao.enableVertexAttribArray(s.getTexposLocation());
        vao.vertexAttribPointer(s.getTexposLocation(), 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);

        // load and create a texture
        Texture2d texture = new Texture2d().load();
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

        // tell opengl for each sampler to which texture unit it belongs to (only has to be done once)
        s.use();
        s.setTex(0);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  // clear the framebuffer

            // bind textures on corresponding texture units
            Texture.activeTexture(0);
            texture.bind();

            // activate shader
            s.use();

            // pass projection matrix to shader (note that in this case it could change every frame)
            Mat4f projection = Mat4f.perspective((float) Math.toRadians(45), (float) WIDTH / HEIGHT, 0.1f, 100.0f);
            s.setProjection(projection);

            // camera/view transformation
            Mat4f view = camera.getViewMatrix();
            s.setView(view);

            // render boxes
            vao.bind();
            for (int i = 0; i < 10; i++) {
                // calculate the model matrix for each object and pass it to shader before drawing
                Mat4f model = Mat4f.identity();
                model = model.multiply(Mat4f.translate(cubePositions[i]));
                model = model.multiply(Mat4f.rotate(new Vec3f(1.0f, 0.3f, 0.5f), (float) Math.toRadians(20.0f * i)));
                // model = model.multiply(Mat4f.scale(i >= 5 ? 1.0f + 0.2f * (i - 4) : 1.0f / (1.0f + 0.2f * (5 - i))));
                s.setModel(model);

                // vao.drawArrays(GL_TRIANGLES, 0, vertices.length / (3 + 2));
                vao.drawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_SHORT, 0);
            }

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        vao.free();
        vbo.free();
        texture.free();
        s.free();
    }
}

package io.github.ititus.stellaris.lwjgl.viewer.engine.window;

import io.github.ititus.stellaris.lwjgl.viewer.engine.GameEngine;
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

public class Window {

    private final int initialWidth;
    private final int initialHeight;
    private final String title;

    private GameEngine gameEngine;
    private long window;
    private int width;
    private int height;

    public Window(int initialWidth, int initialHeight, String title) {
        this.initialWidth = this.width = initialWidth;
        this.initialHeight = this.height = initialHeight;
        this.title = title;
    }

    public void init(GameEngine gameEngine) {
        this.gameEngine = gameEngine;

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

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
        window = glfwCreateWindow(initialWidth, initialHeight, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

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

        glfwSetFramebufferSizeCallback(window, this::onResize);
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> this.gameEngine.onKey(key, scancode, action, mods));
        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> this.gameEngine.onMouseMove(xpos, ypos));
        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> this.gameEngine.onScroll(xoffset, yoffset));

        // capture mouse
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

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

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    public void swap() {
        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    public void cleanup() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void setShouldClose(boolean value) {
        glfwSetWindowShouldClose(window, value);
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
        this.width = width;
        this.height = height;
        glViewport(0, 0, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isKeyPressed(int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }
}

module io.github.ititus.stellaris.lwjgl.viewer {
    requires java.desktop;
    requires io.github.ititus.commons;
    requires io.github.ititus.dds;
    requires io.github.ititus.pdx;
    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;
    requires org.lwjgl;
    requires org.lwjgl.natives;
    requires org.lwjgl.assimp;
    requires org.lwjgl.assimp.natives;
    requires org.lwjgl.glfw;
    requires org.lwjgl.glfw.natives;
    requires org.lwjgl.openal;
    requires org.lwjgl.openal.natives;
    requires org.lwjgl.opengl;
    requires org.lwjgl.opengl.natives;
    requires org.lwjgl.stb;
    requires org.lwjgl.stb.natives;
    requires org.joml;

    exports io.github.ititus.stellaris.lwjgl.viewer;
    exports io.github.ititus.stellaris.lwjgl.viewer.engine;
    exports io.github.ititus.stellaris.lwjgl.viewer.engine.buffer;
    exports io.github.ititus.stellaris.lwjgl.viewer.engine.camera;
    exports io.github.ititus.stellaris.lwjgl.viewer.engine.shader;
    exports io.github.ititus.stellaris.lwjgl.viewer.engine.texture;
    exports io.github.ititus.stellaris.lwjgl.viewer.engine.vertex;
    exports io.github.ititus.stellaris.lwjgl.viewer.engine.window;
}

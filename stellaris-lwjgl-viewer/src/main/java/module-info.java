module io.github.ititus.stellaris.lwjgl.viewer {
    requires java.desktop;
    requires io.github.ititus.commons;
    requires io.github.ititus.dds;
    requires io.github.ititus.pdx;
    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;

    requires org.lwjgl;
    requires org.lwjgl.natives;
    requires org.lwjgl.glfw;
    requires org.lwjgl.glfw.natives;
    requires org.lwjgl.opengl;
    requires org.lwjgl.opengl.natives;
    requires org.joml;

    exports io.github.ititus.stellaris.lwjgl.viewer;
}

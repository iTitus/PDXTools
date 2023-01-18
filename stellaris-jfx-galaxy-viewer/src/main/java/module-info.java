module io.github.ititus.stellaris.viewer {
    requires java.desktop;
    requires io.github.ititus.commons;
    requires io.github.ititus.ddsfx;
    requires io.github.ititus.pdx;
    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;

    exports io.github.ititus.stellaris.viewer;
    exports io.github.ititus.stellaris.viewer.view;
}

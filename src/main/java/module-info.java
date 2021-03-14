module io.github.ititus.pdx {
    requires java.base;
    requires java.desktop;

    requires ititus.commons;

    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;

    exports io.github.ititus.pdx;
}
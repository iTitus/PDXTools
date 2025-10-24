module io.github.ititus.stellaris.viewer {
    requires java.desktop;
    requires io.github.ititus.commons;
    requires io.github.ititus.ddsiio;
    requires io.github.ititus.pdx;
    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires io.github.ititus.valve_tools.steam_api;

    exports io.github.ititus.stellaris.viewer;
    exports io.github.ititus.stellaris.viewer.view;
}

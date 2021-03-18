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
    exports io.github.ititus.pdx.pdxasset;
    exports io.github.ititus.pdx.pdxlocalisation;
    exports io.github.ititus.pdx.pdxscript;
    exports io.github.ititus.pdx.stellaris;
    exports io.github.ititus.pdx.stellaris.game;
    exports io.github.ititus.pdx.stellaris.game.common;
    exports io.github.ititus.pdx.stellaris.game.dlc;
    exports io.github.ititus.pdx.stellaris.user;
    exports io.github.ititus.pdx.stellaris.user.mod;
    exports io.github.ititus.pdx.stellaris.user.save;
    exports io.github.ititus.pdx.stellaris.view;
    exports io.github.ititus.pdx.util;
    exports io.github.ititus.pdx.util.io;
    exports io.github.ititus.pdx.util.mutable;
    exports io.github.ititus.pdx.util.collection;
}
module io.github.ititus.pdx {
    requires java.desktop;
    requires jdk.zipfs;
    requires io.github.ititus.commons;
    requires io.github.ititus.ddsiio;
    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;
    requires io.github.javadiffutils;

    exports io.github.ititus.pdx;
    exports io.github.ititus.pdx.pdxasset;
    exports io.github.ititus.pdx.pdxlocalisation;
    exports io.github.ititus.pdx.pdxscript;
    exports io.github.ititus.pdx.shared;
    exports io.github.ititus.pdx.shared.effect;
    exports io.github.ititus.pdx.shared.localisation;
    exports io.github.ititus.pdx.shared.scope;
    exports io.github.ititus.pdx.shared.trigger;
    exports io.github.ititus.pdx.stellaris.game;
    exports io.github.ititus.pdx.stellaris.game.common;
    exports io.github.ititus.pdx.stellaris.game.common.deposits;
    exports io.github.ititus.pdx.stellaris.game.common.planet_classes;
    exports io.github.ititus.pdx.stellaris.game.common.species_classes;
    exports io.github.ititus.pdx.stellaris.game.common.technology;
    exports io.github.ititus.pdx.stellaris.game.common.technology.category;
    exports io.github.ititus.pdx.stellaris.game.common.technology.tier;
    exports io.github.ititus.pdx.stellaris.game.dlc;
    exports io.github.ititus.pdx.stellaris.game.gfx;
    exports io.github.ititus.pdx.stellaris.game.scope;
    exports io.github.ititus.pdx.stellaris.game.scope.interfaces;
    exports io.github.ititus.pdx.stellaris.game.trigger;
    exports io.github.ititus.pdx.stellaris.shared;
    exports io.github.ititus.pdx.stellaris.user;
    exports io.github.ititus.pdx.stellaris.user.mod;
    exports io.github.ititus.pdx.stellaris.user.save;
    exports io.github.ititus.pdx.util;
}

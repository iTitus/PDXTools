package io.github.ititus.stellaris.lwjgl.viewer.engine;

public interface Game {

    void init(GameEngine gameEngine);

    void processInput();

    void update();

    void render(float delta);

    void cleanup();

    void onKey(int key, int scancode, int action, int mods);

    void onMouseMove(double xpos, double ypos);

    void onScroll(double xoffset, double yoffset);
}

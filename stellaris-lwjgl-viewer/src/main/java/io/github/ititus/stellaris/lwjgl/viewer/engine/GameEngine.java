package io.github.ititus.stellaris.lwjgl.viewer.engine;

import io.github.ititus.stellaris.lwjgl.viewer.engine.window.Window;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class GameEngine {

    private final double ups;
    private final Window window;
    private final Game game;

    public GameEngine(double ups, int initialWidth, int initialHeight, String title, Game game) {
        this.ups = ups;
        this.window = new Window(initialWidth, initialHeight, title);
        this.game = game;
    }

    public void run() {
        window.init(this);
        game.init(this);

        double updatesPerSecond = 1 / ups;
        double prev = glfwGetTime();
        double acc = 0;
        while (!window.shouldClose()) {
            double curr = glfwGetTime();
            double elapsed = curr - prev;
            prev = curr;
            acc += elapsed;

            game.processInput();

            while (acc >= updatesPerSecond) {
                game.update();
                acc -= updatesPerSecond;
            }

            game.render((float) (acc / updatesPerSecond));
        }

        game.cleanup();
        window.cleanup();
    }

    public void onKey(int key, int scancode, int action, int mods) {
        game.onKey(key, scancode, action, mods);
    }

    public void onMouseMove(double xpos, double ypos) {
        game.onMouseMove(xpos, ypos);
    }

    public void onScroll(double xoffset, double yoffset) {
        game.onScroll(xoffset, yoffset);
    }

    public Window getWindow() {
        return window;
    }
}

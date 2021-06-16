package io.github.ititus.stellaris.lwjgl.viewer.engine;

public abstract class GlObject {

    protected final int id;
    private boolean valid;

    protected GlObject() {
        this.id = create();
        this.valid = id != 0;
        if (!isValid()) {
            throw new IllegalStateException("gl object is invalid");
        }
    }

    protected abstract int create();

    public abstract void load();

    protected abstract void delete();

    public final void free() {
        delete();
        valid = false;
    }

    public int id() {
        return id;
    }

    public boolean isValid() {
        return valid;
    }
}

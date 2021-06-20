package io.github.ititus.stellaris.lwjgl.viewer.engine;

public abstract class GlObject<T extends GlObject<T>> {

    protected int id;

    protected GlObject() {
    }

    protected abstract int create();

    protected abstract void init();

    @SuppressWarnings("unchecked")
    public final T load() {
        this.id = create();
        if (!isValid()) {
            throw new IllegalStateException("gl object is invalid");
        }

        init();
        return (T) this;
    }

    protected abstract void delete();

    public final void free() {
        delete();
        id = 0;
    }

    public int id() {
        return id;
    }

    public boolean isValid() {
        return id != 0;
    }
}

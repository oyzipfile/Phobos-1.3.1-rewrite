/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.util.misc;

public class MutableWrapper<T> {
    protected T value;

    public MutableWrapper() {
        this(null);
    }

    public MutableWrapper(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}


/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.tweaker.launch.arguments;

import me.earth.earthhack.tweaker.launch.Argument;

public abstract class AbstractArgument<T>
implements Argument<T> {
    protected T value;

    public AbstractArgument(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return this.value;
    }
}


/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.gui.visibility;

public interface VisibilitySupplier {
    public boolean isVisible();

    default public VisibilitySupplier compose(VisibilitySupplier other) {
        return this;
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joml.Vector3f
 */
package me.earth.earthhack.impl.util.render.entity;

import me.earth.earthhack.impl.util.render.entity.IRenderable;
import org.joml.Vector3f;

public abstract class QuadRenderer
implements IRenderable {
    private Vector3f[] vectors = new Vector3f[4];

    public QuadRenderer(Vector3f vec, Vector3f vec1, Vector3f vec2, Vector3f vec3) {
        this.vectors[0] = vec;
        this.vectors[1] = vec1;
        this.vectors[2] = vec2;
        this.vectors[3] = vec3;
    }
}


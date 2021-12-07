/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joml.Vector3f
 */
package me.earth.earthhack.impl.util.render.entity;

import java.util.ArrayList;
import java.util.List;
import me.earth.earthhack.impl.util.render.entity.BoxRenderer;
import me.earth.earthhack.impl.util.render.entity.PlainQuad;
import org.joml.Vector3f;

public class FaceBoxRenderer
extends BoxRenderer {
    private List<PlainQuad> quads = new ArrayList<PlainQuad>();

    public FaceBoxRenderer(Vector3f min, Vector3f max) {
        super(min, max);
    }

    @Override
    void render(float scale) {
    }

    @Override
    void setup() {
    }
}


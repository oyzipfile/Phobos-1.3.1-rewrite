/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiTextureType
 */
package me.earth.earthhack.impl.util.render.model;

import jassimp.AiTextureType;
import me.earth.earthhack.impl.util.render.image.EfficientTexture;

public class Texture {
    private final int id;
    private final EfficientTexture texture;
    private final int width;
    private final int height;
    private final AiTextureType type;

    public Texture(int id, AiTextureType type, EfficientTexture texture, int width, int height) {
        this.id = id;
        this.type = type;
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return this.id;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public AiTextureType getType() {
        return this.type;
    }

    public EfficientTexture getTexture() {
        return this.texture;
    }
}


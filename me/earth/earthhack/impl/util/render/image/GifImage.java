/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 */
package me.earth.earthhack.impl.util.render.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.util.render.image.EfficientTexture;
import me.earth.earthhack.impl.util.render.image.ImageUtil;
import org.lwjgl.Sys;

public class GifImage
implements Globals,
Nameable {
    private String name;
    private List<BufferedImage> frames = new LinkedList<BufferedImage>();
    private List<EfficientTexture> textures = new LinkedList<EfficientTexture>();
    private int offset;
    private int delay;
    private boolean firstUpdate;
    private long lastUpdate;
    private long timeLeft;

    public GifImage(List<BufferedImage> images, int delay, String name) {
        this.name = name;
        for (BufferedImage image : images) {
            this.frames.add(ImageUtil.createFlipped(image));
        }
        this.offset = 0;
        this.delay = delay;
        this.firstUpdate = true;
        for (BufferedImage image : this.frames) {
            try {
                String generatedString = UUID.randomUUID().toString().split("-")[0];
                this.textures.add(ImageUtil.cacheBufferedImage(image, "gif", generatedString));
            }
            catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        this.reset();
    }

    public GifImage(String name, int delay, List<EfficientTexture> textures) {
        this.name = name;
        this.delay = delay;
        this.offset = 0;
        this.textures = textures;
        this.firstUpdate = true;
        this.reset();
    }

    public void reset() {
        this.firstUpdate = true;
        this.timeLeft = this.delay;
        this.offset = 0;
    }

    public BufferedImage getBufferedImage() {
        if (this.frames.size() == 0) {
            return null;
        }
        long now = this.getTime();
        long delta = now - this.lastUpdate;
        if (this.firstUpdate) {
            delta = 0L;
            this.firstUpdate = false;
        }
        this.lastUpdate = now;
        this.timeLeft -= delta;
        if (this.timeLeft <= 0L) {
            ++this.offset;
            this.timeLeft = this.delay;
        }
        if (this.offset >= this.frames.size()) {
            this.offset = 0;
        }
        return this.frames.get(this.offset);
    }

    public EfficientTexture getDynamicTexture() {
        if (this.frames.size() == 0) {
            return null;
        }
        long now = this.getTime();
        long delta = now - this.lastUpdate;
        if (this.firstUpdate) {
            delta = 0L;
            this.firstUpdate = false;
        }
        this.lastUpdate = now;
        this.timeLeft -= delta;
        if (this.timeLeft <= 0L) {
            ++this.offset;
            this.timeLeft = this.delay;
        }
        if (this.offset >= this.frames.size()) {
            this.offset = 0;
        }
        return this.textures.get(this.offset);
    }

    private long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getTextureSize() {
        return this.textures.size();
    }
}


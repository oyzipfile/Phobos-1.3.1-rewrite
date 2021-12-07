/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.newdawn.slick.Animation
 */
package me.earth.earthhack.impl.util.render.image;

import me.earth.earthhack.api.util.interfaces.Nameable;
import org.newdawn.slick.Animation;

public class GifAnimationWrapper
implements Nameable {
    private final String name;
    private final Animation animation;

    public GifAnimationWrapper(String name, Animation animation) {
        this.name = name;
        this.animation = animation;
    }

    public int getCurrentFrameTexID() {
        if (this.animation == null) {
            return 0;
        }
        this.animation.updateNoDraw();
        return this.animation.getCurrentFrame().getTexture().getTextureID();
    }

    public Animation getAnimation() {
        return this.animation;
    }

    @Override
    public String getName() {
        return this.name;
    }
}


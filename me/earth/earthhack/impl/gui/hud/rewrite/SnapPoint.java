/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.gui.hud.rewrite;

import java.awt.Color;
import java.util.Collection;
import me.earth.earthhack.api.hud.HudElement;
import me.earth.earthhack.impl.util.misc.GuiUtil;
import me.earth.earthhack.impl.util.render.Render2DUtil;

public class SnapPoint {
    private float size;
    private float off;
    private float location;
    private Orientation orientation;
    private boolean visible;
    private boolean shouldSnap;

    public SnapPoint(float off, float size, float location, boolean visible, Orientation orientation) {
        this.off = off;
        this.size = size;
        this.location = location;
        this.orientation = orientation;
        this.visible = visible;
        this.shouldSnap = true;
    }

    public void update(Collection<HudElement> elements) {
        if (this.shouldSnap) {
            for (HudElement element : elements) {
                boolean inverted = false;
                switch (this.orientation) {
                    case TOP: {
                        inverted = Math.min(Math.abs(element.getY() - this.location), Math.abs(element.getY() + element.getHeight() - this.location)) != Math.abs(element.getY() - this.location);
                        break;
                    }
                    case BOTTOM: {
                        inverted = Math.min(Math.abs(element.getY() - this.location), Math.abs(element.getY() + element.getHeight() - this.location)) == Math.abs(element.getY() - this.location);
                        break;
                    }
                    case LEFT: {
                        inverted = Math.min(Math.abs(element.getX() - this.location), Math.abs(element.getX() + element.getWidth() - this.location)) != Math.abs(element.getX() - this.location);
                        break;
                    }
                    case RIGHT: {
                        boolean bl = inverted = Math.min(Math.abs(element.getX() - this.location), Math.abs(element.getX() + element.getWidth() - this.location)) == Math.abs(element.getX() - this.location);
                    }
                }
                if (!(GuiUtil.getDistance(this, element) < 4.0) || element.isDragging()) continue;
                GuiUtil.updatePosition(this, element, inverted);
            }
        }
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (this.orientation == Orientation.BOTTOM || this.orientation == Orientation.TOP || this.orientation == Orientation.HORIZONTAL_CENTER) {
            Render2DUtil.drawLine(this.off, this.location, this.off + this.size, this.location, 1.0f, Color.WHITE.getRGB());
        } else if (this.orientation == Orientation.LEFT || this.orientation == Orientation.RIGHT || this.orientation == Orientation.VERTICAL_CENTER) {
            Render2DUtil.drawLine(this.location, this.off, this.location, this.off + this.size, 1.0f, Color.WHITE.getRGB());
        }
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public float getOff() {
        return this.off;
    }

    public void setOff(float off) {
        this.off = off;
    }

    public float getSize() {
        return this.size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getLocation() {
        return this.location;
    }

    public void setLocation(float location) {
        this.location = location;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean shouldSnap() {
        return this.shouldSnap;
    }

    public void setShouldSnap(boolean shouldSnap) {
        this.shouldSnap = shouldSnap;
    }

    public static enum Orientation {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        VERTICAL_CENTER,
        HORIZONTAL_CENTER;

    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.math.MathHelper
 */
package me.earth.earthhack.api.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import me.earth.earthhack.api.event.bus.api.Listener;
import me.earth.earthhack.api.event.bus.api.Subscriber;
import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.api.hud.SnapAxis;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.SettingContainer;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.api.setting.settings.NumberSetting;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.util.misc.GuiUtil;
import me.earth.earthhack.impl.util.render.Render2DUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

public abstract class HudElement
extends SettingContainer
implements Globals,
Subscriber,
Nameable {
    private final Setting<Boolean> enabled = this.register(new BooleanSetting("Enabled", false));
    private final Setting<Float> x = this.register(new NumberSetting<Float>("X", Float.valueOf(2.0f), Float.valueOf(-2000.0f), Float.valueOf(2000.0f)));
    private final Setting<Float> y = this.register(new NumberSetting<Float>("Y", Float.valueOf(2.0f), Float.valueOf(-2000.0f), Float.valueOf(2000.0f)));
    private final Setting<Integer> z = this.register(new NumberSetting<Integer>("Z", 0, -2000, 2000));
    private final Setting<Float> scale = this.register(new NumberSetting<Float>("Scale", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(10.0f)));
    protected final List<Listener<?>> listeners = new ArrayList();
    private final AtomicBoolean enableCheck = new AtomicBoolean();
    private final AtomicBoolean inOnEnable = new AtomicBoolean();
    private HudElement snappedTo;
    private SnapAxis axis = SnapAxis.NONE;
    private final String name;
    private float width = 100.0f;
    private float height = 100.0f;
    private boolean dragging;
    private float draggingX;
    private float draggingY;

    public HudElement(String name) {
        this.name = name;
        this.enabled.addObserver(event -> {
            if (event.isCancelled()) {
                return;
            }
            this.enableCheck.set((Boolean)event.getValue());
            if (((Boolean)event.getValue()).booleanValue() && !Bus.EVENT_BUS.isSubscribed(this)) {
                this.inOnEnable.set(true);
                this.onEnable();
                this.inOnEnable.set(false);
                if (this.enableCheck.get()) {
                    Bus.EVENT_BUS.subscribe(this);
                }
            } else if (!((Boolean)event.getValue()).booleanValue() && (Bus.EVENT_BUS.isSubscribed(this) || this.inOnEnable.get())) {
                Bus.EVENT_BUS.unsubscribe(this);
                this.onDisable();
            }
        });
    }

    public HudElement(String name, float x, float y, float width, float height) {
        this(name);
        this.x.setValue(Float.valueOf(x));
        this.y.setValue(Float.valueOf(y));
        this.width = width;
        this.height = height;
    }

    public final void toggle() {
        if (this.isEnabled()) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public final void enable() {
        if (!this.isEnabled()) {
            this.enabled.setValue(true);
        }
    }

    public final void disable() {
        if (this.isEnabled()) {
            this.enabled.setValue(false);
        }
    }

    public final void load() {
        if (this.isEnabled() && !Bus.EVENT_BUS.isSubscribed(this)) {
            Bus.EVENT_BUS.subscribe(this);
        }
        this.onLoad();
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    protected void onLoad() {
    }

    public void guiUpdate(int mouseX, int mouseY, float partialTicks) {
        if (this.dragging) {
            this.setX((float)mouseX - this.draggingX);
            this.setY((float)mouseY - this.draggingY);
        }
    }

    public void guiDraw(int mouseX, int mouseY, float partialTicks) {
        Render2DUtil.drawRect(this.x.getValue().floatValue(), this.y.getValue().floatValue(), this.x.getValue().floatValue() + this.width, this.y.getValue().floatValue() + this.height, new Color(40, 40, 40, 255).getRGB());
    }

    public void guiKeyPressed(char eventChar, int key) {
    }

    public void guiMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (GuiUtil.isHovered(this, mouseX, mouseY)) {
            this.setDragging(true);
            this.draggingX = (float)mouseX - this.getX();
            this.draggingY = (float)mouseY - this.getY();
        }
    }

    public void guiMouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.setDragging(false);
    }

    public void hudUpdate(float partialTicks) {
    }

    public abstract void hudDraw(float var1);

    public boolean isOverlapping(HudElement other) {
        double[] rec1 = new double[]{this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight()};
        double[] rec2 = new double[]{other.getX(), other.getY(), other.getX() + other.getWidth(), other.getY() + other.getHeight()};
        if (rec1[0] == rec1[2] || rec1[1] == rec1[3] || rec2[0] == rec2[2] || rec2[1] == rec2[3]) {
            return false;
        }
        return !(rec1[2] <= rec2[0] || rec1[3] <= rec2[1] || rec1[0] >= rec2[2] || rec1[1] >= rec2[3]);
    }

    @Override
    public Collection<Listener<?>> getListeners() {
        return this.listeners;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof HudElement) {
            String name = this.name;
            return name != null && name.equals(((HudElement)o).name);
        }
        return false;
    }

    public float getX() {
        return this.x.getValue().floatValue();
    }

    public void setX(float x) {
        ScaledResolution resolution = new ScaledResolution(mc);
        this.x.setValue(Float.valueOf(MathHelper.clamp((float)x, (float)0.0f, (float)resolution.getScaledWidth())));
    }

    public float getY() {
        return this.y.getValue().floatValue();
    }

    public void setY(float y) {
        ScaledResolution resolution = new ScaledResolution(mc);
        this.y.setValue(Float.valueOf(MathHelper.clamp((float)y, (float)0.0f, (float)resolution.getScaledHeight())));
    }

    public float getZ() {
        return this.z.getValue().intValue();
    }

    public void setZ(int z) {
        this.z.setValue(z);
    }

    public float getScale() {
        return this.scale.getValue().floatValue();
    }

    public void setScale(float scale) {
        this.scale.setValue(Float.valueOf(scale));
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDraggingX() {
        return this.draggingX;
    }

    public void setDraggingX(float draggingX) {
        this.draggingX = draggingX;
    }

    public float getDraggingY() {
        return this.draggingY;
    }

    public void setDraggingY(float draggingY) {
        this.draggingY = draggingY;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
        if (!dragging) {
            for (HudElement element : Managers.ELEMENTS.getRegistered().stream().sorted(Comparator.comparing(HudElement::getZ)).collect(Collectors.toList())) {
                if (this.isOverlapping(element)) {
                    this.setSnappedTo(element);
                    if (this.getY() < element.getY() + element.getHeight() / 2.0f) {
                        this.setAxis(SnapAxis.TOP);
                        continue;
                    }
                    this.setAxis(SnapAxis.BOTTOM);
                    continue;
                }
                this.setAxis(SnapAxis.NONE);
            }
        }
    }

    public boolean isEnabled() {
        return this.enabled.getValue();
    }

    public HudElement getSnappedTo() {
        return this.snappedTo;
    }

    public void setSnappedTo(HudElement snappedTo) {
        this.snappedTo = snappedTo;
    }

    public SnapAxis getAxis() {
        return this.axis;
    }

    public void setAxis(SnapAxis axis) {
        this.axis = axis;
    }
}


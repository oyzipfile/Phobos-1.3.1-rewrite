/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.ResourceLocation
 */
package me.earth.earthhack.impl.gui.hud.rewrite;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.api.cache.SettingCache;
import me.earth.earthhack.api.hud.HudElement;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.impl.gui.click.component.Component;
import me.earth.earthhack.impl.gui.click.component.impl.ColorComponent;
import me.earth.earthhack.impl.gui.click.component.impl.KeybindComponent;
import me.earth.earthhack.impl.gui.click.component.impl.ModuleComponent;
import me.earth.earthhack.impl.gui.click.component.impl.StringComponent;
import me.earth.earthhack.impl.gui.click.frame.impl.DescriptionFrame;
import me.earth.earthhack.impl.gui.hud.rewrite.SnapPoint;
import me.earth.earthhack.impl.gui.hud.rewrite.frame.HudElementFrame;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.client.clickgui.ClickGui;
import me.earth.earthhack.impl.modules.client.commands.Commands;
import me.earth.earthhack.impl.util.misc.GuiUtil;
import me.earth.earthhack.impl.util.render.Render2DUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class HudEditorGui
extends GuiScreen {
    private static final SettingCache<Boolean, BooleanSetting, Commands> BACK = Caches.getSetting(Commands.class, BooleanSetting.class, "BackgroundGui", false);
    private static final ResourceLocation BLACK_PNG = new ResourceLocation("earthhack:textures/gui/black.png");
    private static final ModuleCache<ClickGui> CLICK_GUI = Caches.getModule(ClickGui.class);
    public static DescriptionFrame descriptionFrame = new DescriptionFrame(0.0f, 0.0f, 200.0f, 16.0f);
    public static Map<String, List<SnapPoint>> snapPoints;
    private final Set<HudElement> elements = new HashSet<HudElement>();
    private HudElementFrame frame;
    private boolean oldVal = false;
    private boolean attached = false;
    private double mouseClickedX;
    private double mouseClickedY;
    private double mouseReleasedX;
    private double mouseReleasedY;
    private boolean selecting;

    public void init() {
        if (!this.attached) {
            ((ClickGui)HudEditorGui.CLICK_GUI.get()).descriptionWidth.addObserver(e -> descriptionFrame.setWidth(((Integer)e.getValue()).intValue()));
            this.attached = true;
        }
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        this.frame = new HudElementFrame();
        descriptionFrame = new DescriptionFrame(resolution.getScaledWidth() - ((ClickGui)HudEditorGui.CLICK_GUI.get()).descriptionWidth.getValue() - 4, 4.0f, ((ClickGui)HudEditorGui.CLICK_GUI.get()).descriptionWidth.getValue().intValue(), 16.0f);
        this.frame.init();
        descriptionFrame.init();
        this.oldVal = ((ClickGui)HudEditorGui.CLICK_GUI.get()).catEars.getValue();
        snapPoints = new HashMap<String, List<SnapPoint>>();
        ArrayList<SnapPoint> points = new ArrayList<SnapPoint>();
        points.add(new SnapPoint(2.0f, resolution.getScaledHeight() - 4, 2.0f, true, SnapPoint.Orientation.LEFT));
        points.add(new SnapPoint(2.0f, resolution.getScaledHeight() - 4, resolution.getScaledWidth() - 2, true, SnapPoint.Orientation.RIGHT));
        points.add(new SnapPoint(2.0f, resolution.getScaledWidth() - 4, 2.0f, true, SnapPoint.Orientation.TOP));
        points.add(new SnapPoint(2.0f, resolution.getScaledWidth() - 4, resolution.getScaledHeight() - 2, true, SnapPoint.Orientation.BOTTOM));
        points.add(new SnapPoint(2.0f, resolution.getScaledHeight() - 4, (float)resolution.getScaledWidth() / 2.0f, true, SnapPoint.Orientation.VERTICAL_CENTER));
        points.add(new SnapPoint(2.0f, resolution.getScaledWidth() - 4, (float)resolution.getScaledHeight() / 2.0f, true, SnapPoint.Orientation.HORIZONTAL_CENTER));
        snapPoints.put("default", points);
    }

    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        this.init();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.mc.world == null) {
            if (BACK.getValue().booleanValue()) {
                this.drawDefaultBackground();
            } else {
                GlStateManager.disableLighting();
                GlStateManager.disableFog();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                this.mc.getTextureManager().bindTexture(BLACK_PNG);
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos(0.0, (double)this.height, 0.0).tex(0.0, (double)((float)this.height / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
                bufferBuilder.pos((double)this.width, (double)this.height, 0.0).tex((double)((float)this.width / 32.0f), (double)((float)this.height / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
                bufferBuilder.pos((double)this.width, 0.0, 0.0).tex((double)((float)this.width / 32.0f), 0.0).color(64, 64, 64, 255).endVertex();
                bufferBuilder.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(64, 64, 64, 255).endVertex();
                tessellator.draw();
            }
        }
        if (this.oldVal != ((ClickGui)HudEditorGui.CLICK_GUI.get()).catEars.getValue()) {
            this.init();
            this.oldVal = ((ClickGui)HudEditorGui.CLICK_GUI.get()).catEars.getValue();
        }
        if (((ClickGui)HudEditorGui.CLICK_GUI.get()).blur.getValue().booleanValue()) {
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            Render2DUtil.drawBlurryRect(0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), ((ClickGui)HudEditorGui.CLICK_GUI.get()).blurAmount.getValue(), ((ClickGui)HudEditorGui.CLICK_GUI.get()).blurSize.getValue());
        }
        for (List list : snapPoints.values()) {
            for (SnapPoint point : list) {
                if (point.isVisible()) {
                    point.draw(mouseX, mouseY, partialTicks);
                }
                point.update(Managers.ELEMENTS.getRegistered());
            }
        }
        for (HudElement hudElement : Managers.ELEMENTS.getRegistered()) {
            if (!hudElement.isEnabled()) continue;
            double minX = Math.min(this.mouseClickedX, (double)mouseX);
            double minY = Math.min(this.mouseClickedY, (double)mouseY);
            double maxWidth = Math.max(this.mouseClickedX, (double)mouseY) - minX;
            double maxHeight = Math.max(this.mouseClickedY, (double)mouseY) - minY;
            if (GuiUtil.isOverlapping(new double[]{minX, minY, minX + maxWidth, minY + maxHeight}, new double[]{hudElement.getX(), hudElement.getY(), hudElement.getX() + hudElement.getWidth(), hudElement.getY() + hudElement.getHeight()})) {
                this.elements.add(hudElement);
            }
            hudElement.guiUpdate(mouseX, mouseY, partialTicks);
            hudElement.guiDraw(mouseX, mouseY, partialTicks);
        }
        if (this.selecting) {
            double minX = Math.min(this.mouseClickedX, (double)mouseX);
            double minY = Math.min(this.mouseClickedY, (double)mouseY);
            double maxWidth = Math.max(this.mouseClickedX, (double)mouseY);
            double maxHeight = Math.max(this.mouseClickedY, (double)mouseY);
            Render2DUtil.drawRect((float)minX, (float)minY, (float)maxWidth, (float)maxHeight, new Color(255, 255, 255, 128).getRGB());
        }
        this.frame.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void keyTyped(char character, int keyCode) throws IOException {
        super.keyTyped(character, keyCode);
        for (HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (!element.isEnabled()) continue;
            element.guiKeyPressed(character, keyCode);
        }
        this.frame.keyTyped(character, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        ArrayList<HudElement> clicked = new ArrayList<HudElement>();
        boolean hasDragging = false;
        for (HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (!element.isEnabled() || !GuiUtil.isHovered(element, mouseX, mouseY)) continue;
            clicked.add(element);
            if (!element.isDragging()) continue;
            hasDragging = true;
        }
        clicked.sort(Comparator.comparing(HudElement::getZ));
        if (!clicked.isEmpty()) {
            ((HudElement)clicked.get(0)).guiMouseClicked(mouseX, mouseY, mouseButton);
        } else if (!GuiUtil.isHovered(this.frame, mouseX, mouseY) && !hasDragging) {
            this.selecting = true;
            this.mouseClickedX = mouseX;
            this.mouseClickedY = mouseY;
            return;
        }
        this.frame.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (this.selecting) {
            this.mouseReleasedX = mouseX;
            this.mouseReleasedY = mouseY;
        }
        for (HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (!element.isEnabled()) continue;
            element.guiMouseReleased(mouseX, mouseY, mouseButton);
            if (!this.elements.remove(element) || !this.selecting) continue;
            element.setDraggingX((float)mouseX - element.getX());
            element.setDraggingY((float)mouseY - element.getY());
            element.setDragging(true);
        }
        this.selecting = false;
        this.frame.mouseReleased(mouseX, mouseY, mouseButton);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        for (Component comp : this.frame.getComponents()) {
            if (comp instanceof ModuleComponent) {
                ModuleComponent moduleComponent = (ModuleComponent)comp;
                for (Component component : moduleComponent.getComponents()) {
                    if (component instanceof KeybindComponent) {
                        KeybindComponent keybindComponent = (KeybindComponent)component;
                        keybindComponent.setBinding(false);
                    }
                    if (!(component instanceof StringComponent)) continue;
                    StringComponent stringComponent = (StringComponent)component;
                    stringComponent.setListening(false);
                }
            }
            for (HudElement element : Managers.ELEMENTS.getRegistered()) {
                element.setDragging(false);
            }
        }
        this.selecting = false;
        this.elements.clear();
    }

    public void onGuiOpened() {
        for (Component comp : this.frame.getComponents()) {
            if (!(comp instanceof ModuleComponent)) continue;
            ModuleComponent moduleComponent = (ModuleComponent)comp;
            for (Component component : moduleComponent.getComponents()) {
                if (!(component instanceof ColorComponent)) continue;
                ColorComponent colorComponent = (ColorComponent)component;
                float[] hsb = Color.RGBtoHSB(colorComponent.getColorSetting().getRed(), colorComponent.getColorSetting().getGreen(), colorComponent.getColorSetting().getBlue(), null);
                colorComponent.setHue(hsb[0]);
                colorComponent.setSaturation(hsb[1]);
                colorComponent.setBrightness(hsb[2]);
                colorComponent.setAlpha((float)colorComponent.getColorSetting().getAlpha() / 255.0f);
            }
        }
    }
}


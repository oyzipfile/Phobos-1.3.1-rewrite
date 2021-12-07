/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.earth.earthhack.impl.modules.client.editor;

import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.impl.gui.hud.rewrite.HudEditorGui;
import net.minecraft.client.gui.GuiScreen;

public class HudEditor
extends Module {
    public HudEditor() {
        super("HudEditor", Category.Client);
    }

    @Override
    public void onEnable() {
        HudEditorGui gui = new HudEditorGui();
        gui.init();
        mc.displayGuiScreen((GuiScreen)gui);
    }
}


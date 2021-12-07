/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 */
package me.earth.earthhack.impl.gui.click.component.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.earth.earthhack.api.setting.settings.ListSetting;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.gui.click.component.Component;
import me.earth.earthhack.impl.util.render.RenderUtil;
import net.minecraft.client.Minecraft;

public class ListComponent
extends Component {
    private final ListSetting setting;

    public ListComponent(ListSetting setting, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(setting.getName(), posX, posY, offsetX, offsetY, width, height);
        this.setting = setting;
    }

    @Override
    public void moved(float posX, float posY) {
        super.moved(posX, posY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.getLabel() + ": " + (Object)ChatFormatting.GRAY + ((Nameable)this.getListSetting().getValue()).getName(), this.getFinishedX() + 5.0f, this.getFinishedY() + this.getHeight() / 2.0f - (float)(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT >> 1), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + 5.0f, this.getFinishedY() + 1.0f, this.getWidth() - 10.0f, this.getHeight() - 2.0f);
        if (hovered) {
            int index = this.getListSetting().getValues().indexOf(this.getListSetting().getValue());
            if (index == -1) {
                if (!this.getListSetting().getValues().isEmpty()) {
                    index = 0;
                } else {
                    return;
                }
            }
            if (mouseButton == 0) {
                if (++index >= this.getListSetting().getValues().size()) {
                    index = 0;
                }
                this.getListSetting().setValue(this.getListSetting().getValues().get(index));
            } else if (mouseButton == 1) {
                if (--index < 0) {
                    index = this.getListSetting().getValues().size() - 1;
                }
                this.getListSetting().setValue(this.getListSetting().getValues().get(index));
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    public ListSetting getListSetting() {
        return this.setting;
    }
}


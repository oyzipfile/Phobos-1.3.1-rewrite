/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.Style
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.event.ClickEvent
 *  net.minecraft.util.text.event.ClickEvent$Action
 *  net.minecraft.util.text.event.HoverEvent
 *  net.minecraft.util.text.event.HoverEvent$Action
 */
package me.earth.earthhack.impl.modules.render.waypoints;

import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.impl.gui.chat.clickevents.SmartClickEvent;
import me.earth.earthhack.impl.gui.chat.components.SettingComponent;
import me.earth.earthhack.impl.gui.chat.components.SimpleComponent;
import me.earth.earthhack.impl.gui.chat.factory.ComponentFactory;
import me.earth.earthhack.impl.modules.client.commands.Commands;
import me.earth.earthhack.impl.modules.render.waypoints.WayPointSetting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class WayPointComponent
extends SettingComponent<BlockPos, WayPointSetting> {
    public WayPointComponent(final WayPointSetting setting) {
        super(setting);
        SimpleComponent value;
        SimpleComponent remove = new SimpleComponent("\u00a7cRemove");
        remove.setStyle(new Style().setHoverEvent(ComponentFactory.getHoverEvent(setting)));
        if (setting.getContainer() instanceof Module) {
            remove.getStyle().setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND){

                @Override
                public String getValue() {
                    return Commands.getPrefix() + "hiddensetting " + ((Module)setting.getContainer()).getName() + " \"" + setting.getName() + "\" remove";
                }
            });
        }
        if (setting.isCorrupted()) {
            value = new SimpleComponent("\u00a7cCorrupted ");
            value.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("This settings config got corrupted!"))));
        } else {
            BlockPos pos = (BlockPos)setting.getValue();
            String t = setting.getType().toString().substring(0, 1);
            value = new SimpleComponent("(" + t + "," + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ") ");
            value.setStyle(new Style().setHoverEvent(ComponentFactory.getHoverEvent(setting)));
        }
        this.appendSibling((ITextComponent)value);
        this.appendSibling((ITextComponent)remove);
    }

    @Override
    public String getText() {
        return super.getText() + "\u00a7f";
    }
}


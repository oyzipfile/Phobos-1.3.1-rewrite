/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.Style
 *  net.minecraft.util.text.event.ClickEvent
 *  net.minecraft.util.text.event.ClickEvent$Action
 *  net.minecraft.util.text.event.HoverEvent
 *  net.minecraft.util.text.event.HoverEvent$Action
 */
package me.earth.earthhack.impl.gui.chat.components.setting;

import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.setting.settings.EnumSetting;
import me.earth.earthhack.api.util.EnumHelper;
import me.earth.earthhack.impl.gui.chat.clickevents.SmartClickEvent;
import me.earth.earthhack.impl.gui.chat.components.SettingComponent;
import me.earth.earthhack.impl.gui.chat.components.values.EnumHoverComponent;
import me.earth.earthhack.impl.gui.chat.components.values.ValueComponent;
import me.earth.earthhack.impl.gui.chat.factory.IComponentFactory;
import me.earth.earthhack.impl.modules.client.commands.Commands;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class EnumComponent<A extends Enum<A>>
extends SettingComponent<A, EnumSetting<A>> {
    public static final IComponentFactory<?, ?> FACTORY = new EnumComponentFactory();

    public EnumComponent(final EnumSetting<A> setting) {
        super(setting);
        if (!(setting.getContainer() instanceof Module)) {
            this.appendSibling(new ValueComponent(setting).setStyle(this.getStyle()));
            return;
        }
        this.appendSibling(new ValueComponent(setting).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new EnumHoverComponent<A>(setting))).setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND){

            @Override
            public String getValue() {
                Enum<?> next = EnumHelper.next((Enum)setting.getValue());
                return Commands.getPrefix() + "hiddensetting " + ((Module)setting.getContainer()).getName() + " \"" + setting.getName() + "\" " + next.name();
            }
        })));
    }

    @Override
    public String getText() {
        return super.getText() + "\u00a7b";
    }

    private static final class EnumComponentFactory<F extends Enum<F>>
    implements IComponentFactory<F, EnumSetting<F>> {
        private EnumComponentFactory() {
        }

        @Override
        public SettingComponent<F, EnumSetting<F>> create(EnumSetting<F> setting) {
            return new EnumComponent<F>(setting);
        }
    }
}


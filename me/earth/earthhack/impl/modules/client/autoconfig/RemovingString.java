/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.client.autoconfig;

import me.earth.earthhack.api.setting.event.SettingResult;
import me.earth.earthhack.api.setting.settings.StringSetting;
import me.earth.earthhack.impl.gui.chat.factory.ComponentFactory;
import me.earth.earthhack.impl.modules.client.autoconfig.RemovingStringComponent;
import me.earth.earthhack.impl.util.helpers.addable.setting.Removable;

public class RemovingString
extends StringSetting
implements Removable {
    public RemovingString(String nameIn, String initialValue) {
        super(nameIn, initialValue);
    }

    @Override
    public void remove() {
        if (this.container != null) {
            this.container.unregister(this);
        }
    }

    @Override
    public SettingResult fromString(String string) {
        if ("remove".equalsIgnoreCase(string)) {
            this.remove();
            return new SettingResult(false, this.getName() + " was removed.");
        }
        return super.fromString(string);
    }

    @Override
    public String getInputs(String string) {
        if (string == null || string.isEmpty()) {
            return super.getInputs(string) + " or <remove>";
        }
        if ("remove".startsWith(string.toLowerCase())) {
            return "remove";
        }
        return super.getInputs(string);
    }

    @Override
    public String getInitial() {
        return (String)this.getValue();
    }

    @Override
    public void reset() {
    }

    static {
        ComponentFactory.register(RemovingString.class, RemovingStringComponent::new);
    }
}


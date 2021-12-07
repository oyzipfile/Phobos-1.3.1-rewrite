/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.api.config.preset;

import me.earth.earthhack.api.config.preset.ModulePreset;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.SettingContainer;

public class DefaultPreset<M extends SettingContainer>
extends ModulePreset<M> {
    public DefaultPreset(M module) {
        super("reset", module, "Resets all settings to the default value.");
    }

    @Override
    public void apply() {
        for (Setting<?> setting : ((SettingContainer)this.getModule()).getSettings()) {
            setting.reset();
        }
    }
}


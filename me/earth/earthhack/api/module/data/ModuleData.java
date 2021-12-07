/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.api.module.data;

import java.util.Collection;
import java.util.Map;
import me.earth.earthhack.api.config.preset.ModulePreset;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.SettingContainer;

public interface ModuleData<M extends SettingContainer> {
    public int getColor();

    public String getDescription();

    public Map<Setting<?>, String> settingDescriptions();

    public Collection<ModulePreset<M>> getPresets();
}


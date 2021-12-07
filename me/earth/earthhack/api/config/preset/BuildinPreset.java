/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.api.config.preset;

import java.util.HashMap;
import java.util.Map;
import me.earth.earthhack.api.config.preset.ModulePreset;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.SettingContainer;

public class BuildinPreset<M extends SettingContainer>
extends ModulePreset<M> {
    private final Map<Setting, Object> values = new HashMap<Setting, Object>();

    public BuildinPreset(String name, M module, String description) {
        super(name, module, description);
    }

    public <T> void add(Setting<T> setting, T value) {
        this.values.put(setting, value);
    }

    public <T> void add(String setting, T value) {
        Setting<?> s = ((SettingContainer)this.getModule()).getSetting(setting);
        this.add(s, value);
    }

    @Override
    public void apply() {
        for (Map.Entry<Setting, Object> entry : this.values.entrySet()) {
            entry.getKey().setValue(entry.getValue());
        }
    }
}


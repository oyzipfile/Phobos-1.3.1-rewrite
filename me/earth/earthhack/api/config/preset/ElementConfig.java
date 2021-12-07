/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.api.config.preset;

import java.util.ArrayList;
import java.util.List;
import me.earth.earthhack.api.config.Config;
import me.earth.earthhack.api.config.preset.HudValuePreset;
import me.earth.earthhack.api.config.preset.ModulePreset;
import me.earth.earthhack.api.hud.HudElement;
import me.earth.earthhack.api.register.Register;
import me.earth.earthhack.api.util.IdentifiedNameable;

public class ElementConfig
extends IdentifiedNameable
implements Config {
    private List<HudValuePreset> presets = new ArrayList<HudValuePreset>();

    public ElementConfig(String name) {
        super(name);
    }

    public void setPresets(List<HudValuePreset> presets) {
        if (presets != null) {
            this.presets = presets;
        }
    }

    public List<HudValuePreset> getPresets() {
        return this.presets;
    }

    @Override
    public void apply() {
        for (ModulePreset modulePreset : this.presets) {
            modulePreset.apply();
        }
    }

    public static ElementConfig create(String name, Register<HudElement> elements) {
        ElementConfig config = new ElementConfig(name);
        for (HudElement element : elements.getRegistered()) {
            HudValuePreset preset = HudValuePreset.snapshot(name, element);
            config.presets.add(preset);
        }
        return config;
    }
}


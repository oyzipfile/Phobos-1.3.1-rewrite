/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 */
package me.earth.earthhack.api.setting.settings;

import com.google.gson.JsonElement;
import java.util.List;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.event.SettingResult;
import me.earth.earthhack.api.util.interfaces.Nameable;

public class ListSetting<M extends Nameable>
extends Setting<M> {
    private List<M> values;
    private String concatenated;

    public ListSetting(String nameIn, M initialValue, List<M> values) {
        super(nameIn, initialValue);
        this.values = values;
        this.concatenated = this.concatenateInputs();
    }

    @Override
    public void fromJson(JsonElement element) {
        String name = element.getAsString();
        for (Nameable nameable : this.values) {
            if (!nameable.getName().equalsIgnoreCase(name)) continue;
            this.setValue(nameable);
        }
    }

    @Override
    public SettingResult fromString(String string) {
        for (Nameable nameable : this.values) {
            if (!nameable.getName().equalsIgnoreCase(string)) continue;
            return SettingResult.SUCCESSFUL;
        }
        return new SettingResult(false, "No value found with name " + string + ".");
    }

    @Override
    public String getInputs(String string) {
        if (string == null || string.length() == 0) {
            return this.concatenated;
        }
        for (Nameable nameable : this.values) {
            if (!nameable.getName().startsWith(string)) continue;
            return nameable.getName();
        }
        return "";
    }

    private String concatenateInputs() {
        StringBuilder builder = new StringBuilder("<");
        for (Nameable nameable : this.values) {
            builder.append(nameable.getName()).append(", ");
        }
        if (builder.length() < 2) {
            builder.append(", ");
        }
        builder.replace(builder.length() - 2, builder.length(), ">");
        return builder.toString();
    }

    public List<M> getValues() {
        return this.values;
    }
}


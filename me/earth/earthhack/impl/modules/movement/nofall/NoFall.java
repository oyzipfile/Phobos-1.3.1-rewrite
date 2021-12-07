/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.movement.nofall;

import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.settings.EnumSetting;
import me.earth.earthhack.impl.modules.movement.nofall.ListenerMotion;
import me.earth.earthhack.impl.modules.movement.nofall.ListenerPlayerPackets;
import me.earth.earthhack.impl.modules.movement.nofall.mode.FallMode;
import me.earth.earthhack.impl.util.client.SimpleData;
import me.earth.earthhack.impl.util.math.StopWatch;

public class NoFall
extends Module {
    protected final Setting<FallMode> mode = this.register(new EnumSetting<FallMode>("Mode", FallMode.Packet));
    protected final StopWatch timer = new StopWatch();

    public NoFall() {
        super("NoFall", Category.Movement);
        this.listeners.add(new ListenerMotion(this));
        this.listeners.addAll(new ListenerPlayerPackets(this).getListeners());
        SimpleData data = new SimpleData(this, "Prevents Falldamage.");
        data.register(this.mode, "-Packet standard NoFall.\n-AAC a NoFall for the AAC anticheat.\n-Anti prevents damage by elevating your position silently.\n-Bucket uses a water bucket if you have on in your hotbar.");
        this.setData(data);
    }

    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
}


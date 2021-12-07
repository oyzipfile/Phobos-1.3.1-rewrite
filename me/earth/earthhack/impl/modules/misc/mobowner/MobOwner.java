/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.entity.passive.EntityTameable
 */
package me.earth.earthhack.impl.modules.misc.mobowner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.impl.modules.misc.mobowner.ListenerTick;
import me.earth.earthhack.impl.util.client.SimpleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;

public class MobOwner
extends Module {
    final Map<UUID, String> cache = new HashMap<UUID, String>();

    public MobOwner() {
        super("MobOwner", Category.Misc);
        this.listeners.add(new ListenerTick(this));
        this.setData(new SimpleData(this, "Displays the name of their owners above mobs."));
    }

    @Override
    protected void onDisable() {
        if (MobOwner.mc.world != null) {
            for (Entity entity : MobOwner.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityTameable) && !(entity instanceof AbstractHorse)) continue;
                try {
                    entity.setAlwaysRenderNameTag(false);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


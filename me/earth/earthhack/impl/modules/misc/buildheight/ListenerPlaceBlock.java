/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumFacing
 */
package me.earth.earthhack.impl.modules.misc.buildheight;

import me.earth.earthhack.impl.core.mixins.network.client.ICPacketPlayerTryUseItemOnBlock;
import me.earth.earthhack.impl.event.events.network.PacketEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.misc.buildheight.BuildHeight;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;

final class ListenerPlaceBlock
extends ModuleListener<BuildHeight, PacketEvent.Send<CPacketPlayerTryUseItemOnBlock>> {
    public ListenerPlaceBlock(BuildHeight module) {
        super(module, PacketEvent.Send.class, CPacketPlayerTryUseItemOnBlock.class);
    }

    @Override
    public void invoke(PacketEvent.Send<CPacketPlayerTryUseItemOnBlock> event) {
        CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
        if (!(packet.getPos().getY() < 255 || ((BuildHeight)this.module).crystals.getValue().booleanValue() && ListenerPlaceBlock.mc.player.getHeldItem(packet.getHand()).getItem() != Items.END_CRYSTAL || packet.getDirection() != EnumFacing.UP)) {
            ((ICPacketPlayerTryUseItemOnBlock)packet).setFacing(EnumFacing.DOWN);
        }
    }
}


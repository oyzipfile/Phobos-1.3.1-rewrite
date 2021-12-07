/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 */
package me.earth.earthhack.impl.managers.minecraft;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.earth.earthhack.api.event.bus.EventListener;
import me.earth.earthhack.api.event.bus.SubscriberImpl;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.event.events.misc.TickEvent;
import me.earth.earthhack.impl.event.events.network.WorldClientEvent;
import me.earth.earthhack.impl.event.events.render.GuiScreenEvent;
import me.earth.earthhack.impl.event.listeners.SendListener;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.util.math.StopWatch;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class EnderChestManager
extends SubscriberImpl
implements Globals {
    private final Map<Integer, ItemStack> stacks = new ConcurrentHashMap<Integer, ItemStack>();
    private final StopWatch timer = new StopWatch();
    private boolean shouldCache;
    private boolean hasInitialized;

    public EnderChestManager() {
        this.listeners.add(new EventListener<WorldClientEvent>(WorldClientEvent.class){

            @Override
            public void invoke(WorldClientEvent event) {
                EnderChestManager.this.stacks.clear();
                EnderChestManager.this.shouldCache = false;
                EnderChestManager.this.hasInitialized = false;
            }
        });
        this.listeners.add(new SendListener<CPacketPlayerTryUseItemOnBlock>(CPacketPlayerTryUseItemOnBlock.class, event -> {
            CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
            if (EnderChestManager.mc.world.getBlockState(packet.getPos()).getBlock() == Blocks.ENDER_CHEST && !Managers.ACTION.isSneaking()) {
                this.shouldCache = true;
                this.timer.reset();
            }
        }));
        this.listeners.add(new EventListener<GuiScreenEvent<?>>(GuiScreenEvent.class){

            @Override
            public void invoke(GuiScreenEvent<?> event) {
                GuiScreen currentScreen = Globals.mc.currentScreen;
                if (event.getScreen() == null && currentScreen instanceof GuiChest && EnderChestManager.this.shouldCache) {
                    GuiChest container = (GuiChest)currentScreen;
                    for (int i = 0; i < 28; ++i) {
                        ItemStack stack = (ItemStack)container.inventorySlots.getInventory().get(i);
                        EnderChestManager.this.stacks.put(i, stack);
                    }
                    EnderChestManager.this.hasInitialized = true;
                    EnderChestManager.this.shouldCache = false;
                }
            }
        });
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class){

            @Override
            public void invoke(TickEvent event) {
                if (EnderChestManager.this.shouldCache && EnderChestManager.this.timer.passed(1000L) && Globals.mc.currentScreen == null) {
                    EnderChestManager.this.shouldCache = false;
                }
            }
        });
    }

    public ItemStack getStackInSlot(int slot) {
        if (!this.hasInitialized) {
            return null;
        }
        return this.stacks.get(slot);
    }

    public boolean has(Predicate<ItemStack> stackPredicate) {
        if (this.stacks.isEmpty()) {
            return false;
        }
        return this.stacks.values().stream().anyMatch(stackPredicate);
    }

    public boolean hasItem(Item item) {
        if (this.stacks.isEmpty()) {
            return false;
        }
        return this.stacks.values().stream().anyMatch(stack -> stack.getItem() == item);
    }

    public boolean hasInitialized() {
        return this.hasInitialized;
    }
}


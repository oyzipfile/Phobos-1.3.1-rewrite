/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package me.earth.earthhack.impl.managers.render;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.math.BlockPos;

public class ColoredLightManager {
    private final Map<BlockPos, Integer> coloredLightMap = new ConcurrentHashMap<BlockPos, Integer>();
}


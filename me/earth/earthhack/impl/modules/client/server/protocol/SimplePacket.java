/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.client.server.protocol;

import me.earth.earthhack.impl.modules.client.server.api.IPacket;

public class SimplePacket
implements IPacket {
    private final byte[] buffer;
    private final int id;

    public SimplePacket(int id, byte[] buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public byte[] getBuffer() {
        return this.buffer;
    }
}


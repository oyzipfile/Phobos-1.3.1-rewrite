/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.misc.announcer.util;

public class Announcement {
    private final String name;
    private int amount;

    public Announcement(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getName() {
        return this.name;
    }
}

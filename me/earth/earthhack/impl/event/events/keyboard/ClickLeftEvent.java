/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.event.events.keyboard;

import me.earth.earthhack.api.event.events.Event;

public class ClickLeftEvent
extends Event {
    private int leftClickCounter;

    public ClickLeftEvent(int leftClickCounter) {
        this.leftClickCounter = leftClickCounter;
    }

    public int getLeftClickCounter() {
        return this.leftClickCounter;
    }

    public void setLeftClickCounter(int leftClickCounter) {
        this.leftClickCounter = leftClickCounter;
    }
}


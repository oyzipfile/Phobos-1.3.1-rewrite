/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.client.media;

import me.earth.earthhack.impl.event.events.render.ChatEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.client.media.Media;

final class ListenerClearChat
extends ModuleListener<Media, ChatEvent.Clear> {
    public ListenerClearChat(Media module) {
        super(module, ChatEvent.Clear.class);
    }

    @Override
    public void invoke(ChatEvent.Clear event) {
        ((Media)this.module).cache.clear();
    }
}


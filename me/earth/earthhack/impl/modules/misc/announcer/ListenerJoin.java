/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.events.network.ConnectionEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.misc.announcer.Announcer;
import me.earth.earthhack.impl.modules.misc.announcer.util.AnnouncementType;

final class ListenerJoin
extends ModuleListener<Announcer, ConnectionEvent.Join> {
    public ListenerJoin(Announcer module) {
        super(module, ConnectionEvent.Join.class);
    }

    @Override
    public void invoke(ConnectionEvent.Join event) {
        if (((Announcer)this.module).join.getValue().booleanValue() && !event.getName().equals(mc.getSession().getProfile().getName())) {
            ((Announcer)this.module).addWordAndIncrement(AnnouncementType.Join, event.getName());
        }
    }
}


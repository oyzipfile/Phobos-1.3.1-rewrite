/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.commands.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimesProcess {
    private final AtomicBoolean valid = new AtomicBoolean(true);
    private final List<Future<?>> futures;

    public TimesProcess(int size) {
        this.futures = new ArrayList(size);
    }

    public void addFuture(Future<?> future) {
        this.futures.add(future);
    }

    public void clear() {
        for (Future<?> future : this.futures) {
            future.cancel(true);
        }
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }

    public boolean isValid() {
        return this.valid.get();
    }
}


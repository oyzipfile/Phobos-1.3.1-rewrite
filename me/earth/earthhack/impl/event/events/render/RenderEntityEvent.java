/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.entity.Entity
 */
package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.Event;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class RenderEntityEvent
extends Event {
    private final Render<Entity> renderer;
    private final Entity entity;

    private RenderEntityEvent(Render<Entity> renderer, Entity entity) {
        this.renderer = renderer;
        this.entity = entity;
    }

    public Render<Entity> getRenderer() {
        return this.renderer;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public static class Post
    extends RenderEntityEvent {
        public Post(Render<Entity> renderer, Entity entity) {
            super(renderer, entity);
        }
    }

    public static class Pre
    extends RenderEntityEvent {
        private final double posX;
        private final double posY;
        private final double posZ;
        private final float entityYaw;
        private final float partialTicks;

        public Pre(Render<Entity> renderer, Entity entity, double posX, double posY, double posZ, float entityYaw, float partialTicks) {
            super(renderer, entity);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.entityYaw = entityYaw;
            this.partialTicks = partialTicks;
        }

        public double getPosX() {
            return this.posX;
        }

        public double getPosY() {
            return this.posY;
        }

        public double getPosZ() {
            return this.posZ;
        }

        public float getEntityYaw() {
            return this.entityYaw;
        }

        public float getPartialTicks() {
            return this.partialTicks;
        }
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.entity.Entity
 */
package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.Event;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class RenderArmorEvent
extends Event {
    private final Entity entity;
    private final ModelBase model;

    private RenderArmorEvent(Entity entity, ModelBase model) {
        this.entity = entity;
        this.model = model;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public ModelBase getModel() {
        return this.model;
    }

    public static class Post
    extends RenderArmorEvent {
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float ageInTicks;
        private final float netHeadYaw;
        private final float headPitch;
        private final float scale;

        public Post(Entity entity, ModelBase model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            super(entity, model);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
            this.scale = scale;
        }

        public float getLimbSwing() {
            return this.limbSwing;
        }

        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }

        public float getAgeInTicks() {
            return this.ageInTicks;
        }

        public float getNetHeadYaw() {
            return this.netHeadYaw;
        }

        public float getHeadPitch() {
            return this.headPitch;
        }

        public float getScale() {
            return this.scale;
        }
    }

    public static class Pre
    extends RenderArmorEvent {
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float ageInTicks;
        private final float netHeadYaw;
        private final float headPitch;
        private final float scale;

        public Pre(Entity entity, ModelBase model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            super(entity, model);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
            this.scale = scale;
        }

        public float getLimbSwing() {
            return this.limbSwing;
        }

        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }

        public float getAgeInTicks() {
            return this.ageInTicks;
        }

        public float getNetHeadYaw() {
            return this.netHeadYaw;
        }

        public float getHeadPitch() {
            return this.headPitch;
        }

        public float getScale() {
            return this.scale;
        }
    }
}


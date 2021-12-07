/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraft.util.text.ITextComponent
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.gui;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.api.cache.SettingCache;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.api.setting.settings.ColorSetting;
import me.earth.earthhack.impl.core.ducks.gui.IChatLine;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.misc.chat.Chat;
import me.earth.earthhack.impl.util.animation.AnimationMode;
import me.earth.earthhack.impl.util.animation.TimeAnimation;
import me.earth.earthhack.impl.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatLine.class})
public abstract class MixinChatLine
implements IChatLine {
    private static final ModuleCache<Chat> CHAT = Caches.getModule(Chat.class);
    private static final SettingCache<Boolean, BooleanSetting, Chat> TIME_STAMPS = Caches.getSetting(Chat.class, BooleanSetting.class, "TimeStamps", false);
    private static final SettingCache<Color, ColorSetting, Chat> COLOR = Caches.getSetting(Chat.class, ColorSetting.class, "TimeStampsColor", Color.WHITE);
    private static final SettingCache<Boolean, BooleanSetting, Chat> RAINBOW = Caches.getSetting(Chat.class, BooleanSetting.class, "RainbowTimeStamps", false);
    private static final DateFormat FORMAT = new SimpleDateFormat("k:mm");
    private static final Minecraft MC = Minecraft.getMinecraft();
    private String timeStamp;

    @Override
    public String getTimeStamp() {
        return this.timeStamp;
    }

    @Override
    @Accessor(value="lineString")
    public abstract void setComponent(ITextComponent var1);

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    public void constructorHook(int updateCounterCreatedIn, ITextComponent lineStringIn, int chatLineIDIn, CallbackInfo ci) {
        Color color = COLOR.getValue();
        int hex = MathUtil.toRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        String colorString = "\u00a7z" + Integer.toHexString(hex);
        this.timeStamp = (RAINBOW.getValue() != false ? "\u00a7+" : colorString) + "<" + FORMAT.format(new Date()) + "> " + "\u00a7r";
        String t = lineStringIn.getFormattedText();
        if (CHAT.isEnabled() && TIME_STAMPS.getValue().booleanValue()) {
            t = this.timeStamp + t;
        }
        ((Chat)MixinChatLine.CHAT.get()).animationMap.put((ChatLine)ChatLine.class.cast(this), new TimeAnimation(((Chat)MixinChatLine.CHAT.get()).time.getValue().intValue(), -MixinChatLine.MC.fontRenderer.getStringWidth(t), 0.0, false, AnimationMode.LINEAR));
    }
}


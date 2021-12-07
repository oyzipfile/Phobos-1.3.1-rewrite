/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.Tuple
 */
package me.earth.earthhack.impl.modules.render.rechams;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.api.setting.settings.ColorSetting;
import me.earth.earthhack.api.setting.settings.EnumSetting;
import me.earth.earthhack.api.setting.settings.ListSetting;
import me.earth.earthhack.api.setting.settings.NumberSetting;
import me.earth.earthhack.api.setting.settings.StringSetting;
import me.earth.earthhack.impl.gui.visibility.PageBuilder;
import me.earth.earthhack.impl.gui.visibility.Visibilities;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.render.rechams.ListenerBeginRender;
import me.earth.earthhack.impl.modules.render.rechams.ListenerPreRenderHud;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRender2D;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRender3D;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderArmor;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderCrystalCube;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderCrystalPost;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderCrystalPre;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderEntity;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderEntityPost;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderModelPost;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderModelPre;
import me.earth.earthhack.impl.modules.render.rechams.ListenerRenderWorld;
import me.earth.earthhack.impl.modules.render.rechams.mode.ChamsMode;
import me.earth.earthhack.impl.modules.render.rechams.mode.ChamsPage;
import me.earth.earthhack.impl.util.client.SimpleData;
import me.earth.earthhack.impl.util.minecraft.EntityType;
import me.earth.earthhack.impl.util.render.GlShader;
import me.earth.earthhack.impl.util.render.image.GifImage;
import me.earth.earthhack.impl.util.render.image.NameableImage;
import me.earth.earthhack.impl.util.render.shader.FramebufferWrapper;
import me.earth.earthhack.impl.util.render.shader.SettingShader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Tuple;

public class Chams
extends Module {
    public final Setting<ChamsPage> page = this.register(new EnumSetting<ChamsPage>("Page", ChamsPage.Players));
    public final Setting<ChamsMode> playerMode = this.register(new EnumSetting<ChamsMode>("PlayerMode", ChamsMode.Normal));
    public final Setting<Color> color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
    public final Setting<Color> wallsColor = this.register(new ColorSetting("WallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Color> armorColor = this.register(new ColorSetting("ArmorColor", new Color(255, 255, 255, 255)));
    public final Setting<Color> armorWallsColor = this.register(new ColorSetting("ArmorWallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> xqz = this.register(new BooleanSetting("XQZ", false));
    public final Setting<Boolean> armor = this.register(new BooleanSetting("Armor", false));
    public final Setting<Boolean> armorXQZ = this.register(new BooleanSetting("ArmorXQZ", false));
    public final Setting<Boolean> wireframe = this.register(new BooleanSetting("Wireframe", false));
    public final Setting<Color> wireframeColor = this.register(new ColorSetting("WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> wireframeXQZ = this.register(new BooleanSetting("Wireframe", false));
    public final Setting<Color> wireframeWallsColor = this.register(new ColorSetting("WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> lightning = this.register(new BooleanSetting("Lightning", false));
    public final Setting<Color> lightningColor = this.register(new ColorSetting("LightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> xqzLightning = this.register(new BooleanSetting("XQZLightning", false));
    public final Setting<Color> wallsLightningColor = this.register(new ColorSetting("WallsLightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> glint = this.register(new BooleanSetting("Glint", false));
    public final Setting<Color> glintColor = this.register(new ColorSetting("GlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> xqzGlint = this.register(new BooleanSetting("XQZGlint", false));
    public final Setting<Color> wallsGlintColor = this.register(new ColorSetting("WallsGlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Float> glintMultiplier = this.register(new NumberSetting<Float>("GlintSpeed", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> glintRotate = this.register(new NumberSetting<Float>("GlintRotateMult", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> glintScale = this.register(new NumberSetting<Float>("GlintScale", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    public final Setting<Integer> glints = this.register(new NumberSetting<Integer>("Glints", 2, 1, 10));
    public final Setting<NameableImage> image = this.register(new ListSetting<NameableImage>("Image", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<NameableImage> wallsImage = this.register(new ListSetting<NameableImage>("WallsImage", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<Boolean> fit = this.register(new BooleanSetting("Fit", false));
    public final Setting<Boolean> fill = this.register(new BooleanSetting("Fill", false));
    public final Setting<Boolean> useGif = this.register(new BooleanSetting("UseGif", false));
    public final Setting<Boolean> useWallsGif = this.register(new BooleanSetting("UseWallsGif", false));
    public final Setting<GifImage> gif = this.register(new ListSetting<GifImage>("Gif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<GifImage> wallsGif = this.register(new ListSetting<GifImage>("WallsGif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<Float> mixFactor = this.register(new NumberSetting<Float>("ImageMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<Float> colorMixFactor = this.register(new NumberSetting<Float>("ImageColorMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<SettingShader> shader = this.register(new ListSetting<SettingShader>("Shader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> wallsShader = this.register(new ListSetting<SettingShader>("WallsShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> framebufferShader = this.register(new ListSetting<SettingShader>("FramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> wallsFramebufferShader = this.register(new ListSetting<SettingShader>("WallsFramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<Boolean> alphaTest = this.register(new BooleanSetting("AlphaTest", false));
    public final Setting<ChamsMode> friendMode = this.register(new EnumSetting<ChamsMode>("FriendMode", ChamsMode.Normal));
    public final Setting<Color> friendColor = this.register(new ColorSetting("F-Color", new Color(255, 255, 255, 255)));
    public final Setting<Color> friendWallColor = this.register(new ColorSetting("F-WallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Color> friendArmorColor = this.register(new ColorSetting("F-ArmorColor", new Color(255, 255, 255, 255)));
    public final Setting<Color> friendArmorWallsColor = this.register(new ColorSetting("F-ArmorWallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> friendXQZ = this.register(new BooleanSetting("F-XQZ", false));
    public final Setting<Boolean> friendArmor = this.register(new BooleanSetting("F-Armor", false));
    public final Setting<Boolean> friendArmorXQZ = this.register(new BooleanSetting("F-ArmorXQZ", false));
    public final Setting<Boolean> friendWireframe = this.register(new BooleanSetting("F-Wireframe", false));
    public final Setting<Color> friendWireframeColor = this.register(new ColorSetting("F-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> friendWireframeXQZ = this.register(new BooleanSetting("F-Wireframe", false));
    public final Setting<Color> friendWireframeWallsColor = this.register(new ColorSetting("F-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> friendLightning = this.register(new BooleanSetting("F-Lightning", false));
    public final Setting<Color> friendLightningColor = this.register(new ColorSetting("F-LightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> friendXQZLightning = this.register(new BooleanSetting("F-XQZLightning", false));
    public final Setting<Color> friendWallsLightningColor = this.register(new ColorSetting("F-WallsLightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> friendGlint = this.register(new BooleanSetting("F-Glint", false));
    public final Setting<Color> friendGlintColor = this.register(new ColorSetting("F-GlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> friendXQZGlint = this.register(new BooleanSetting("F-XQZGlint", false));
    public final Setting<Color> friendWallsGlintColor = this.register(new ColorSetting("F-WallsGlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Float> friendGlintMultiplier = this.register(new NumberSetting<Float>("F-GlintSpeed", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> friendGlintRotate = this.register(new NumberSetting<Float>("F-GlintRotateMult", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> friendGlintScale = this.register(new NumberSetting<Float>("F-GlintScale", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    public final Setting<Integer> friendGlints = this.register(new NumberSetting<Integer>("F-Glints", 2, 1, 10));
    public final Setting<NameableImage> friendImage = this.register(new ListSetting<NameableImage>("F-Image", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<NameableImage> friendWallsImage = this.register(new ListSetting<NameableImage>("F-WallsImage", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<Boolean> friendFit = this.register(new BooleanSetting("F-Fit", false));
    public final Setting<Boolean> friendFill = this.register(new BooleanSetting("F-Fill", false));
    public final Setting<Boolean> friendUseGif = this.register(new BooleanSetting("F-UseGif", false));
    public final Setting<Boolean> friendUseWallsGif = this.register(new BooleanSetting("F-UseWallsGif", false));
    public final Setting<GifImage> friendGif = this.register(new ListSetting<GifImage>("F-Gif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<GifImage> friendWallsGif = this.register(new ListSetting<GifImage>("F-WallsGif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<Float> friendMixFactor = this.register(new NumberSetting<Float>("F-ImageMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<Float> friendColorMixFactor = this.register(new NumberSetting<Float>("F-ImageColorMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<SettingShader> friendShader = this.register(new ListSetting<SettingShader>("F-Shader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> friendWallsShader = this.register(new ListSetting<SettingShader>("F-WallsShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> friendFramebufferShader = this.register(new ListSetting<SettingShader>("F-FramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> friendWallsFramebufferShader = this.register(new ListSetting<SettingShader>("F-WallsFramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<Boolean> friendAlphaTest = this.register(new BooleanSetting("F-AlphaTest", false));
    public final Setting<ChamsMode> enemyMode = this.register(new EnumSetting<ChamsMode>("EnemyMode", ChamsMode.Normal));
    public final Setting<Color> enemyColor = this.register(new ColorSetting("E-Color", new Color(255, 255, 255, 255)));
    public final Setting<Color> enemyWallColor = this.register(new ColorSetting("E-WallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Color> enemyArmorColor = this.register(new ColorSetting("E-ArmorColor", new Color(255, 255, 255, 255)));
    public final Setting<Color> enemyArmorWallsColor = this.register(new ColorSetting("E-ArmorWallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> enemyXQZ = this.register(new BooleanSetting("E-XQZ", false));
    public final Setting<Boolean> enemyArmor = this.register(new BooleanSetting("E-Armor", false));
    public final Setting<Boolean> enemyArmorXQZ = this.register(new BooleanSetting("E-ArmorXQZ", false));
    public final Setting<Boolean> enemyWireframe = this.register(new BooleanSetting("E-Wireframe", false));
    public final Setting<Color> enemyWireframeColor = this.register(new ColorSetting("E-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> enemyWireframeXQZ = this.register(new BooleanSetting("E-Wireframe", false));
    public final Setting<Color> enemyWireframeWallsColor = this.register(new ColorSetting("E-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> enemyLightning = this.register(new BooleanSetting("E-Lightning", false));
    public final Setting<Color> enemyLightningColor = this.register(new ColorSetting("E-LightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> enemyXQZLightning = this.register(new BooleanSetting("E-XQZLightning", false));
    public final Setting<Color> enemyWallsLightningColor = this.register(new ColorSetting("E-WallsLightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> enemyGlint = this.register(new BooleanSetting("E-Glint", false));
    public final Setting<Color> enemyGlintColor = this.register(new ColorSetting("E-GlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> enemyXQZGlint = this.register(new BooleanSetting("E-XQZGlint", false));
    public final Setting<Color> enemyWallsGlintColor = this.register(new ColorSetting("E-WallsGlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Float> enemyGlintMultiplier = this.register(new NumberSetting<Float>("E-GlintSpeed", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> enemyGlintRotate = this.register(new NumberSetting<Float>("E-GlintRotateMult", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> enemyGlintScale = this.register(new NumberSetting<Float>("E-GlintScale", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    public final Setting<Integer> enemyGlints = this.register(new NumberSetting<Integer>("E-Glints", 2, 1, 10));
    public final Setting<NameableImage> enemyImage = this.register(new ListSetting<NameableImage>("E-Image", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<NameableImage> enemyWallsImage = this.register(new ListSetting<NameableImage>("E-WallsImage", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<Boolean> enemyFit = this.register(new BooleanSetting("E-Fit", false));
    public final Setting<Boolean> enemyFill = this.register(new BooleanSetting("E-Fill", false));
    public final Setting<Boolean> enemyUseGif = this.register(new BooleanSetting("E-UseGif", false));
    public final Setting<Boolean> enemyUseWallsGif = this.register(new BooleanSetting("E-UseWallsGif", false));
    public final Setting<GifImage> enemyGif = this.register(new ListSetting<GifImage>("E-Gif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<GifImage> enemyWallsGif = this.register(new ListSetting<GifImage>("E-WallsGif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<Float> enemyMixFactor = this.register(new NumberSetting<Float>("E-ImageMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<Float> enemyColorMixFactor = this.register(new NumberSetting<Float>("E-ImageColorMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<SettingShader> enemyShader = this.register(new ListSetting<SettingShader>("E-Shader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> enemyWallsShader = this.register(new ListSetting<SettingShader>("E-WallsShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> enemyFramebufferShader = this.register(new ListSetting<SettingShader>("E-FramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> enemyWallsFramebufferShader = this.register(new ListSetting<SettingShader>("E-WallsFramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<Boolean> enemyAlphaTest = this.register(new BooleanSetting("E-AlphaTest", false));
    public final Setting<ChamsMode> crystalMode = this.register(new EnumSetting<ChamsMode>("CrystalMode", ChamsMode.Normal));
    public final Setting<Color> crystalColor = this.register(new ColorSetting("C-Color", new Color(255, 255, 255, 255)));
    public final Setting<Color> crystalWallColor = this.register(new ColorSetting("C-WallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> crystalXQZ = this.register(new BooleanSetting("C-XQZ", false));
    public final Setting<Boolean> crystalWireframe = this.register(new BooleanSetting("C-Wireframe", false));
    public final Setting<Color> crystalWireframeColor = this.register(new ColorSetting("C-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> crystalWireframeXQZ = this.register(new BooleanSetting("C-Wireframe", false));
    public final Setting<Color> crystalWireframeWallsColor = this.register(new ColorSetting("C-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> crystalLightning = this.register(new BooleanSetting("C-Lightning", false));
    public final Setting<Color> crystalLightningColor = this.register(new ColorSetting("C-LightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> crystalXQZLightning = this.register(new BooleanSetting("C-XQZLightning", false));
    public final Setting<Color> crystalWallsLightningColor = this.register(new ColorSetting("C-WallsLightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> crystalGlint = this.register(new BooleanSetting("C-Glint", false));
    public final Setting<Color> crystalGlintColor = this.register(new ColorSetting("C-GlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> crystalXQZGlint = this.register(new BooleanSetting("C-XQZGlint", false));
    public final Setting<Color> crystalWallsGlintColor = this.register(new ColorSetting("C-WallsGlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Float> crystalGlintMultiplier = this.register(new NumberSetting<Float>("C-GlintSpeed", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> crystalGlintRotate = this.register(new NumberSetting<Float>("C-GlintRotateMult", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> crystalGlintScale = this.register(new NumberSetting<Float>("C-GlintScale", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    public final Setting<Integer> crystalGlints = this.register(new NumberSetting<Integer>("C-Glints", 2, 1, 10));
    public final Setting<NameableImage> crystalImage = this.register(new ListSetting<NameableImage>("C-Image", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<NameableImage> crystalWallsImage = this.register(new ListSetting<NameableImage>("C-WallsImage", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    public final Setting<Boolean> crystalFit = this.register(new BooleanSetting("C-Fit", false));
    public final Setting<Boolean> crystalFill = this.register(new BooleanSetting("C-Fill", false));
    public final Setting<Boolean> crystalUseGif = this.register(new BooleanSetting("C-UseGif", false));
    public final Setting<Boolean> crystalUseWallsGif = this.register(new BooleanSetting("C-UseWallsGif", false));
    public final Setting<GifImage> crystalGif = this.register(new ListSetting<GifImage>("C-Gif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<GifImage> crystalWallsGif = this.register(new ListSetting<GifImage>("C-WallsGif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<Float> crystalMixFactor = this.register(new NumberSetting<Float>("C-ImageMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<Float> crystalColorMixFactor = this.register(new NumberSetting<Float>("C-ImageColorMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<SettingShader> crystalShader = this.register(new ListSetting<SettingShader>("C-Shader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> crystalWallsShader = this.register(new ListSetting<SettingShader>("C-WallsShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> crystalFramebufferShader = this.register(new ListSetting<SettingShader>("C-FramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<SettingShader> crystalWallsFramebufferShader = this.register(new ListSetting<SettingShader>("C-WallsFramebufferShader", Managers.FILES.getInitialShader(), Managers.FILES.getShaders()));
    public final Setting<Boolean> crystalAlphaTest = this.register(new BooleanSetting("C-AlphaTest", false));
    public final Setting<ChamsMode> animalMode = this.register(new EnumSetting<ChamsMode>("AnimalMode", ChamsMode.Normal));
    public final Setting<Color> animalColor = this.register(new ColorSetting("A-Color", new Color(255, 255, 255, 255)));
    public final Setting<Color> animalWallColor = this.register(new ColorSetting("A-WallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> animalXQZ = this.register(new BooleanSetting("A-XQZ", false));
    public final Setting<Boolean> animalWireframe = this.register(new BooleanSetting("A-Wireframe", false));
    public final Setting<Color> animalWireframeColor = this.register(new ColorSetting("A-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> animalWireframeXQZ = this.register(new BooleanSetting("A-Wireframe", false));
    public final Setting<Color> animalWireframeWallsColor = this.register(new ColorSetting("A-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> animalLightning = this.register(new BooleanSetting("A-Lightning", false));
    public final Setting<Color> animalLightningColor = this.register(new ColorSetting("A-LightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> animalXQZLightning = this.register(new BooleanSetting("A-XQZLightning", false));
    public final Setting<Color> animalWallsLightningColor = this.register(new ColorSetting("A-WallsLightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> animalGlint = this.register(new BooleanSetting("A-Glint", false));
    public final Setting<Color> animalGlintColor = this.register(new ColorSetting("A-GlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> animalXQZGlint = this.register(new BooleanSetting("A-XQZGlint", false));
    public final Setting<Color> animalWallsGlintColor = this.register(new ColorSetting("A-WallsGlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Float> animalGlintMultiplier = this.register(new NumberSetting<Float>("A-GlintSpeed", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> animalGlintRotate = this.register(new NumberSetting<Float>("A-GlintRotateMult", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> animalGlintScale = this.register(new NumberSetting<Float>("A-GlintScale", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    public final Setting<Integer> animalGlints = this.register(new NumberSetting<Integer>("A-Glints", 2, 1, 10));
    public final Setting<String> animalImage = this.register(new StringSetting("A-Image", "None!"));
    public final Setting<String> animalWallImage = this.register(new StringSetting("A-WallsImage", "None!"));
    public final Setting<Boolean> animalFit = this.register(new BooleanSetting("A-Fit", false));
    public final Setting<Float> animalMixFactor = this.register(new NumberSetting<Float>("A-ImageMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<Float> animalColorMixFactor = this.register(new NumberSetting<Float>("A-ImageColorMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<ChamsMode> monsterMode = this.register(new EnumSetting<ChamsMode>("MonsterMode", ChamsMode.Normal));
    public final Setting<Color> monsterColor = this.register(new ColorSetting("M-Color", new Color(255, 255, 255, 255)));
    public final Setting<Color> monsterWallColor = this.register(new ColorSetting("M-WallsColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> monsterXQZ = this.register(new BooleanSetting("M-XQZ", false));
    public final Setting<Boolean> monsterWireframe = this.register(new BooleanSetting("M-Wireframe", false));
    public final Setting<Color> monsterWireframeColor = this.register(new ColorSetting("M-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> monsterWireframeXQZ = this.register(new BooleanSetting("M-Wireframe", false));
    public final Setting<Color> monsterWireframeWallsColor = this.register(new ColorSetting("M-WireframeColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> monsterLightning = this.register(new BooleanSetting("M-Lightning", false));
    public final Setting<Color> monsterLightningColor = this.register(new ColorSetting("M-LightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> monsterXQZLightning = this.register(new BooleanSetting("M-XQZLightning", false));
    public final Setting<Color> monsterWallsLightningColor = this.register(new ColorSetting("M-WallsLightningColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> monsterGlint = this.register(new BooleanSetting("M-Glint", false));
    public final Setting<Color> monsterGlintColor = this.register(new ColorSetting("M-GlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Boolean> monsterXQZGlint = this.register(new BooleanSetting("M-XQZGlint", false));
    public final Setting<Color> monsterWallsGlintColor = this.register(new ColorSetting("M-WallsGlintColor", new Color(255, 255, 255, 255)));
    public final Setting<Float> monsterGlintMultiplier = this.register(new NumberSetting<Float>("M-GlintSpeed", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> monsterGlintRotate = this.register(new NumberSetting<Float>("M-GlintRotateMult", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(5.0f)));
    public final Setting<Float> monsterGlintScale = this.register(new NumberSetting<Float>("M-GlintScale", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    public final Setting<Integer> monsterGlints = this.register(new NumberSetting<Integer>("M-Glints", 2, 1, 10));
    public final Setting<String> monsterImage = this.register(new StringSetting("M-Image", "None!"));
    public final Setting<String> monsterWallImage = this.register(new StringSetting("M-WallsImage", "None!"));
    public final Setting<Boolean> monsterFit = this.register(new BooleanSetting("M-Fit", false));
    public final Setting<Float> monsterMixFactor = this.register(new NumberSetting<Float>("M-ImageMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final Setting<Float> monsterColorMixFactor = this.register(new NumberSetting<Float>("M-ImageColorMixFactor", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    public final GlShader imageShader = new GlShader("image");
    public boolean renderLayers;
    public boolean forceRenderEntities;
    public final Tuple<ChamsPage, FramebufferWrapper> playerBuffer = new Tuple((Object)ChamsPage.Players, (Object)new FramebufferWrapper());
    public final Tuple<ChamsPage, FramebufferWrapper> friendBuffer = new Tuple((Object)ChamsPage.Friends, (Object)new FramebufferWrapper());
    public final Tuple<ChamsPage, FramebufferWrapper> enemyBuffer = new Tuple((Object)ChamsPage.Enemies, (Object)new FramebufferWrapper());
    public final Tuple<ChamsPage, FramebufferWrapper> crystalBuffer = new Tuple((Object)ChamsPage.Crystals, (Object)new FramebufferWrapper());
    public final Tuple<ChamsPage, FramebufferWrapper> animalBuffer = new Tuple((Object)ChamsPage.Animals, (Object)new FramebufferWrapper());
    public final Tuple<ChamsPage, FramebufferWrapper> monsterBuffer = new Tuple((Object)ChamsPage.Monsters, (Object)new FramebufferWrapper());
    public final GlShader framebufferImageShader = new GlShader("framebufferimage");
    public final GlShader stencilShader = new GlShader("stencil");

    public Chams() {
        super("ReChams", Category.Render);
        this.listeners.add(new ListenerRenderModelPre(this));
        this.listeners.add(new ListenerRenderModelPost(this));
        this.listeners.add(new ListenerRenderCrystalPre(this));
        this.listeners.add(new ListenerRenderCrystalPost(this));
        this.listeners.add(new ListenerRenderEntity(this));
        this.listeners.add(new ListenerRenderWorld(this));
        this.listeners.add(new ListenerPreRenderHud(this));
        this.listeners.add(new ListenerRender3D(this));
        this.listeners.add(new ListenerRenderEntityPost(this));
        this.listeners.add(new ListenerRender2D(this));
        this.listeners.add(new ListenerBeginRender(this));
        this.listeners.add(new ListenerRenderCrystalCube(this));
        this.listeners.add(new ListenerRenderArmor(this));
        new PageBuilder<ChamsPage>(this, this.page).addPage(p -> p == ChamsPage.Players, (Setting<?>)this.playerMode, (Setting<?>)this.alphaTest).addPage(p -> p == ChamsPage.Friends, (Setting<?>)this.friendMode, (Setting<?>)this.friendAlphaTest).addPage(p -> p == ChamsPage.Enemies, (Setting<?>)this.enemyMode, (Setting<?>)this.enemyAlphaTest).addPage(p -> p == ChamsPage.Crystals, (Setting<?>)this.crystalMode, (Setting<?>)this.crystalAlphaTest).addPage(p -> p == ChamsPage.Animals, (Setting<?>)this.animalMode, (Setting<?>)this.animalColorMixFactor).addPage(p -> p == ChamsPage.Monsters, (Setting<?>)this.monsterMode, (Setting<?>)this.monsterColorMixFactor).register(Visibilities.VISIBILITY_MANAGER);
        this.setData(new SimpleData(this, "Tha best in the game."));
    }

    public ChamsMode getModeFromEntity(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendMode.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyMode.getValue();
            }
            return this.playerMode.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalMode.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalMode.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterMode.getValue();
        }
        return ChamsMode.None;
    }

    public boolean shouldXQZ(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendXQZ.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyXQZ.getValue();
            }
            return this.xqz.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalXQZ.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalXQZ.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterXQZ.getValue();
        }
        return false;
    }

    public Color getColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyColor.getValue();
            }
            return this.color.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalColor.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalColor.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterColor.getValue();
        }
        return Color.WHITE;
    }

    public Color getWallsColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyColor.getValue();
            }
            return this.color.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalColor.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalColor.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterColor.getValue();
        }
        return Color.WHITE;
    }

    public Color getArmorColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendArmorColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyArmorColor.getValue();
            }
            return this.armorColor.getValue();
        }
        return Color.WHITE;
    }

    public Color getArmorWallsColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendArmorWallsColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyArmorWallsColor.getValue();
            }
            return this.armorWallsColor.getValue();
        }
        return Color.WHITE;
    }

    public Color getGlintColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendGlintColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyGlintColor.getValue();
            }
            return this.glintColor.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalGlintColor.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalGlintColor.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterGlintColor.getValue();
        }
        return Color.WHITE;
    }

    public Color getGlintWallsColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendWallsGlintColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyWallsGlintColor.getValue();
            }
            return this.wallsGlintColor.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalWallsGlintColor.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalWallsGlintColor.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterWallsGlintColor.getValue();
        }
        return Color.WHITE;
    }

    public Color getLightningColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendLightningColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyLightningColor.getValue();
            }
            return this.lightningColor.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalLightningColor.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalLightningColor.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterLightningColor.getValue();
        }
        return Color.WHITE;
    }

    public Color getLightningWallsColor(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendWallsLightningColor.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyWallsLightningColor.getValue();
            }
            return this.wallsLightningColor.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalWallsLightningColor.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalWallsLightningColor.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterWallsLightningColor.getValue();
        }
        return Color.WHITE;
    }

    public boolean shouldGlint(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendGlint.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyGlint.getValue();
            }
            return this.glint.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalGlint.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalGlint.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterGlint.getValue();
        }
        return false;
    }

    public boolean shouldLightning(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendLightning.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyLightning.getValue();
            }
            return this.lightning.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalLightning.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalLightning.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterLightning.getValue();
        }
        return false;
    }

    public boolean shouldWallsGlint(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendXQZGlint.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyXQZGlint.getValue();
            }
            return this.xqzGlint.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalXQZGlint.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalXQZGlint.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterXQZGlint.getValue();
        }
        return false;
    }

    public boolean shouldWallsLightning(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Managers.FRIENDS.contains(entity)) {
                return this.friendXQZ.getValue();
            }
            if (Managers.ENEMIES.contains(entity)) {
                return this.enemyXQZ.getValue();
            }
            return this.xqz.getValue();
        }
        if (entity instanceof EntityEnderCrystal) {
            return this.crystalXQZ.getValue();
        }
        if (EntityType.isAngry(entity) || EntityType.isAnimal(entity)) {
            return this.animalXQZ.getValue();
        }
        if (EntityType.isMonster(entity) || EntityType.isBoss(entity)) {
            return this.monsterXQZ.getValue();
        }
        return false;
    }

    public boolean shouldFit(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendFit.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyFit.getValue().booleanValue() : this.fit.getValue().booleanValue())) : (entity instanceof EntityEnderCrystal ? this.crystalFit.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? this.animalFit.getValue() : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? this.monsterXQZ.getValue() : false)));
    }

    public boolean shouldFill(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendFill.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyFill.getValue().booleanValue() : this.fill.getValue().booleanValue())) : (entity instanceof EntityEnderCrystal ? this.crystalFill.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? false : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? false : false)));
    }

    public boolean shouldGif(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendUseGif.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyUseGif.getValue().booleanValue() : this.useGif.getValue().booleanValue())) : (entity instanceof EntityEnderCrystal ? this.crystalUseGif.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? false : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? false : false)));
    }

    public boolean shouldWallsGif(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendUseWallsGif.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyUseWallsGif.getValue().booleanValue() : this.useWallsGif.getValue().booleanValue())) : (entity instanceof EntityEnderCrystal ? this.crystalUseWallsGif.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? false : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? false : false)));
    }

    public boolean shouldGif(ChamsPage entity) {
        switch (entity) {
            case Players: {
                return this.useGif.getValue();
            }
            case Friends: {
                return this.friendUseGif.getValue();
            }
            case Enemies: {
                return this.enemyUseGif.getValue();
            }
            case Crystals: {
                return this.crystalUseGif.getValue();
            }
        }
        return false;
    }

    public boolean shouldWallsGif(ChamsPage entity) {
        switch (entity) {
            case Players: {
                return this.useWallsGif.getValue();
            }
            case Friends: {
                return this.friendUseWallsGif.getValue();
            }
            case Enemies: {
                return this.enemyUseWallsGif.getValue();
            }
            case Crystals: {
                return this.crystalUseWallsGif.getValue();
            }
        }
        return false;
    }

    public GifImage getGif(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendGif.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyGif.getValue() : this.gif.getValue())) : (entity instanceof EntityEnderCrystal ? this.crystalGif.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? null : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? null : null)));
    }

    public GifImage getWallsGif(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendWallsGif.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyWallsGif.getValue() : this.wallsGif.getValue())) : (entity instanceof EntityEnderCrystal ? this.crystalWallsGif.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? null : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? null : null)));
    }

    public NameableImage getImage(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendImage.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyImage.getValue() : this.image.getValue())) : (entity instanceof EntityEnderCrystal ? this.crystalImage.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? null : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? null : null)));
    }

    public NameableImage getWallsImage(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendWallsImage.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyWallsImage.getValue() : this.wallsImage.getValue())) : (entity instanceof EntityEnderCrystal ? this.crystalWallsImage.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? null : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? null : null)));
    }

    public GifImage getGif(ChamsPage page) {
        switch (page) {
            case Players: {
                return this.gif.getValue();
            }
            case Enemies: {
                return this.enemyGif.getValue();
            }
            case Friends: {
                return this.friendGif.getValue();
            }
            case Crystals: {
                return this.crystalGif.getValue();
            }
        }
        return null;
    }

    public GifImage getWallsGif(ChamsPage page) {
        switch (page) {
            case Players: {
                return this.wallsGif.getValue();
            }
            case Enemies: {
                return this.enemyWallsGif.getValue();
            }
            case Friends: {
                return this.friendWallsGif.getValue();
            }
            case Crystals: {
                return this.crystalWallsGif.getValue();
            }
        }
        return null;
    }

    public NameableImage getImage(ChamsPage page) {
        switch (page) {
            case Players: {
                return this.image.getValue();
            }
            case Enemies: {
                return this.enemyImage.getValue();
            }
            case Friends: {
                return this.friendImage.getValue();
            }
            case Crystals: {
                return this.crystalImage.getValue();
            }
        }
        return null;
    }

    public NameableImage getWallsImage(ChamsPage page) {
        switch (page) {
            case Players: {
                return this.wallsImage.getValue();
            }
            case Enemies: {
                return this.enemyWallsImage.getValue();
            }
            case Friends: {
                return this.friendWallsImage.getValue();
            }
            case Crystals: {
                return this.crystalWallsImage.getValue();
            }
        }
        return null;
    }

    public float getMixFactor(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendMixFactor.getValue().floatValue() : (Managers.ENEMIES.contains(entity) ? this.enemyMixFactor.getValue().floatValue() : this.mixFactor.getValue().floatValue())) : (entity instanceof EntityEnderCrystal ? this.crystalMixFactor.getValue().floatValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? this.animalMixFactor.getValue().floatValue() : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? this.monsterMixFactor.getValue().floatValue() : 1.0f)));
    }

    public float getColorMixFactor(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendColorMixFactor.getValue().floatValue() : (Managers.ENEMIES.contains(entity) ? this.enemyColorMixFactor.getValue().floatValue() : this.colorMixFactor.getValue().floatValue())) : (entity instanceof EntityEnderCrystal ? this.crystalColorMixFactor.getValue().floatValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? this.animalColorMixFactor.getValue().floatValue() : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? this.monsterColorMixFactor.getValue().floatValue() : 0.0f)));
    }

    public boolean shouldAlphaTest(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendAlphaTest.getValue() : (Managers.ENEMIES.contains(entity) ? this.enemyAlphaTest.getValue().booleanValue() : this.alphaTest.getValue().booleanValue())) : (entity instanceof EntityEnderCrystal ? this.crystalAlphaTest.getValue() : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? false : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? false : false)));
    }

    public ChamsMode[] getAllCurrentModes() {
        ChamsMode[] toReturn = new ChamsMode[]{this.playerMode.getValue(), this.friendMode.getValue(), this.enemyMode.getValue(), this.crystalMode.getValue(), this.animalMode.getValue(), this.monsterMode.getValue()};
        return toReturn;
    }

    public Tuple<ChamsPage, FramebufferWrapper> getFrameBufferFromEntity(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? this.friendBuffer : (Managers.ENEMIES.contains(entity) ? this.enemyBuffer : this.playerBuffer)) : (entity instanceof EntityEnderCrystal ? this.crystalBuffer : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? this.animalBuffer : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? this.monsterBuffer : null)));
    }

    public List<Tuple<ChamsPage, FramebufferWrapper>> getFramebuffers() {
        ArrayList<Tuple<ChamsPage, FramebufferWrapper>> toReturn = new ArrayList<Tuple<ChamsPage, FramebufferWrapper>>();
        toReturn.add(this.playerBuffer);
        toReturn.add(this.friendBuffer);
        toReturn.add(this.enemyBuffer);
        toReturn.add(this.crystalBuffer);
        toReturn.add(this.animalBuffer);
        toReturn.add(this.monsterBuffer);
        return toReturn;
    }

    public List<Tuple<ChamsPage, FramebufferWrapper>> getPlayerBuffers() {
        ArrayList<Tuple<ChamsPage, FramebufferWrapper>> toReturn = new ArrayList<Tuple<ChamsPage, FramebufferWrapper>>();
        toReturn.add(this.playerBuffer);
        toReturn.add(this.friendBuffer);
        toReturn.add(this.enemyBuffer);
        return toReturn;
    }

    public List<Tuple<ChamsPage, FramebufferWrapper>> getFramebuffersFromMode(ChamsMode mode) {
        ArrayList<Tuple<ChamsPage, FramebufferWrapper>> toReturn = new ArrayList<Tuple<ChamsPage, FramebufferWrapper>>();
        if (this.crystalMode.getValue() == mode) {
            toReturn.add(this.crystalBuffer);
        }
        if (this.playerMode.getValue() == mode) {
            toReturn.add(this.playerBuffer);
        }
        if (this.friendMode.getValue() == mode) {
            toReturn.add(this.friendBuffer);
        }
        if (this.enemyMode.getValue() == mode) {
            toReturn.add(this.enemyBuffer);
        }
        if (this.animalMode.getValue() == mode) {
            toReturn.add(this.animalBuffer);
        }
        if (this.monsterMode.getValue() == mode) {
            toReturn.add(this.monsterBuffer);
        }
        return toReturn;
    }

    public List<ChamsPage> getPagesFromMode(ChamsMode mode) {
        ArrayList<ChamsPage> toReturn = new ArrayList<ChamsPage>();
        if (this.crystalMode.getValue() == mode) {
            toReturn.add(ChamsPage.Crystals);
        }
        if (this.playerMode.getValue() == mode) {
            toReturn.add(ChamsPage.Players);
        }
        if (this.friendMode.getValue() == mode) {
            toReturn.add(ChamsPage.Friends);
        }
        if (this.enemyMode.getValue() == mode) {
            toReturn.add(ChamsPage.Enemies);
        }
        if (this.animalMode.getValue() == mode) {
            toReturn.add(ChamsPage.Animals);
        }
        if (this.monsterMode.getValue() == mode) {
            toReturn.add(ChamsPage.Monsters);
        }
        return toReturn;
    }

    public Predicate<Entity> getEntityPredicate(ChamsMode mode) {
        return entity -> mode == this.getModeFromEntity((Entity)entity);
    }

    public boolean isValid(Entity entity, ChamsMode mode) {
        return this.getEntityPredicate(mode).test(entity);
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.GLAllocation
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  org.joml.Vector3f
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Project
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.render.entity;

import com.google.common.base.Predicate;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.List;
import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.api.cache.SettingCache;
import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.api.setting.settings.NumberSetting;
import me.earth.earthhack.impl.Earthhack;
import me.earth.earthhack.impl.core.ducks.entity.IEntityRenderer;
import me.earth.earthhack.impl.event.events.misc.ReachEvent;
import me.earth.earthhack.impl.event.events.render.AspectRatioEvent;
import me.earth.earthhack.impl.event.events.render.BeginRenderHandEvent;
import me.earth.earthhack.impl.event.events.render.PreRenderHandEvent;
import me.earth.earthhack.impl.event.events.render.PreRenderHudEvent;
import me.earth.earthhack.impl.event.events.render.Render3DEvent;
import me.earth.earthhack.impl.event.events.render.RenderEntitiesEvent;
import me.earth.earthhack.impl.event.events.render.RenderItemActivationEvent;
import me.earth.earthhack.impl.event.events.render.WorldRenderEvent;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.client.management.Management;
import me.earth.earthhack.impl.modules.player.blocktweaks.BlockTweaks;
import me.earth.earthhack.impl.modules.player.raytrace.RayTrace;
import me.earth.earthhack.impl.modules.player.spectate.Spectate;
import me.earth.earthhack.impl.modules.render.ambience.Ambience;
import me.earth.earthhack.impl.modules.render.blockhighlight.BlockHighlight;
import me.earth.earthhack.impl.modules.render.esp.ESP;
import me.earth.earthhack.impl.modules.render.norender.NoRender;
import me.earth.earthhack.impl.modules.render.viewclip.CameraClip;
import me.earth.earthhack.impl.modules.render.weather.Weather;
import me.earth.earthhack.impl.util.math.MathUtil;
import me.earth.earthhack.impl.util.math.raytrace.RayTracer;
import me.earth.earthhack.impl.util.math.rotation.RotationUtil;
import me.earth.earthhack.impl.util.minecraft.InventoryUtil;
import me.earth.earthhack.impl.util.misc.MutableWrapper;
import me.earth.earthhack.impl.util.render.GLUProjection;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderer.class})
public abstract class MixinEntityRenderer
implements IEntityRenderer {
    private static final ModuleCache<NoRender> NO_RENDER = Caches.getModule(NoRender.class);
    private static final ModuleCache<BlockHighlight> BLOCK_HIGHLIGHT = Caches.getModule(BlockHighlight.class);
    private static final ModuleCache<BlockTweaks> BLOCK_TWEAKS = Caches.getModule(BlockTweaks.class);
    private static final ModuleCache<CameraClip> CAMERA_CLIP = Caches.getModule(CameraClip.class);
    private static final ModuleCache<Weather> WEATHER = Caches.getModule(Weather.class);
    private static final ModuleCache<RayTrace> RAYTRACE = Caches.getModule(RayTrace.class);
    private static final ModuleCache<Spectate> SPECTATE = Caches.getModule(Spectate.class);
    private static final ModuleCache<Management> MANAGEMENT = Caches.getModule(Management.class);
    private static final ModuleCache<Ambience> AMBIENCE = Caches.getModule(Ambience.class);
    private static final SettingCache<Boolean, BooleanSetting, CameraClip> EXTEND = Caches.getSetting(CameraClip.class, BooleanSetting.class, "Extend", false);
    private static final SettingCache<Double, NumberSetting<Double>, CameraClip> DISTANCE = Caches.getSetting(CameraClip.class, Setting.class, "Distance", 10.0);
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private ItemStack itemActivationItem;
    @Shadow
    @Final
    private int[] lightmapColors;
    private float lastReach;

    @Shadow
    protected abstract void orientCamera(float var1);

    @Shadow
    protected abstract void setupCameraTransform(float var1, int var2);

    @Shadow
    protected abstract FloatBuffer setFogColorBuffer(float var1, float var2, float var3, float var4);

    @Shadow
    protected abstract void renderHand(float var1, int var2);

    @Override
    public void invokeOrientCamera(float partialTicks) {
        this.orientCamera(partialTicks);
    }

    @Override
    public void invokeSetupCameraTransform(float partialTicks, int pass) {
        this.setupCameraTransform(partialTicks, pass);
    }

    @Override
    public void invokeRenderHand(float partialTicks, int pass) {
        this.renderHand(partialTicks, pass);
    }

    @Override
    @Accessor(value="lightmapUpdateNeeded")
    public abstract void setLightmapUpdateNeeded(boolean var1);

    @Redirect(method={"setupCameraTransform"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V", remap=false))
    private void onSetupCameraTransform(float fovy, float aspect, float zNear, float zFar) {
        AspectRatioEvent event = new AspectRatioEvent((float)this.mc.displayWidth / (float)this.mc.displayHeight);
        Bus.EVENT_BUS.post(event);
        Project.gluPerspective((float)fovy, (float)event.getAspectRatio(), (float)zNear, (float)zFar);
    }

    @Redirect(method={"renderWorldPass"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V", remap=false))
    private void onRenderWorldPass(float fovy, float aspect, float zNear, float zFar) {
        AspectRatioEvent event = new AspectRatioEvent((float)this.mc.displayWidth / (float)this.mc.displayHeight);
        Bus.EVENT_BUS.post(event);
        Project.gluPerspective((float)fovy, (float)event.getAspectRatio(), (float)zNear, (float)zFar);
        GLUProjection projection = GLUProjection.getInstance();
        IntBuffer viewPort = GLAllocation.createDirectIntBuffer((int)16);
        FloatBuffer modelView = GLAllocation.createDirectFloatBuffer((int)16);
        FloatBuffer projectionPort = GLAllocation.createDirectFloatBuffer((int)16);
        GL11.glGetFloat((int)2982, (FloatBuffer)modelView);
        GL11.glGetFloat((int)2983, (FloatBuffer)projectionPort);
        GL11.glGetInteger((int)2978, (IntBuffer)viewPort);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        projection.updateMatrices(viewPort, modelView, projectionPort, (double)scaledResolution.getScaledWidth() / (double)Minecraft.getMinecraft().displayWidth, (double)scaledResolution.getScaledHeight() / (double)Minecraft.getMinecraft().displayHeight);
    }

    @Redirect(method={"renderCloudsCheck"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V", remap=false))
    private void onRenderCloudsCheck(float fovy, float aspect, float zNear, float zFar) {
        AspectRatioEvent event = new AspectRatioEvent((float)this.mc.displayWidth / (float)this.mc.displayHeight);
        Bus.EVENT_BUS.post(event);
        Project.gluPerspective((float)fovy, (float)event.getAspectRatio(), (float)zNear, (float)zFar);
    }

    @Inject(method={"renderItemActivation"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderItemActivationHook(CallbackInfo info) {
        if (this.itemActivationItem != null && NO_RENDER.returnIfPresent(NoRender::noTotems, false).booleanValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
            info.cancel();
        }
    }

    @Inject(method={"renderWorldPass"}, at={@At(value="INVOKE", target="net/minecraft/client/renderer/GlStateManager.clear(I)V", ordinal=1, shift=At.Shift.AFTER)})
    private void renderWorldPassHook(int pass, float partialTicks, long finishTimeNano, CallbackInfo info) {
        if (Display.isActive() || Display.isVisible()) {
            Bus.EVENT_BUS.post(new Render3DEvent(partialTicks));
        }
    }

    @Inject(method={"renderWorldPass"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderGlobal;renderEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;F)V", shift=At.Shift.AFTER)})
    private void renderEntitiesHook(int pass, float partialTicks, long finishTimeNano, CallbackInfo info) {
        Bus.EVENT_BUS.post(new RenderEntitiesEvent());
    }

    @Redirect(method={"setupCameraTransform"}, at=@At(value="FIELD", target="Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
    public float prevTimeInPortalHook(EntityPlayerSP entityPlayerSP) {
        if (NO_RENDER.returnIfPresent(NoRender::noNausea, false).booleanValue()) {
            return -3.4028235E38f;
        }
        return entityPlayerSP.prevTimeInPortal;
    }

    @Inject(method={"setupFog"}, at={@At(value="RETURN")}, cancellable=true)
    public void setupFogHook(int startCoords, float partialTicks, CallbackInfo info) {
        if (NO_RENDER.returnIfPresent(NoRender::noFog, false).booleanValue()) {
            GlStateManager.setFogDensity((float)0.0f);
        }
    }

    @Inject(method={"hurtCameraEffect"}, at={@At(value="HEAD")}, cancellable=true)
    public void hurtCameraEffectHook(float ticks, CallbackInfo info) {
        if (NO_RENDER.returnIfPresent(NoRender::noHurtCam, false).booleanValue() || ESP.isRendering) {
            info.cancel();
        }
    }

    @Redirect(method={"getMouseOver"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcludingHook(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate) {
        if (BLOCK_TWEAKS.isEnabled() && ((BlockTweaks)BLOCK_TWEAKS.get()).noMiningTrace()) {
            return Collections.emptyList();
        }
        try {
            Predicate p = e -> predicate.test(e) && !e.equals((Object)this.mc.player);
            return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, p);
        }
        catch (Exception e2) {
            Earthhack.getLogger().warn("It's that Exception again...");
            e2.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Redirect(method={"getMouseOver"}, at=@At(value="INVOKE", target="net/minecraft/client/multiplayer/PlayerControllerMP.getBlockReachDistance()F"))
    private float getBlockReachDistanceHook(PlayerControllerMP controller) {
        ReachEvent event = new ReachEvent(controller.getBlockReachDistance(), 0.0f);
        Bus.EVENT_BUS.post(event);
        this.lastReach = event.isCancelled() ? event.getReach() : 0.0f;
        return this.mc.playerController.getBlockReachDistance() + this.lastReach;
    }

    @Redirect(method={"getMouseOver"}, at=@At(value="INVOKE", target="net/minecraft/util/math/Vec3d.distanceTo(Lnet/minecraft/util/math/Vec3d;)D"))
    private double distanceToHook(Vec3d vec3d, Vec3d vec3d1) {
        return vec3d.distanceTo(vec3d1) - (double)this.lastReach;
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=3, at=@At(value="STORE", ordinal=0), require=1)
    public double changeCameraDistanceHook(double range) {
        return CAMERA_CLIP.isEnabled() && EXTEND.getValue() != false ? DISTANCE.getValue() : range;
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=7, at=@At(value="STORE", ordinal=0), require=1)
    public double orientCameraHook(double range) {
        return CAMERA_CLIP.isEnabled() ? (EXTEND.getValue().booleanValue() ? DISTANCE.getValue() : 4.0) : range;
    }

    @Redirect(method={"renderWorldPass"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBox(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/RayTraceResult;IF)V"))
    public void drawSelectionBoxHook(RenderGlobal renderGlobal, EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
        if (!BLOCK_HIGHLIGHT.isEnabled()) {
            renderGlobal.drawSelectionBox(player, movingObjectPositionIn, execute, partialTicks);
        }
    }

    @Inject(method={"renderWorldPass"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/EntityRenderer;renderRainSnow(F)V")})
    public void weatherHook(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        if (WEATHER.isEnabled()) {
            ((Weather)WEATHER.get()).render(partialTicks);
        }
    }

    @Redirect(method={"getMouseOver"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;rayTrace(DF)Lnet/minecraft/util/math/RayTraceResult;"))
    private RayTraceResult getMouseOverHook(Entity entity, double blockReachDistance, float partialTicks) {
        if (RAYTRACE.returnIfPresent(RayTrace::isActive, false).booleanValue()) {
            Vec3d start = entity.getPositionEyes(partialTicks);
            Vec3d look = entity.getLook(partialTicks);
            Vec3d end = start.add(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance);
            if (RAYTRACE.returnIfPresent(RayTrace::liquidCrystalPlace, false).booleanValue() && (this.mc.player.isInsideOfMaterial(Material.WATER) || this.mc.player.isInsideOfMaterial(Material.LAVA)) && InventoryUtil.isHolding(Blocks.OBSIDIAN)) {
                MutableWrapper<BlockPos> opposite = new MutableWrapper<BlockPos>();
                RayTraceResult result = this.traceInLiquid(start, end, opposite, true);
                if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    if (result.getBlockPos().equals((Object)opposite.get())) {
                        result.sideHit = result.sideHit.getOpposite();
                    }
                    return result;
                }
                result = this.traceInLiquid(start, end, opposite, false);
                if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    if (result.getBlockPos().equals((Object)opposite.get())) {
                        result.sideHit = result.sideHit.getOpposite();
                    }
                    return result;
                }
            }
            if (RAYTRACE.returnIfPresent(RayTrace::phaseCheck, false).booleanValue()) {
                return RayTracer.traceTri((World)this.mc.world, (IBlockAccess)this.mc.world, start, end, false, false, true, (b, p, ef) -> {
                    AxisAlignedBB bb = this.mc.world.getBlockState(p).getBoundingBox((IBlockAccess)this.mc.world, p).offset(p);
                    if (RotationUtil.getRotationPlayer().getEntityBoundingBox().intersects(bb) && (double)p.getY() > this.mc.player.posY + 0.25) {
                        return false;
                    }
                    if (ef == null) {
                        return true;
                    }
                    for (Entity e : this.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(p.offset(ef)))) {
                        if (e == null || e.isDead || this.mc.player.equals((Object)e) || RotationUtil.getRotationPlayer().equals((Object)e)) continue;
                        return false;
                    }
                    return true;
                });
            }
        }
        return entity.rayTrace(blockReachDistance, partialTicks);
    }

    @Redirect(method={"updateCameraAndRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
    private void turnHook(EntityPlayerSP entityPlayerSP, float yaw, float pitch) {
        if (SPECTATE.isEnabled()) {
            EntityPlayer spectate;
            if (((Spectate)SPECTATE.get()).shouldTurn() && (spectate = ((Spectate)SPECTATE.get()).getRender()) != null) {
                spectate.turn(yaw, pitch);
                spectate.rotationYawHead = spectate.rotationYaw;
            }
            return;
        }
        entityPlayerSP.turn(yaw, pitch);
    }

    @Inject(method={"setupFogColor"}, at={@At(value="HEAD")}, cancellable=true)
    public void setupFogColoHook(boolean black, CallbackInfo ci) {
        if (((Management)MANAGEMENT.get()).isUsingCustomFogColor()) {
            ci.cancel();
            Color fogColor = ((Management)MANAGEMENT.get()).getCustomFogColor();
            GlStateManager.glFog((int)2918, (FloatBuffer)this.setFogColorBuffer((float)fogColor.getRed() / 255.0f, (float)fogColor.getGreen() / 255.0f, (float)fogColor.getBlue() / 255.0f, (float)fogColor.getAlpha() / 255.0f));
        }
    }

    @Redirect(method={"renderHand"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/ItemRenderer;renderItemInFirstPerson(F)V"))
    private void drHook(ItemRenderer itemRenderer, float partialTicks) {
        BeginRenderHandEvent event = new BeginRenderHandEvent();
        Bus.EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            itemRenderer.renderItemInFirstPerson(partialTicks);
        }
    }

    @Inject(method={"updateCameraAndRender"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/EntityRenderer;setupOverlayRendering()V", ordinal=0)})
    private void nurseHook(float partialTicks, long nanoTime, CallbackInfo ci) {
        PreRenderHudEvent event = new PreRenderHudEvent();
        Bus.EVENT_BUS.post(event);
    }

    @Inject(method={"renderHand"}, at={@At(value="HEAD")})
    private void preRenderHandHook(float partialTicks, int pass, CallbackInfo info) {
        PreRenderHandEvent event = new PreRenderHandEvent();
        Bus.EVENT_BUS.post(event);
    }

    @Inject(method={"renderWorld"}, at={@At(value="RETURN")})
    private void renderWorldHook(CallbackInfo info) {
        int guiScale = this.mc.gameSettings.guiScale;
        this.mc.gameSettings.guiScale = 1;
        Bus.EVENT_BUS.post(new WorldRenderEvent());
        this.mc.gameSettings.guiScale = guiScale;
    }

    @Redirect(method={"renderItemActivation"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void renderItemHook(RenderItem item, ItemStack stack, ItemCameraTransforms.TransformType type) {
        RenderItemActivationEvent event = new RenderItemActivationEvent(item, stack, type);
        Bus.EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            event.getRenderItem().renderItem(stack, type);
        }
    }

    @Inject(method={"updateLightmap"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/texture/DynamicTexture;updateDynamicTexture()V", shift=At.Shift.BEFORE)})
    private void updateTextureHook(float partialTicks, CallbackInfo ci) {
        if (AMBIENCE.isEnabled()) {
            for (int i = 0; i < this.lightmapColors.length; ++i) {
                Color ambientColor = ((Ambience)AMBIENCE.get()).getColor();
                int alpha = ambientColor.getAlpha();
                float modifier = (float)alpha / 255.0f;
                int color = this.lightmapColors[i];
                int[] bgr = MathUtil.toRGBAArray(color);
                Vector3f values = new Vector3f((float)bgr[2] / 255.0f, (float)bgr[1] / 255.0f, (float)bgr[0] / 255.0f);
                Vector3f newValues = new Vector3f((float)ambientColor.getRed() / 255.0f, (float)ambientColor.getGreen() / 255.0f, (float)ambientColor.getBlue() / 255.0f);
                Vector3f finalValues = MathUtil.mix(values, newValues, modifier);
                int red = (int)(finalValues.x * 255.0f);
                int green = (int)(finalValues.y * 255.0f);
                int blue = (int)(finalValues.z * 255.0f);
                this.lightmapColors[i] = 0xFF000000 | red << 16 | green << 8 | blue;
            }
        }
    }

    private RayTraceResult traceInLiquid(Vec3d start, Vec3d end, MutableWrapper<BlockPos> opposite, boolean air) {
        return RayTracer.traceTri((World)this.mc.world, (IBlockAccess)this.mc.world, start, end, false, false, true, (b, p, ef) -> {
            if (ef == null || RotationUtil.getRotationPlayer().getEntityBoundingBox().intersects(new AxisAlignedBB(p))) {
                return false;
            }
            BlockPos pos = p.offset(ef);
            BlockPos up = pos.up();
            if ((!air || this.mc.world.getBlockState(up).getBlock() == Blocks.AIR && this.mc.world.getBlockState(up.up()).getBlock() == Blocks.AIR) && this.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB((double)up.getX(), (double)up.getY(), (double)up.getZ(), (double)up.getX() + 1.0, (double)up.getY() + 2.0, (double)up.getZ() + 1.0)).isEmpty()) {
                return true;
            }
            pos = p.offset(ef.getOpposite());
            up = pos.up();
            if ((!air || this.mc.world.getBlockState(up).getBlock() == Blocks.AIR && this.mc.world.getBlockState(up.up()).getBlock() == Blocks.AIR) && this.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB((double)up.getX(), (double)up.getY(), (double)up.getZ(), (double)up.getX() + 1.0, (double)up.getY() + 2.0, (double)up.getZ() + 1.0)).isEmpty()) {
                opposite.set((BlockPos)p);
                return true;
            }
            return !(b instanceof BlockLiquid) && !(b instanceof BlockAir);
        }, (b, p, ef) -> true);
    }
}


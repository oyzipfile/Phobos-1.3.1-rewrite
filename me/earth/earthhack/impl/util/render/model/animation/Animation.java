/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiAnimation
 *  jassimp.AiNodeAnim
 *  jassimp.AiScene
 *  jassimp.AiWrapperProvider
 *  org.joml.Matrix4f
 */
package me.earth.earthhack.impl.util.render.model.animation;

import jassimp.AiAnimation;
import jassimp.AiNodeAnim;
import jassimp.AiScene;
import jassimp.AiWrapperProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.util.render.model.AssimpJomlProvider;
import me.earth.earthhack.impl.util.render.model.animation.AssimpNodeData;
import me.earth.earthhack.impl.util.render.model.animation.Bone;
import me.earth.earthhack.impl.util.render.model.animation.BoneInfo;
import me.earth.earthhack.impl.util.render.model.animation.RiggedModel;
import me.earth.earthhack.impl.util.render.model.animation.SourceNode;
import org.joml.Matrix4f;

public class Animation
implements Nameable {
    private final String name;
    private double duration;
    private double ticksPerSecond;
    private AssimpNodeData root;
    private List<Bone> bones = new ArrayList<Bone>();
    private Map<String, BoneInfo> boneInfoMap = new HashMap<String, BoneInfo>();

    public Animation(AiScene scene, RiggedModel model, int index) {
        AiAnimation animation = (AiAnimation)scene.getAnimations().get(index);
        this.name = animation.getName();
        System.out.println("Animation: " + this.name);
        this.duration = animation.getDuration();
        this.ticksPerSecond = animation.getTicksPerSecond();
        this.root = new AssimpNodeData();
        this.handleChildren(this.root, (SourceNode)scene.getSceneRoot((AiWrapperProvider)new AssimpJomlProvider()));
        this.findMissingBones(animation, model);
    }

    private void handleChildren(AssimpNodeData data, SourceNode source) {
        data.setName(source.getName());
        data.setTransformation(source.getTransformation());
        for (SourceNode child : source.getChildren()) {
            AssimpNodeData newData = new AssimpNodeData();
            this.handleChildren(newData, child);
            data.getChildren().add(newData);
        }
    }

    private void findMissingBones(AiAnimation animation, RiggedModel model) {
        int size = animation.getNumChannels();
        Map<String, BoneInfo> infoMap = model.getBones();
        int boneCount = model.getBoneCount();
        for (int i = 0; i < size; ++i) {
            AiNodeAnim node = (AiNodeAnim)animation.getChannels().get(i);
            String name = node.getNodeName();
            if (!infoMap.containsKey(name)) {
                infoMap.put(name, new BoneInfo(boneCount, new Matrix4f()));
                if (++boneCount >= 100) break;
            }
            this.bones.add(new Bone(name, infoMap.get(name).getId(), node));
        }
        this.boneInfoMap = infoMap;
    }

    public Bone findBone(String name) {
        for (Bone bone : this.bones) {
            if (!bone.getName().equalsIgnoreCase(name)) continue;
            return bone;
        }
        return null;
    }

    public double getTPS() {
        return this.ticksPerSecond;
    }

    public double getLength() {
        return this.duration;
    }

    public AssimpNodeData getRoot() {
        return this.root;
    }

    public Map<String, BoneInfo> getBoneInfoMap() {
        return this.boneInfoMap;
    }

    @Override
    public String getName() {
        return this.name;
    }
}


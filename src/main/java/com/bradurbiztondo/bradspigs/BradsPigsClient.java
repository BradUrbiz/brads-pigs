package com.bradurbiztondo.bradspigs;

import com.bradurbiztondo.bradspigs.client.model.BaboyModel;
import com.bradurbiztondo.bradspigs.client.renderer.BaboyRenderer;
import com.bradurbiztondo.bradspigs.client.sound.BaboyWalkSoundInstance;
import com.bradurbiztondo.bradspigs.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.text.Text;

// baboy only r when on tame + have stock
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import net.minecraft.item.Items;

// using https://wiki.fabricmc.net/tutorial:keybinds
// yarn equivalents instead of mojang
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.lwjgl.glfw.GLFW;

import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import com.bradurbiztondo.bradspigs.network.ThrowTntPayload;
import com.bradurbiztondo.bradspigs.network.SummonMegaFartPayload;
import net.minecraft.util.math.Box;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BradsPigsClient implements ClientModInitializer {
    private static final double BABOY_SOUND_RANGE = 64.0;
    private static final Map<UUID, BaboyWalkSoundInstance> BABOY_WALK_SOUNDS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.BABOY, BaboyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BaboyModel.LAYER_LOCATION, BaboyModel::getTexturedModelData);

        KeyBinding throwTntKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.bradspigs.throw_tnt",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.bradspigs.controls"));

        KeyBinding megaFartKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.bradspigs.mega_fart",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.bradspigs.controls"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (throwTntKey.wasPressed()) {
                ClientPlayNetworking.send(new ThrowTntPayload());
            }

            while (megaFartKey.wasPressed()) {
                ClientPlayNetworking.send(new SummonMegaFartPayload());
            }

            if (client.world == null) return;
            Box searchBox = client.player.getBoundingBox().expand(BABOY_SOUND_RANGE);
            var baboys = client.world.getEntitiesByClass(BaboyEntity.class, searchBox, baboy -> true);
            Map<UUID, BaboyEntity> inRange = new HashMap<>();
            for (BaboyEntity baboy : baboys) {
                inRange.put(baboy.getUuid(), baboy);
                boolean isMoving = BaboyWalkSoundInstance.isBaboyMoving(baboy);
                boolean isPlaying = BABOY_WALK_SOUNDS.containsKey(baboy.getUuid());
                if (isMoving && !isPlaying) {
                    BaboyWalkSoundInstance instance = new BaboyWalkSoundInstance(baboy);
                    BABOY_WALK_SOUNDS.put(baboy.getUuid(), instance);
                    client.getSoundManager().play(instance);
                } else if (!isMoving && isPlaying) {
                    BABOY_WALK_SOUNDS.get(baboy.getUuid()).stop();
                    BABOY_WALK_SOUNDS.remove(baboy.getUuid());
                }
            }

            BABOY_WALK_SOUNDS.entrySet().removeIf(entry -> {
                BaboyEntity baboy = inRange.get(entry.getKey());
                if (baboy == null || baboy.isRemoved() || !BaboyWalkSoundInstance.isBaboyMoving(baboy)) {
                    entry.getValue().stop();
                    return true;
                }
                return false;
            });
        });
    }
}

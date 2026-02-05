package com.bradurbiztondo.bradspigs;

import com.bradurbiztondo.bradspigs.client.model.BaboyModel;
import com.bradurbiztondo.bradspigs.client.renderer.BaboyRenderer;
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

public class BradsPigsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.BABOY, BaboyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BaboyModel.LAYER_LOCATION, BaboyModel::getTexturedModelData);

        KeyBinding throwTntKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.bradspigs.throw_tnt",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.bradspigs.controls"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (throwTntKey.wasPressed()) {
                if (client.player.getVehicle() instanceof BaboyEntity baboy
                    && baboy.isTame()
                    && baboy.getBaboyHeldItem().isOf(Items.TNT)
                    && baboy.getBaboyHeldItem().getCount() > 0) {
                    client.player.sendMessage(Text.literal("Clicked R"), false);
                }

            }
        });
    }
}

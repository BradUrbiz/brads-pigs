package com.bradurbiztondo.bradspigs;
// practicing intellij comment
import net.fabricmc.api.ModInitializer;
import com.bradurbiztondo.bradspigs.registry.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import com.bradurbiztondo.bradspigs.registry.ModEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Items;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import com.bradurbiztondo.bradspigs.network.ThrowTntPayload;

public class BradsPigs implements ModInitializer {
	public static final String MOD_ID = "bradspigs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(ThrowTntPayload.ID, ThrowTntPayload.CODEC);
		ModEntities.init();
		ModItems.init();
		FabricDefaultAttributeRegistry.register(ModEntities.BABOY, BaboyEntity.createBaboyAttributes());

		ServerPlayNetworking.registerGlobalReceiver(ThrowTntPayload.ID,
				(payload, context) -> {
					context.server().execute(() -> {
						var player = context.player();
						if (player.getVehicle() instanceof BaboyEntity baboy
								&& baboy.isTame()
								&& baboy.getBaboyHeldItem().isOf(Items.TNT)
								&& baboy.getBaboyHeldItem().getCount() >= 1) {
							baboy.tryPrimeBaboyTnt(player);
						}
					});
				});
	}
}

package com.bradurbiztondo.bradspigs;
// practicing intellij comment
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import com.bradurbiztondo.bradspigs.registry.ModEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;


public class BradsPigs implements ModInitializer {
	public static final String MOD_ID = "bradspigs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEntities.init();
		FabricDefaultAttributeRegistry.register(ModEntities.BABOY, BaboyEntity.createBaboyAttributes());
	}
}

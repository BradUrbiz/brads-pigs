package com.bradurbiztondo.bradspigs.registry;

import com.bradurbiztondo.bradspigs.BradsPigs;
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public final class ModEntities {
    public static final EntityType<BaboyEntity> BABOY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(BradsPigs.MOD_ID, "baboy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BaboyEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0F, 2.0F))
                    .build()
    );

    private ModEntities() {}

    public static void init() {
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(PEAKS_SPAWN_BIOMES),
                SpawnGroup.CREATURE,
                BABOY,
                10,
                1,
                2
        );
    }

    private static final RegistryKey<Biome>[] PEAKS_SPAWN_BIOMES = new RegistryKey[] {
            BiomeKeys.JAGGED_PEAKS,
            BiomeKeys.STONY_PEAKS
    };
}

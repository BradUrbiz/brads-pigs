package com.bradurbiztondo.bradspigs.registry;

import com.bradurbiztondo.bradspigs.BradsPigs;
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModEntities {
    public static final EntityType<BaboyEntity> BABOY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(BradsPigs.MOD_ID, "baboy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BaboyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F, 0.9F))
                    .build()
    );

    private ModEntities() {}

    public static void init() {}
}
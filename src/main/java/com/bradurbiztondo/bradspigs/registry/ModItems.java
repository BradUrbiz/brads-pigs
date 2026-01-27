package com.bradurbiztondo.bradspigs.registry;

import com.bradurbiztondo.bradspigs.BradsPigs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {
    public static final Item BABOY_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            Identifier.of(BradsPigs.MOD_ID, "baboy_spawn_egg"),
            new SpawnEggItem(ModEntities.BABOY, 0xF2B6C8, 0x7A3E2F, new Item.Settings())
    );

    private ModItems() {}

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
                .register(entries -> entries.add(BABOY_SPAWN_EGG));
    }
}
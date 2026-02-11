package com.bradurbiztondo.bradspigs.registry;

import com.bradurbiztondo.bradspigs.BradsPigs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
    public static final SoundEvent BABOY_TNT = register("baboy_tnt");
    public static final SoundEvent BABOY_MEGA_FART = register("baboy_mega_fart");
    public static final SoundEvent BABOY_WALK = register("baboy_walk");

    private ModSounds() {}

    public static void init() {}

    private static SoundEvent register(String id) {
        Identifier identifier = Identifier.of(BradsPigs.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}

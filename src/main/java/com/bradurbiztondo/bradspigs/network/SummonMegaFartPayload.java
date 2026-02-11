package com.bradurbiztondo.bradspigs.network;

import net.minecraft.util.Identifier;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;

public record SummonMegaFartPayload() implements CustomPayload {
    public static final Id<SummonMegaFartPayload> ID =
            new Id<>(Identifier.of("bradspigs", "summon_mega_fart"));
    public static final PacketCodec<RegistryByteBuf, SummonMegaFartPayload> CODEC =
            PacketCodec.unit(new SummonMegaFartPayload());

    @Override
    public Id<SummonMegaFartPayload> getId() {
        return ID;
    }
}
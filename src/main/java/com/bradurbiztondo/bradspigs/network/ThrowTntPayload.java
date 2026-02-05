package com.bradurbiztondo.bradspigs.network;

import net.minecraft.util.Identifier;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;

public record ThrowTntPayload() implements CustomPayload {
    public static final Id<ThrowTntPayload> ID =
            new Id<>(Identifier.of("bradspigs", "throw_tnt"));
    public static final PacketCodec<RegistryByteBuf, ThrowTntPayload> CODEC =
            PacketCodec.unit(new ThrowTntPayload());

    @Override
    public Id<ThrowTntPayload> getId() {
        return ID;
    }
}

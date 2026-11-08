package com.advanced_enchants.event;

import com.advanced_enchants.AdvancedEnchantment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;

import static com.advanced_enchants.event.EnchantTrigRenderer.texts;

/**
 * 附魔触发提示的网络同步。
 *
 * @see com.advanced_enchants.event.EnchantTrigRenderer
 */
public class EnchantTrigNetWorking {
    // 注册附魔触发同步包。
    public static void init() {
        EnchantTrigS2Cpayload.init();
        ClientPlayNetworking.registerGlobalReceiver(EnchantTrigS2Cpayload.ID, (payload, context) -> context.client().execute(() -> {
            Text add = Text.translatable("chat.triggered").append(payload.data()).append(Text.translatable("enchantment.level." + payload.level()));
            texts.put(add, 50);
        }));
    }

    public static void sendPacket(ServerPlayerEntity player, Text data, int level) {
        ServerPlayNetworking.send(player, new EnchantTrigS2Cpayload(data, level));
    }
}

record EnchantTrigS2Cpayload(Text data, int level) implements CustomPayload {
    public static final CustomPayload.Id<EnchantTrigS2Cpayload> ID = new Id<>(Identifier.of(AdvancedEnchantment.MOD_ID, "enchant_trig_sync"));
    public static final PacketCodec<RegistryByteBuf, EnchantTrigS2Cpayload> CODEC = PacketCodec.tuple(
            TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC, EnchantTrigS2Cpayload::data,
            PacketCodecs.INTEGER, EnchantTrigS2Cpayload::level, EnchantTrigS2Cpayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void init() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
    }
}
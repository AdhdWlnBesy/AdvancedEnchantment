package com.advanced_enchants.event;

import com.advanced_enchants.AdvancedEnchantment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * 高级锻造台的网络同步。
 *
 * @see com.advanced_enchants.gui.SmithingScreenHandler
 */
public class SmithingNetWorking {
    public static volatile boolean SMITH_SUCCESS = false;
    public static volatile boolean SMITH_FAIL = false;
    public static volatile long SMITH_TICK = 0;

    // 注册锻造状态同步。
    public static void register() {
        SmithStatusS2CPayload.registerCodec();

        ClientPlayNetworking.registerGlobalReceiver(SmithStatusS2CPayload.PACKET_ID, (payload, context) -> context.client().execute(() -> {
            SMITH_SUCCESS = payload.success();
            SMITH_FAIL = !payload.success();
            SMITH_TICK = payload.tick();
        }));
    }

    // 服务端发包
    public static void sendSmithStatus(PlayerEntity player, boolean success, long tick) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new SmithStatusS2CPayload(success, tick));
        }
    }
}

record SmithStatusS2CPayload(boolean success, long tick) implements CustomPayload {
    public static final Identifier ID = Identifier.of(AdvancedEnchantment.MOD_ID, "smith_status");
    public static final CustomPayload.Id<SmithStatusS2CPayload> PACKET_ID = new CustomPayload.Id<>(ID);
    public static final PacketCodec<RegistryByteBuf, SmithStatusS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, SmithStatusS2CPayload::success,
            PacketCodecs.VAR_LONG, SmithStatusS2CPayload::tick,
            SmithStatusS2CPayload::new
    );

    public static void registerCodec() {
        PayloadTypeRegistry.playS2C().register(PACKET_ID, CODEC);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
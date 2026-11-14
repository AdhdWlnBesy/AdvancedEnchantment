package com.advanced_enchants.block.broadcast;

import com.advanced_enchants.block.DungeonBlock;
import com.advanced_enchants.register.ModAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * 地牢主控方块实体的文字渲染。
 *
 * @see DungeonBroadcastBlockEntity
 */
public class DungeonBroadcastBlockEntityRenderer implements BlockEntityRenderer<DungeonBroadcastBlockEntity> {
    public DungeonBroadcastBlockEntityRenderer(BlockEntityRendererFactory.Context ignoredCtx) {
    }

    @Override
    public void render(DungeonBroadcastBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ModAPI.drawTextCentered(Text.translatable("chat.broadcast.welcome", entity.getCachedState().get(DungeonBlock.DUNGEON_LEVEL)).formatted(Formatting.AQUA), textPos(entity, 2.5), matrices);
        if (!entity.lock) {
            if (!entity.isBooting) {
                ModAPI.drawTextCentered(Text.translatable("chat.broadcast.welcome1").formatted(Formatting.YELLOW), textPos(entity, 2), matrices);
            } else {
                ModAPI.drawTextCentered(Text.translatable("chat.broadcast.fight").formatted(Formatting.YELLOW), textPos(entity, 2), matrices);
            }
        } else {
            ModAPI.drawTextCentered(Text.translatable("chat.broadcast.cooling", 1200 - (entity.time / 20)), textPos(entity, 2), matrices);
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        VertexConsumer vc = mc.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getCutoutMipped());
        matrices.push();
        if (mc.world != null && entity.getWorld() != null) {
            mc.getBlockRenderManager().renderBlock(entity.getWorld().getBlockState(entity.getPos().down()), entity.getPos(), mc.world, matrices, vc, false, mc.world.random);
        }
        matrices.pop();
    }

    /**
     * 文字放在主控方块与地牢箱的中间（按两个方块的中心计算），拿不到箱子时退回主控方块中心上方。
     */
    private static Vec3d textPos(DungeonBroadcastBlockEntity entity, double y) {
        BlockPos origin = entity.getPos();
        BlockPos chest = entity.chestPos;
        if (chest == null
                || Math.abs(chest.getX() - origin.getX()) > 4
                || Math.abs(chest.getY() - origin.getY()) > 4
                || Math.abs(chest.getZ() - origin.getZ()) > 4) {
            return new Vec3d(0.5, y, 0.5);
        }
        return new Vec3d(
                (chest.getX() - origin.getX()) / 2.0 + 0.5,
                y + (chest.getY() - origin.getY()) / 2.0,
                (chest.getZ() - origin.getZ()) / 2.0 + 0.5
        );
    }
}

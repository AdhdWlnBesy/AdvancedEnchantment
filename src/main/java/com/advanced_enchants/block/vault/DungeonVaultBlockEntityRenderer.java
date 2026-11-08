package com.advanced_enchants.block.vault;

import com.advanced_enchants.register.ModAPI;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * 地牢宝库方块实体的渲染。
 *
 * @see com.advanced_enchants.block.vault.DungeonVaultBlockEntity
 */
public class DungeonVaultBlockEntityRenderer implements BlockEntityRenderer<DungeonVaultBlockEntity> {
    public DungeonVaultBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(DungeonVaultBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        BlockPos pos = entity.getPos().up();
        if (entity.state != 0 && entity.showItems != null && !entity.showItems.isEmpty()) {
            if (entity.getFacing() == Direction.EAST || entity.getFacing() == Direction.WEST) {
                ModAPI.showItem(entity.showItems.get(0), pos.north(2), world);
                ModAPI.showItem(entity.showItems.get(1), pos.north(), world);
                ModAPI.showItem(entity.showItems.get(2), pos, world);
                ModAPI.showItem(entity.showItems.get(3), pos.south(), world);
                ModAPI.showItem(entity.showItems.get(4), pos.south(2), world);
            } else {
                ModAPI.showItem(entity.showItems.get(0), pos.east(2), world);
                ModAPI.showItem(entity.showItems.get(1), pos.east(), world);
                ModAPI.showItem(entity.showItems.get(2), pos, world);
                ModAPI.showItem(entity.showItems.get(3), pos.west(), world);
                ModAPI.showItem(entity.showItems.get(4), pos.west(2), world);
            }
        }
    }
}

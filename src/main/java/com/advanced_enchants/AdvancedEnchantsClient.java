package com.advanced_enchants;

import com.advanced_enchants.block.broadcast.DungeonBroadcastBlockEntityRenderer;
import com.advanced_enchants.block.vault.DungeonVaultBlockEntityRenderer;
import com.advanced_enchants.event.EnchantTrigRenderer;
import com.advanced_enchants.gui.AnvilScreen;
import com.advanced_enchants.gui.GrindstoneScreen;
import com.advanced_enchants.gui.SmithingScreen;
import com.advanced_enchants.register.ModBlockEntityType;
import com.advanced_enchants.register.ModBlocks;
import com.advanced_enchants.register.ModScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

/**
 * 客户端入口，注册客户端渲染与事件。
 *
 * @see AdvancedEnchantment
 */
public class AdvancedEnchantsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //注册GUI
        HandledScreens.register(ModScreenHandler.SMITHING_TYPE, SmithingScreen::new);
        HandledScreens.register(ModScreenHandler.ANVIL_TYPE, AnvilScreen::new);
        HandledScreens.register(ModScreenHandler.GRINDSTONE_TYPE, GrindstoneScreen::new);
        //注册方块渲染
        BlockEntityRendererFactories.register(ModBlockEntityType.DUNGEON_VAULT, DungeonVaultBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntityType.DUNGEON_BROADCAST, DungeonBroadcastBlockEntityRenderer::new);
        //搭配.nonOpaque().blockVision(Blocks::never)使方块可以拥有透明图层
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DUNGEON_VAULT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DUNGEON_SPAWNER, RenderLayer.getCutout());
        EnchantTrigRenderer.mainEvent();
    }
}

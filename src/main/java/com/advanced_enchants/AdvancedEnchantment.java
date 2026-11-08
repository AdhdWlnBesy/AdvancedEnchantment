package com.advanced_enchants;

import com.advanced_enchants.datagen.AdvancementProvider;
import com.advanced_enchants.enchantment.EnchantmentHandler;
import com.advanced_enchants.event.*;
import com.advanced_enchants.register.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模组主入口，负责注册内容与初始化事件。
 *
 * @see com.advanced_enchants.register.ModItems
 */
public class AdvancedEnchantment implements ModInitializer {
    public static final String MOD_ID = "advanced_enchants";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger("Advanced Enchantments");
    public static final boolean BUILT = false; // 建筑时，打开将会停止地牢方块的tick运行方便建筑

    @Override
    public void onInitialize() {
        ModScreenHandler.loadScreenHandler();
        ModItems.loadItems();
        ModBlocks.loadBlocks();
        ModBlockEntityType.loadBlockEntityType();
        ModItemGroups.loadItemGroups();
        ModEnchantments.loadEnchantments();
        ModTags.loadTags();
        ModDataComponentTypes.loadComponentTypes();
        AdvancementProvider.loadCriteria();

        SmithingNetWorking.register();
        EnchantTrigNetWorking.init();
        BookToScroll.mainEvent();
        OpenCompendium.mainEvent();
        ForbidScroll.mainEvent();
        GiveCompendium.mainEvent();
        EnchantmentHandler.mainEvent();
        MobLevelupAdvancement.mainEffect();
        LOGGER.info("All events load successfully!");
    }
}
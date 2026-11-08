package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.gui.AnvilScreenHandler;
import com.advanced_enchants.gui.GrindstoneScreenHandler;
import com.advanced_enchants.gui.SmithingScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

/**
 * 界面处理器注册。
 *
 * @see com.advanced_enchants.gui.AnvilScreenHandler
 */
public class ModScreenHandler {
    public static final ScreenHandlerType<SmithingScreenHandler> SMITHING_TYPE = register("smithing", SmithingScreenHandler::new);
    public static final ScreenHandlerType<AnvilScreenHandler> ANVIL_TYPE = register("anvil", AnvilScreenHandler::new);
    public static final ScreenHandlerType<GrindstoneScreenHandler> GRINDSTONE_TYPE = register("grindstone", GrindstoneScreenHandler::new);

    // 只能注册一次。NeoForge(信雅互联) 的 MappedRegistry 是按"值"查重的，同一个 ScreenHandlerType 换两个 id
    // 注册第二次就会抛 Adding duplicate value 并把整个 mod 的初始化掐掉；Fabric 那边不查值重复，所以一直没暴露。
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String path, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(AdvancedEnchantment.MOD_ID, path),
                new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void loadScreenHandler() {
        AdvancedEnchantment.LOGGER.info("Loading ScreenHandler successfully!");
    }
}

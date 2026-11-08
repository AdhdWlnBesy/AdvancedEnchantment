package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

/**
 * 地牢结构键注册。
 *
 * @see WorldProvider
 */
public class ModStructures {
    public static final RegistryKey<Structure> DUNGEON1 = register("dungeon1");
    public static final RegistryKey<Structure> DUNGEON2 = register("dungeon2");
    public static final RegistryKey<Structure> DUNGEON3 = register("dungeon3");
    public static final RegistryKey<Structure> DUNGEON4 = register("dungeon4");

    private static RegistryKey<Structure> register(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of(AdvancedEnchantment.MOD_ID, id));
    }

    public static void loadStructures() {
        AdvancedEnchantment.LOGGER.info("Loading Structures successfully!");
    }
}

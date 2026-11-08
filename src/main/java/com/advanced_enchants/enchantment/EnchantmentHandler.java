package com.advanced_enchants.enchantment;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.enchantment.armor.*;
import com.advanced_enchants.enchantment.effect.EnchantmentEffect;
import com.advanced_enchants.enchantment.effect.PickaxeEnchantmentEffect;
import com.advanced_enchants.enchantment.other.Advancing;
import com.advanced_enchants.enchantment.other.ArmorThick;
import com.advanced_enchants.enchantment.pickaxe.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class EnchantmentHandler {
    private static final List<EnchantmentEffect> NORMAL_EFFECTS = Arrays.asList(
            new NightVision(), new Speed(), new RabbitJump(), new Bless(), new Gills(), new FireWardHighLevel(), new Advancing(), new BodyFort(), new ArmorThick()
    );
    private static final List<PickaxeEnchantmentEffect> PICKAXE_EFFECTS = Arrays.asList(
            new MiningExperience(), new QuickDigging(), new Magnet(), new Remake(), new Devour(), new WealthDigging(), new Chain()
    );
    private static final Map<UUID, Set<Class<?>>> AFFECTED = new HashMap<>();

    public static void mainEvent() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 5 == 0) {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    Set<Class<?>> ids = new HashSet<>();
                    for (EnchantmentEffect effect : NORMAL_EFFECTS) {
                        RegistryEntry<Enchantment> entry = effect.getEnchantment(server);
                        if (entry == null) continue;
                        int level = EnchantmentHelper.getEquipmentLevel(entry, player);
                        if (level > 0) {
                            ids.add(effect.getClass());
                            if (player.getRandom().nextFloat() < effect.getChance(level).getChance(level)) {
                                effect.apply(player, level);
                            }
                        } else if (AFFECTED.getOrDefault(player.getUuid(), Set.of()).contains(effect.getClass())) {
                            effect.notWarn(player);
                        }
                    }
                    AFFECTED.put(player.getUuid(), ids);
                }
            }
        });
        PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, blockEntity) -> {
            if (player.isCreative() || world.isClient()) return;
            for (PickaxeEnchantmentEffect effect : PICKAXE_EFFECTS) {
                RegistryEntry<Enchantment> entry = effect.getEnchantment(world.getServer());
                if (entry == null) continue;
                int level = EnchantmentHelper.getLevel(entry, player.getMainHandStack());
                if (level > 0) {
                    if (effect instanceof Magnet)
                        Objects.requireNonNull(world.getServer()).execute(() -> effect.apply(world, player, pos, state, blockEntity, level));
                    else if (player.getRandom().nextFloat() < effect.getChance(level).getChance(level)) {
                        effect.apply(world, player, pos, state, blockEntity, level);
                        effect.sendPacket((ServerPlayerEntity) player, entry, level);
                    }
                }
            }
        }));

        AdvancedEnchantment.LOGGER.info("Loading EnchantmentHandler successfully!");
    }
}
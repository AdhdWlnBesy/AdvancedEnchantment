package com.advanced_enchants.enchantment;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.enchantment.armor.*;
import com.advanced_enchants.enchantment.arrow.*;
import com.advanced_enchants.enchantment.effect.*;
import com.advanced_enchants.enchantment.fishing_rod.*;
import com.advanced_enchants.enchantment.other.*;
import com.advanced_enchants.enchantment.pickaxe.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class EnchantmentHandler {
    public static final List<EnchantmentEffect> NORMAL_EFFECTS = new ArrayList<>(List.of(new NightVision(), new Speed(),
            new RabbitJump(), new Bless(), new Gills(), new FireWardHighLevel(), new Advancing(), new BodyFort(), new ArmorThick()
    ));
    public static final List<PickaxeEnchantmentEffect> PICKAXE_EFFECTS = new ArrayList<>(List.of(new MiningExperience(),
            new QuickDigging(), new Magnet(), new Remake(), new Devour(), new WealthDigging(), new Chain()
    ));
    public static final List<ArmorHitEnchantmentEffect> ARMOR_HIT_EFFECTS = new ArrayList<>(List.of(
            new ZombieGuard(), new ApplyPoison(), new SkeletonGuard(), new BounceBurn(), new Recast(), new FireWard(), new WitherThorn(), new CreeperGuard(),
            new Block(), new Counterattack(), new Chill(), new Escape(), new ArrowWard(), new AxeWard(), new SwordWard(), new PiglinGuard(), new VindicatorGuard(),
            new BlazeGuard(), new MortalWard(), new VoidWard(), new Tired(), new GolemGuard()
    ));
    public static final List<BowEnchantmentEffect> BOW_EFFECTS = new ArrayList<>(List.of(
            new WitherArrow(), new Palsy(), new Rime(), new com.advanced_enchants.enchantment.arrow.ThunderCall(), new Bombard(), new PoisonArrow(),
            new Reforge(), new Laches(), new Finisher()
    ));
    public static final List<ShieldEnchantmentEffect> SHIELD_EFFECTS = new ArrayList<>(List.of(
            new WitherRebound(), new PoisonRebound(), new SlownessRebound(), new ShockRebound(), new Fortify()
    ));
    public static final List<FishingRodEnchantmentEffect> FISHING_ROD_EFFECTS = new ArrayList<>(List.of(
            new FireHook(), new WitherHook(), new PoisonHook(), new SharpHook()
    ));
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
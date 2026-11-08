package com.advanced_enchants.datagen;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.enchantment.EnchantmentTypes;
import com.advanced_enchants.enchantment.weapon.*;
import com.advanced_enchants.register.ModBlocks;
import com.advanced_enchants.register.ModEnchantments;
import com.advanced_enchants.register.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AttributeEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.entity.IgniteEnchantmentEffect;
import net.minecraft.enchantment.effect.entity.ReplaceDiskEnchantmentEffect;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;

import static com.advanced_enchants.enchantment.EnchantmentTypes.*;

/**
 * 附魔数据生成器。
 *
 * @see com.advanced_enchants.register.ModEnchantments
 */
public class EnchantmentProvider {
    private static Registerable<Enchantment> registerable;

    public static void bootstrap(Registerable<Enchantment> enchantmentRegisterable) {
        registerable = enchantmentRegisterable;
        normalEffectRegister(ModEnchantments.ZOMBIE_GUARD, ItemTags.LEG_ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.LEGS);
        normalEffectRegister(ModEnchantments.SKELETON_GUARD, ItemTags.CHEST_ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.CHEST);
        register(ModEnchantments.IGNITION, Enchantment.builder(getDefinition(ORDINARY, ItemTags.AXES, 2, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK, EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM,
                        new IgniteEnchantmentEffect(EnchantmentLevelBasedValue.linear(4.0F)),
                        DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create().isDirect(true))));
        normalEffectRegister(ModEnchantments.SMELTING, ItemTags.PICKAXES, 1, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.MINING_EXPERIENCE, ItemTags.PICKAXES, 3, AttributeModifierSlot.MAINHAND);
        weaponEffectRegister(ModEnchantments.FAMINE, ItemTags.AXES, 3, new Famine());
        normalEffectRegister(ModEnchantments.DEVOUR, ItemTags.MINING_ENCHANTABLE, 2, AttributeModifierSlot.MAINHAND);
        register(ModEnchantments.SHOVE, Enchantment.builder(getDefinition(ORDINARY, ItemTags.AXES, 2, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.KNOCKBACK, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1.0F))));
        weaponEffectRegister(ModEnchantments.FORCE_FIELD, ItemTags.SHARP_WEAPON_ENCHANTABLE, 2, new ForceField());
        normalEffectRegister(ModEnchantments.CREEPER_GUARD, ItemTags.LEG_ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.LEGS);
        weaponEffectRegister(ModEnchantments.SUSTAIN, ItemTags.SHARP_WEAPON_ENCHANTABLE, 2, new Sustain());
        register(ModEnchantments.LUCK, Enchantment.builder(getDefinition(ORDINARY, ItemTags.FISHING_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.FISHING_LUCK_BONUS, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(1.2f))));
        normalEffectRegister(ModEnchantments.GAMBLER, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.HEAVINESS, ItemTags.ARMOR_ENCHANTABLE, 2, AttributeModifierSlot.ARMOR);
        register(ModEnchantments.LAVA_WALKER, Enchantment.builder(getDefinition(ADVANCED, ItemTags.FOOT_ARMOR_ENCHANTABLE, 2, AttributeModifierSlot.FEET))
                .addEffect(EnchantmentEffectComponentTypes.LOCATION_CHANGED, new ReplaceDiskEnchantmentEffect(
                                new EnchantmentLevelBasedValue.Clamped(EnchantmentLevelBasedValue.linear(3.0F, 1.0F), 0.0F, 16.0F),
                                EnchantmentLevelBasedValue.constant(1.0F),
                                new Vec3i(0, -1, 0),
                                Optional.of(BlockPredicate.allOf(BlockPredicate.matchingBlockTag(new Vec3i(0, 1, 0), BlockTags.AIR),
                                        BlockPredicate.matchingBlocks(Blocks.LAVA), BlockPredicate.matchingFluids(Fluids.LAVA), BlockPredicate.unobstructed())),
                                BlockStateProvider.of(ModBlocks.PEBBLE), Optional.of(GameEvent.BLOCK_PLACE)),
                        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onGround(true)))));

        weaponEffectRegister(ModEnchantments.FROST, ItemTags.SHARP_WEAPON_ENCHANTABLE, 2, new Frost());
        normalEffectRegister(ModEnchantments.APPLY_POISON, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        addWeaponDamage(ModEnchantments.CREEPER_BANE, 3, ModTags.CREEPER, 0.25f);
        normalEffectRegister(ModEnchantments.QUICK_DIGGING, ItemTags.MINING_ENCHANTABLE, 4, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.BOUNCE_BURN, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        weaponEffectRegister(ModEnchantments.SWIFT_SWORD, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, new SwiftSword());
        normalEffectRegister(ModEnchantments.MAGNET, ItemTags.MINING_ENCHANTABLE, 1, AttributeModifierSlot.MAINHAND);
        weaponEffectRegister(ModEnchantments.NAUSEA, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, new Nausea());
        weaponEffectRegister(ModEnchantments.WITHER, ItemTags.SHARP_WEAPON_ENCHANTABLE, 2, new Wither());
        normalEffectRegister(ModEnchantments.FREEZE, ItemTags.SHARP_WEAPON_ENCHANTABLE, 2, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.RIME, ModTags.ENCHANTABLE_BOW, 3, AttributeModifierSlot.MAINHAND);
        weaponEffectRegister(ModEnchantments.REFORGE, ModTags.BOW_AND_WEAPON, 4, new Reforge());
        normalEffectRegister(ModEnchantments.THUNDER_CALL, ModTags.ENCHANTABLE_BOW, 3, AttributeModifierSlot.MAINHAND);
        weaponEffectRegister(ModEnchantments.MIGHT, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, new Might());
        normalEffectRegister(ModEnchantments.TIRED, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        register(ModEnchantments.POUND, Enchantment.builder(getDefinition(ADVANCED, ItemTags.CROSSBOW_ENCHANTABLE, 5, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.DAMAGE, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.5F)),
                        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_ATTACKER,
                                EntityPredicate.Builder.create().type(EntityTypeTags.ARROWS).build())));
        register(ModEnchantments.BLAZE, Enchantment.builder(getDefinition(ADVANCED, ItemTags.CROSSBOW_ENCHANTABLE, 1, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.PROJECTILE_SPAWNED, new IgniteEnchantmentEffect(EnchantmentLevelBasedValue.constant(100.0F))));
        register(ModEnchantments.CURVE_AXE, Enchantment.builder(getDefinition(ADVANCED, ItemTags.AXES, 3, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES, new AttributeEnchantmentEffect(
                        Identifier.of(AdvancedEnchantment.MOD_ID, "curve_axe_sweep"), EntityAttributes.PLAYER_SWEEPING_DAMAGE_RATIO,
                        new CurveAxeValue(), EntityAttributeModifier.Operation.ADD_VALUE)));
        normalEffectRegister(ModEnchantments.GILLS, ItemTags.HEAD_ARMOR_ENCHANTABLE, 1, AttributeModifierSlot.HEAD);
        normalEffectRegister(ModEnchantments.BOMBARD, ModTags.ENCHANTABLE_BOW, 3, AttributeModifierSlot.MAINHAND);
        weaponEffectRegister(ModEnchantments.RESTORE, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, new Restore());
        addWeaponDamage(ModEnchantments.SPIDER_BANE, 3, ModTags.SPIDER, 0.2f);
        normalEffectRegister(ModEnchantments.POISON_ARROW, ModTags.ENCHANTABLE_BOW, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.WITHER_HOOK, ItemTags.FISHING_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.POISON_HOOK, ItemTags.FISHING_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.FIRE_HOOK, ItemTags.FISHING_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.SHARP_HOOK, ItemTags.FISHING_ENCHANTABLE, 4, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.PIGLIN_GUARD, ItemTags.CHEST_ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.CHEST);
        normalEffectRegister(ModEnchantments.BLAZE_GUARD, ItemTags.HEAD_ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.HEAD);
        normalEffectRegister(ModEnchantments.VINDICATOR_GUARD, ItemTags.FOOT_ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.FEET);
        weaponEffectRegister(ModEnchantments.SAP_ESSENCE, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, new SapEssence());
        normalEffectRegister(ModEnchantments.CRITICAL, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.POISON_REBOUND, ModTags.ENCHANTABLE_SHIELD, 3, AttributeModifierSlot.HAND);
        normalEffectRegister(ModEnchantments.SLOWNESS_REBOUND, ModTags.ENCHANTABLE_SHIELD, 3, AttributeModifierSlot.HAND);

        normalEffectRegister(ModEnchantments.NIGHT_VISION, ItemTags.HEAD_ARMOR_ENCHANTABLE, 1, AttributeModifierSlot.HEAD);
        normalEffectRegister(ModEnchantments.WITHER_ARROW, ModTags.ENCHANTABLE_BOW, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.BLOCK, ItemTags.ARMOR_ENCHANTABLE, 4, AttributeModifierSlot.ARMOR);
        addWeaponDamage(ModEnchantments.ZOMBIE_SKELETON_BANE, 4, ModTags.ZOMBIE_SKELETON, 0.25f);
        register(ModEnchantments.LONG_BOW, Enchantment.builder(getDefinition(RARE, ModTags.ENCHANTABLE_BOW, 4, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.DAMAGE, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.2f)),
                        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.create().type(EntityType.PLAYER).build())));
        normalEffectRegister(ModEnchantments.RABBIT_JUMP, ItemTags.FOOT_ARMOR_ENCHANTABLE, 4, AttributeModifierSlot.ARMOR);
        weaponEffectRegister(ModEnchantments.BUSTER, ItemTags.AXES, 3, new Buster());
        normalEffectRegister(ModEnchantments.FIRE_WARD, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        weaponEffectRegister(ModEnchantments.BLIND, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, new Blind());
        weaponEffectRegister(ModEnchantments.SYPHILIS, ItemTags.SHARP_WEAPON_ENCHANTABLE, 3, new Syphilis());
        normalEffectRegister(ModEnchantments.WITHER_THORN, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.REMAKE, ItemTags.MINING_ENCHANTABLE, 4, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.CHAIN, ItemTags.MINING_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND);
        addWeaponDamage(ModEnchantments.NETHER_BANE, 4, ModTags.NETHER_MOB, 0.25f);
        addWeaponDamage(ModEnchantments.ENDER_BANE, 4, ModTags.ENDER_MOB, 0.25f);
        normalEffectRegister(ModEnchantments.CHILL, ItemTags.ARMOR_ENCHANTABLE, 2, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.LACHES, ModTags.ENCHANTABLE_BOW, 2, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.ESCAPE, ItemTags.ARMOR_ENCHANTABLE, 2, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.AXE_WARD, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.GOLEM_GUARD, ItemTags.ARMOR_ENCHANTABLE, 4, AttributeModifierSlot.ARMOR);
        register(ModEnchantments.BLAST_WARD, Enchantment.builder(getDefinition(RARE, ItemTags.ARMOR_ENCHANTABLE, 4, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.DAMAGE_PROTECTION, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(2.5F)),
                        DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                .tag(TagPredicate.expected(DamageTypeTags.IS_EXPLOSION)))));
        register(ModEnchantments.PIERCE_ARMOR, Enchantment.builder(getDefinition(RARE, ModTags.ENCHANTABLE_BOW, 4, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.ARMOR_EFFECTIVENESS, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(-0.15F))));
        weaponEffectRegister(ModEnchantments.SAP_VITAL, ItemTags.AXES, 4, new SapVital());
        normalEffectRegister(ModEnchantments.SHOCK_REBOUND, ModTags.ENCHANTABLE_SHIELD, 3, AttributeModifierSlot.HAND);
        normalEffectRegister(ModEnchantments.WITHER_REBOUND, ModTags.ENCHANTABLE_SHIELD, 3, AttributeModifierSlot.HAND);
        normalEffectRegister(ModEnchantments.FORTIFY, ModTags.ENCHANTABLE_SHIELD, 3, AttributeModifierSlot.HAND);
        normalEffectRegister(ModEnchantments.FINISHER, ModTags.ENCHANTABLE_BOW, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.MAGIC_WARD, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.MAINHAND);

        weaponEffectRegister(ModEnchantments.BLOODSUCKING, ItemTags.SHARP_WEAPON_ENCHANTABLE, 5, new BloodSucking());
        normalEffectRegister(ModEnchantments.SPEED, ItemTags.FOOT_ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.PALSY, ModTags.ENCHANTABLE_BOW, 3, AttributeModifierSlot.MAINHAND);
        weaponEffectRegister(ModEnchantments.VIGOROUSLY, ItemTags.SHARP_WEAPON_ENCHANTABLE, 4, new Vigorously());
        normalEffectRegister(ModEnchantments.BLESS, ItemTags.ARMOR_ENCHANTABLE, 2, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.RECAST, ItemTags.ARMOR_ENCHANTABLE, 5, AttributeModifierSlot.ARMOR);
        weaponEffectRegister(ModEnchantments.DEMON_POISON, ItemTags.AXES, 4, new DemonPoison());
        normalEffectRegister(ModEnchantments.WEALTH_DIGGING, ItemTags.PICKAXES, 3, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.COUNTERATTACK, ItemTags.ARMOR_ENCHANTABLE, 4, AttributeModifierSlot.ARMOR);
        register(ModEnchantments.MONSTER_SLAYER, Enchantment.builder(getDefinition(PRECIOUS, ModTags.ENCHANTABLE_BOW, 5, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.DAMAGE, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.3f)),
                        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.create().type(ModTags.MONSTER).build())));
        normalEffectRegister(ModEnchantments.ADVANCING, ModTags.ENCHANTABLE_ELYTRA, 3, AttributeModifierSlot.CHEST);
        normalEffectRegister(ModEnchantments.LEAP_STRIKE, ItemTags.SHARP_WEAPON_ENCHANTABLE, 4, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.MORTAL_WARD, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.VOID_WARD, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.SWORD_WARD, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        normalEffectRegister(ModEnchantments.ARROW_WARD, ItemTags.ARMOR_ENCHANTABLE, 3, AttributeModifierSlot.ARMOR);
        weaponEffectRegister(ModEnchantments.BANISH, ItemTags.SHARP_WEAPON_ENCHANTABLE, 4, new Banish());
        normalEffectRegister(ModEnchantments.ARROW_RAIN, ModTags.ENCHANTABLE_BOW, 4, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.COMBO, ItemTags.SHARP_WEAPON_ENCHANTABLE, 4, AttributeModifierSlot.MAINHAND);
        normalEffectRegister(ModEnchantments.BODY_FORT, ItemTags.ARMOR_ENCHANTABLE, 5, AttributeModifierSlot.ARMOR);
        register(ModEnchantments.PIERCE, Enchantment.builder(getDefinition(PRECIOUS, ItemTags.SHARP_WEAPON_ENCHANTABLE, 4, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.ARMOR_EFFECTIVENESS, new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(-0.12F))));
        normalEffectRegister(ModEnchantments.ARMOR_THICK, ModTags.ENCHANTABLE_ELYTRA, 3, AttributeModifierSlot.ARMOR);
        addWeaponDamage(ModEnchantments.MONSTER_KILLER, 5, ModTags.MONSTER, 0.4f);
        normalEffectRegister(ModEnchantments.SOUL_BIND, ItemTags.VANISHING_ENCHANTABLE, 1, AttributeModifierSlot.MAINHAND);
    }

    private static void register(RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registerable.register(key, builder.build(key.getValue()));
    }

    public static <T extends EnchantmentEntityEffect> void weaponEffectRegister(RegistryKey<Enchantment> key, TagKey<Item> tag, int maxLevel, T effect) {
        register(key, Enchantment.builder(getDefinition(ModEnchantments.getEnchantmentLevelType(key), tag, maxLevel, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM, effect));
    }

    public static void normalEffectRegister(RegistryKey<Enchantment> key, TagKey<Item> tag, int maxLevel, AttributeModifierSlot slot) {
        register(key, Enchantment.builder(getDefinition(ModEnchantments.getEnchantmentLevelType(key), tag, maxLevel, slot)));
    }

    public static void addWeaponDamage(RegistryKey<Enchantment> key, int maxLevel, TagKey<EntityType<?>> entityTag, float linear) {
        register(key, Enchantment.builder(
                        getDefinition(ModEnchantments.getEnchantmentLevelType(key), ItemTags.SHARP_WEAPON_ENCHANTABLE, maxLevel, AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.DAMAGE, new IncreaseByPercentEffect(EnchantmentLevelBasedValue.linear(linear)),
                        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.create().type(EntityTypePredicate.create(entityTag)))));
    }

    public static Enchantment.Definition getDefinition(EnchantmentTypes type, TagKey<Item> tag, int maxLevel, AttributeModifierSlot slot) {
        return Enchantment.definition(
                registerable.getRegistryLookup(RegistryKeys.ITEM).getOrThrow(tag),
                1, maxLevel,
                Enchantment.leveledCost(type.level, type.level + 2),
                Enchantment.leveledCost(12, 10),
                0, slot);
    }
}

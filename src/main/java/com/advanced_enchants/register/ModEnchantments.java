package com.advanced_enchants.register;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.enchantment.EnchantmentTypes;
import com.advanced_enchants.enchantment.armor.*;
import com.advanced_enchants.enchantment.arrow.*;
import com.advanced_enchants.enchantment.effect.BaseEnchantmentEffect;
import com.advanced_enchants.enchantment.fishing_rod.FireHook;
import com.advanced_enchants.enchantment.fishing_rod.PoisonHook;
import com.advanced_enchants.enchantment.fishing_rod.SharpHook;
import com.advanced_enchants.enchantment.fishing_rod.WitherHook;
import com.advanced_enchants.enchantment.other.*;
import com.advanced_enchants.enchantment.pickaxe.*;
import com.advanced_enchants.enchantment.weapon.*;
import com.advanced_enchants.enchantment.weapon.Reforge;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.*;

import static com.advanced_enchants.enchantment.EnchantmentTypes.*;

/**
 * 模组附魔注册与效果表。
 *
 * @see ModItems
 */
public class ModEnchantments {
    public static final List<RegistryKey<Enchantment>> ADVANCED_ENCHANTMENTS = new ArrayList<>();
    public static final List<RegistryKey<Enchantment>> ORDINARY_LIST = new ArrayList<>();
    public static final List<RegistryKey<Enchantment>> ADVANCED_LIST = new ArrayList<>();
    public static final List<RegistryKey<Enchantment>> RARE_LIST = new ArrayList<>();
    public static final List<RegistryKey<Enchantment>> PRECIOUS_LIST = new ArrayList<>();
    public static final Map<RegistryKey<Enchantment>, BaseEnchantmentEffect> EFFECTS = new HashMap<>();

    public static final RegistryKey<Enchantment> ZOMBIE_GUARD = registerEnchantments("zombie_guard", ORDINARY, new ZombieGuard());
    public static final RegistryKey<Enchantment> SKELETON_GUARD = registerEnchantments("skeleton_guard", ORDINARY, new SkeletonGuard());
    public static final RegistryKey<Enchantment> FAMINE = registerEnchantments("famine", ORDINARY, new Famine());
    public static final RegistryKey<Enchantment> SMELTING = registerEnchantments("smelting", ORDINARY, new Smelting());
    public static final RegistryKey<Enchantment> IGNITION = registerEnchantments("ignition", ORDINARY, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> MINING_EXPERIENCE = registerEnchantments("mining_experience", ORDINARY, new MiningExperience());
    public static final RegistryKey<Enchantment> DEVOUR = registerEnchantments("devour", ORDINARY, new Devour());
    public static final RegistryKey<Enchantment> SHOVE = registerEnchantments("shove", ORDINARY, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> FORCE_FIELD = registerEnchantments("force_field", ORDINARY, new ForceField());
    public static final RegistryKey<Enchantment> FREEZE = registerEnchantments("freeze", ORDINARY, new Freeze());
    public static final RegistryKey<Enchantment> CREEPER_GUARD = registerEnchantments("creeper_guard", ORDINARY, new CreeperGuard());
    public static final RegistryKey<Enchantment> SUSTAIN = registerEnchantments("sustain", ORDINARY, new Sustain());
    public static final RegistryKey<Enchantment> WITHER_HOOK = registerEnchantments("wither_hook", ORDINARY, new WitherHook());
    public static final RegistryKey<Enchantment> FIRE_HOOK = registerEnchantments("fire_hook", ORDINARY, new FireHook());
    public static final RegistryKey<Enchantment> LUCK = registerEnchantments("luck", ORDINARY, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> GAMBLER = registerEnchantments("gambler", ORDINARY, new Gambler());
    public static final RegistryKey<Enchantment> HEAVINESS = registerEnchantments("heaviness", ORDINARY, new Heaviness());
    public static final RegistryKey<Enchantment> LAVA_WALKER = registerEnchantments("lava_walker", ORDINARY, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> POISON_REBOUND = registerEnchantments("poison_rebound", ORDINARY, new PoisonRebound());
    public static final RegistryKey<Enchantment> SLOWNESS_REBOUND = registerEnchantments("slowness_rebound", ORDINARY, new SlownessRebound());
    public static final RegistryKey<Enchantment> FROST = registerEnchantments("frost", ADVANCED, new Frost());
    public static final RegistryKey<Enchantment> APPLY_POISON = registerEnchantments("apply_poison", ADVANCED, new ApplyPoison());
    public static final RegistryKey<Enchantment> CREEPER_BANE = registerEnchantments("creeper_bane", ADVANCED, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> QUICK_DIGGING = registerEnchantments("quick_digging", ADVANCED, new QuickDigging());
    public static final RegistryKey<Enchantment> BOUNCE_BURN = registerEnchantments("bounce_burn", ADVANCED, new BounceBurn());
    public static final RegistryKey<Enchantment> SWIFT_SWORD = registerEnchantments("swift_sword", ADVANCED, new SwiftSword());
    public static final RegistryKey<Enchantment> MAGNET = registerEnchantments("magnet", ADVANCED, new Magnet());
    public static final RegistryKey<Enchantment> NAUSEA = registerEnchantments("nausea", ADVANCED, new Nausea());
    public static final RegistryKey<Enchantment> WITHER = registerEnchantments("wither", ADVANCED, new Wither());
    public static final RegistryKey<Enchantment> RIME = registerEnchantments("rime", ADVANCED, new Rime());
    public static final RegistryKey<Enchantment> REFORGE = registerEnchantments("reforge", ADVANCED, new Reforge());
    public static final RegistryKey<Enchantment> GILLS = registerEnchantments("gills", ADVANCED, new Gills());
    public static final RegistryKey<Enchantment> BOMBARD = registerEnchantments("bombard", ADVANCED, new Bombard());
    public static final RegistryKey<Enchantment> RESTORE = registerEnchantments("restore", ADVANCED, new Restore());
    public static final RegistryKey<Enchantment> POISON_HOOK = registerEnchantments("poison_hook", ADVANCED, new PoisonHook());
    public static final RegistryKey<Enchantment> SPIDER_BANE = registerEnchantments("spider_bane", ADVANCED, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> POISON_ARROW = registerEnchantments("poison_arrow", ADVANCED, new PoisonArrow());
    public static final RegistryKey<Enchantment> THUNDER_CALL = registerEnchantments("thunder_call", ADVANCED, new ThunderCall());
    public static final RegistryKey<Enchantment> MIGHT = registerEnchantments("might", ADVANCED, new Might());
    public static final RegistryKey<Enchantment> TIRED = registerEnchantments("tired", ADVANCED, new Tired());
    public static final RegistryKey<Enchantment> BLAZE = registerEnchantments("blaze", ADVANCED, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> CURVE_AXE = registerEnchantments("curve_axe", ADVANCED, new CurveAxe());
    public static final RegistryKey<Enchantment> PIGLIN_GUARD = registerEnchantments("piglin_guard", ADVANCED, new PiglinGuard());
    public static final RegistryKey<Enchantment> BLAZE_GUARD = registerEnchantments("blaze_guard", ADVANCED, new BlazeGuard());
    public static final RegistryKey<Enchantment> VINDICATOR_GUARD = registerEnchantments("vindicator_guard", ADVANCED, new VindicatorGuard());
    public static final RegistryKey<Enchantment> POUND = registerEnchantments("pound", ADVANCED, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> SAP_ESSENCE = registerEnchantments("sap_essence", ADVANCED, new SapEssence());
    public static final RegistryKey<Enchantment> CRITICAL = registerEnchantments("critical", ADVANCED, new Critical());
    public static final RegistryKey<Enchantment> WITHER_REBOUND = registerEnchantments("wither_rebound", ADVANCED, new WitherRebound());
    public static final RegistryKey<Enchantment> FINISHER = registerEnchantments("finisher", ADVANCED, new Finisher());
    public static final RegistryKey<Enchantment> NIGHT_VISION = registerEnchantments("night_vision", RARE, new NightVision());
    public static final RegistryKey<Enchantment> WITHER_ARROW = registerEnchantments("wither_arrow", RARE, new WitherArrow());
    public static final RegistryKey<Enchantment> BLOCK = registerEnchantments("block", RARE, new Block());
    public static final RegistryKey<Enchantment> LONG_BOW = registerEnchantments("long_bow", RARE, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> ZOMBIE_SKELETON_BANE = registerEnchantments("zomb_skel_bane", RARE, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> RABBIT_JUMP = registerEnchantments("rabbit_jump", RARE, new RabbitJump());
    public static final RegistryKey<Enchantment> BUSTER = registerEnchantments("buster", RARE, new Buster());
    public static final RegistryKey<Enchantment> FIRE_WARD = registerEnchantments("fire_ward", RARE, new FireWard());
    public static final RegistryKey<Enchantment> BLIND = registerEnchantments("blind", RARE, new Blind());
    public static final RegistryKey<Enchantment> SYPHILIS = registerEnchantments("syphilis", RARE, new Syphilis());
    public static final RegistryKey<Enchantment> WITHER_THORN = registerEnchantments("wither_thorn", RARE, new WitherThorn());
    public static final RegistryKey<Enchantment> REMAKE = registerEnchantments("remake", RARE, new Remake());
    public static final RegistryKey<Enchantment> CHAIN = registerEnchantments("chain", RARE, new Chain());
    public static final RegistryKey<Enchantment> ENDER_BANE = registerEnchantments("ender_bane", RARE, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> NETHER_BANE = registerEnchantments("nether_bane", RARE, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> CHILL = registerEnchantments("chill", RARE, new Chill());
    public static final RegistryKey<Enchantment> LACHES = registerEnchantments("laches", RARE, new Laches());
    public static final RegistryKey<Enchantment> ESCAPE = registerEnchantments("escape", RARE, new Escape());
    public static final RegistryKey<Enchantment> SHARP_HOOK = registerEnchantments("sharp_hook", RARE, new SharpHook());
    public static final RegistryKey<Enchantment> AXE_WARD = registerEnchantments("axe_ward", RARE, new AxeWard());
    public static final RegistryKey<Enchantment> GOLEM_GUARD = registerEnchantments("golem_guard", RARE, new GolemGuard());
    public static final RegistryKey<Enchantment> PIERCE_ARMOR = registerEnchantments("pierce_armor", RARE, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> BLAST_WARD = registerEnchantments("blast_ward", RARE, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> SAP_VITAL = registerEnchantments("sap_vital", RARE, new SapVital());
    public static final RegistryKey<Enchantment> LEAP_STRIKE = registerEnchantments("leap_strike", RARE, new LeapStrike());
    public static final RegistryKey<Enchantment> VOID_WARD = registerEnchantments("mortal_warc", RARE, new VoidWard());
    public static final RegistryKey<Enchantment> SHOCK_REBOUND = registerEnchantments("shock_rebound", RARE, new ShockRebound());
    public static final RegistryKey<Enchantment> FORTIFY = registerEnchantments("fortify", RARE, new Fortify());
    public static final RegistryKey<Enchantment> MAGIC_WARD = registerEnchantments("magic_ward", RARE, new MagicWard());
    public static final RegistryKey<Enchantment> BLOODSUCKING = registerEnchantments("blood_sucking", PRECIOUS, new BloodSucking());
    public static final RegistryKey<Enchantment> SPEED = registerEnchantments("speed", PRECIOUS, new Speed());
    public static final RegistryKey<Enchantment> PALSY = registerEnchantments("palsy", PRECIOUS, new Palsy());
    public static final RegistryKey<Enchantment> VIGOROUSLY = registerEnchantments("vigorously", PRECIOUS, new Vigorously());
    public static final RegistryKey<Enchantment> BLESS = registerEnchantments("bless", PRECIOUS, new Bless());
    public static final RegistryKey<Enchantment> RECAST = registerEnchantments("recast", PRECIOUS, new Recast());
    public static final RegistryKey<Enchantment> DEMON_POISON = registerEnchantments("demonic_poison", PRECIOUS, new DemonPoison());
    public static final RegistryKey<Enchantment> WEALTH_DIGGING = registerEnchantments("wealth_digging", PRECIOUS, new WealthDigging());
    public static final RegistryKey<Enchantment> COUNTERATTACK = registerEnchantments("counterattack", PRECIOUS, new Counterattack());
    public static final RegistryKey<Enchantment> MONSTER_SLAYER = registerEnchantments("monster_slayer", PRECIOUS, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> ADVANCING = registerEnchantments("advancing", PRECIOUS, new Advancing());
    public static final RegistryKey<Enchantment> SWORD_WARD = registerEnchantments("sword_ward", PRECIOUS, new SwordWard());
    public static final RegistryKey<Enchantment> ARROW_WARD = registerEnchantments("arrow_ward", PRECIOUS, new ArrowWard());
    public static final RegistryKey<Enchantment> BANISH = registerEnchantments("banish", PRECIOUS, new Banish());
    public static final RegistryKey<Enchantment> ARROW_RAIN = registerEnchantments("arrow_rain", PRECIOUS, new ArrowRain());
    public static final RegistryKey<Enchantment> COMBO = registerEnchantments("combo", PRECIOUS, new Combo());
    public static final RegistryKey<Enchantment> BODY_FORT = registerEnchantments("body_fort", PRECIOUS, new BodyFort());
    public static final RegistryKey<Enchantment> PIERCE = registerEnchantments("pierce", PRECIOUS, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> ARMOR_THICK = registerEnchantments("armor_thick", PRECIOUS, new ArmorThick());
    public static final RegistryKey<Enchantment> MONSTER_KILLER = registerEnchantments("monster_killer", PRECIOUS, BaseEnchantmentEffect.empty());
    public static final RegistryKey<Enchantment> MORTAL_WARD = registerEnchantments("mortal_ward", PRECIOUS, new MortalWard());
    public static final RegistryKey<Enchantment> SOUL_BIND = registerEnchantments("soul_bind", NULL, new SoulBind());

    public static RegistryKey<Enchantment> registerEnchantments(String id, EnchantmentTypes type, BaseEnchantmentEffect effect) {
        RegistryKey<Enchantment> key = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(AdvancedEnchantment.MOD_ID, id));
        switch (type) {
            case ORDINARY -> ORDINARY_LIST.add(key);
            case ADVANCED -> ADVANCED_LIST.add(key);
            case RARE -> RARE_LIST.add(key);
            case PRECIOUS -> PRECIOUS_LIST.add(key);
        }
        if (type != NULL) {
            ADVANCED_ENCHANTMENTS.add(key);
        }
        EFFECTS.put(key, effect);
        return key;
    }

    public static EnchantmentTypes getEnchantmentLevelType(RegistryKey<Enchantment> key) {
        if (ORDINARY_LIST.contains(key)) return ORDINARY;
        else if (ADVANCED_LIST.contains(key)) return ADVANCED;
        else if (RARE_LIST.contains(key)) return RARE;
        else if (PRECIOUS_LIST.contains(key)) return PRECIOUS;
        else return NULL;
    }

    public static int getAdvancedEnchantmentsCount(Set<RegistryEntry<Enchantment>> enchantments) {
        List<RegistryEntry<Enchantment>> entries = new ArrayList<>(enchantments);
        entries.removeIf(enchantment -> !ModEnchantments.ADVANCED_ENCHANTMENTS.contains(enchantment.getKey().orElse(null)));
        return entries.size();
    }

    public static BaseEnchantmentEffect.Chance getChance(RegistryKey<Enchantment> key, int level) {
        if (!ADVANCED_ENCHANTMENTS.contains(key)) return BaseEnchantmentEffect.empty().getChance(level);
        return EFFECTS.get(key).getChance(level);
    }

    private static <T extends EnchantmentEntityEffect> void attackRegister(String id, MapCodec<T> codec) {
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(AdvancedEnchantment.MOD_ID, id), codec);
    }

    public static void loadEnchantments() {
        attackRegister("frost", Frost.CODEC);
        attackRegister("bloodsucking", BloodSucking.CODEC);
        attackRegister("swiftsword", SwiftSword.CODEC);
        attackRegister("vigorously", Vigorously.CODEC);
        attackRegister("buster", Buster.CODEC);
        attackRegister("nausea", Nausea.CODEC);
        attackRegister("blind", Blind.CODEC);
        attackRegister("syphilis", Syphilis.CODEC);
        attackRegister("famine", Famine.CODEC);
        attackRegister("wither", Wither.CODEC);
        attackRegister("demon_poison", DemonPoison.CODEC);
        attackRegister("force_field", ForceField.CODEC);
        attackRegister("reforge", Reforge.CODEC);
        attackRegister("sustain", Sustain.CODEC);
        attackRegister("might", Might.CODEC);
        attackRegister("restore", Restore.CODEC);
        attackRegister("banish", Banish.CODEC);
        attackRegister("sap_essence", SapEssence.CODEC);
        attackRegister("sap_vital", SapVital.CODEC);
        Registry.register(Registries.ENCHANTMENT_LEVEL_BASED_VALUE_TYPE, Identifier.of(AdvancedEnchantment.MOD_ID, "curve_axe"),
                com.advanced_enchants.enchantment.weapon.CurveAxeValue.CODEC);
        Registry.register(Registries.ENCHANTMENT_VALUE_EFFECT_TYPE,
                Identifier.of(AdvancedEnchantment.MOD_ID, "increase_by_percent_effect"), IncreaseByPercentEffect.CODEC);
        AdvancedEnchantment.LOGGER.info("Loading {}+{}+{}+{} Enchantments successfully!",
                ORDINARY_LIST.size(), ADVANCED_LIST.size(), RARE_LIST.size(), PRECIOUS_LIST.size());
    }
}
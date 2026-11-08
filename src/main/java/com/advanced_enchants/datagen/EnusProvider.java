package com.advanced_enchants.datagen;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.register.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * 英文语言文件生成器。
 *
 * @see ZhcnProvider
 */
public class EnusProvider extends FabricLanguageProvider {
    TranslationBuilder transBuilder;

    public EnusProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder transBuilder) {
        this.transBuilder = transBuilder;
        transBuilder.add(ModItems.ADVANCED_ENCHANTS_COMPENDIUM, "Advanced enchants compendium");
        transBuilder.add(ModItems.ORDINARY_ENCHANTS_BOOK, "Ordinary enchants book");
        transBuilder.add(ModItems.ADVANCED_ENCHANTS_BOOK, "Advanced enchants book");
        transBuilder.add(ModItems.RARE_ENCHANTS_BOOK, "Rare enchants book");
        transBuilder.add(ModItems.PRECIOUS_ENCHANTS_BOOK, "Precious enchants book");
        transBuilder.add(ModItems.ORDINARY_ENCHANTS_SCROLL, "Ordinary enchants scroll");
        transBuilder.add(ModItems.ADVANCED_ENCHANTS_SCROLL, "Advanced enchants scroll");
        transBuilder.add(ModItems.RARE_ENCHANTS_SCROLL, "Rare enchants scroll");
        transBuilder.add(ModItems.PRECIOUS_ENCHANTS_SCROLL, "Precious enchants scroll");
        transBuilder.add(ModItems.EMPTY_SCROLL, "Empty scroll");
        transBuilder.add(ModItems.ENCHANTED_SCROLL, "Enchanted scroll");
        transBuilder.add(ModItems.ORDINARY_LEVELUP_BALL, "Ordinary levelup ball");
        transBuilder.add(ModItems.ADVANCED_LEVELUP_BALL, "Advanced levelup ball");
        transBuilder.add(ModItems.RARE_LEVELUP_BALL, "Rare levelup ball");
        transBuilder.add(ModItems.PRECIOUS_LEVELUP_BALL, "Precious levelup ball");
        transBuilder.add(ModItems.LEVEL1_SLOT_BUILDER, "Level 1 slot builder");
        transBuilder.add(ModItems.LEVEL2_SLOT_BUILDER, "Level 2 slot builder");
        transBuilder.add(ModItems.LEVEL3_SLOT_BUILDER, "Level 3 slot builder");
        transBuilder.add(ModItems.DUNGEON1_KEY, "Dungeon level 1 key");
        transBuilder.add(ModItems.DUNGEON2_KEY, "Dungeon level 2 key");
        transBuilder.add(ModItems.DUNGEON3_KEY, "Dungeon level 3 key");
        transBuilder.add(ModItems.DUNGEON4_KEY, "Dungeon level 4 key");
        transBuilder.add(ModItems.DUNGEON5_KEY, "Dungeon level 5 key");
        transBuilder.add("item.advanced_slot", "§l§6Remaining advanced enchantments slots:§c%s");

        transBuilder.add(ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE, "Advanced smithing table");
        transBuilder.add(ModBlocks.ADVANCED_ENCHANTS_ANVIL, "Advanced anvil");
        transBuilder.add(ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE, "Advanced grindstone");
        transBuilder.add(ModBlocks.DUNGEON_VAULT, "Dungeon vault");
        transBuilder.add(ModBlocks.DUNGEON_SPAWNER, "Dungeon spawner");
        transBuilder.add(ModBlocks.DUNGEON_BROADCAST, "Dungeon master control");
        transBuilder.add(ModBlocks.PEBBLE, "pebble");
        transBuilder.add(ModItemGroups.ADVANCED_ENCHANTS_GROUPS, "Advanced enchants item groups");
        transBuilder.add("gui.advanced_enchants_anvil.text", "Upgrade Item");
        transBuilder.add("gui.advanced_enchants_grindstone.text", "Transfer and Reset");
        transBuilder.add("gui.advanced_enchants_smithing.text", "Enchant Item");

        transBuilder.add("gui.compendium.page", "Page %s");
        transBuilder.add("gui.compendium.page1.line1", "  ADVANCED ENCHANTS");
        transBuilder.add("gui.compendium.page1.line2", "Version " + AdvancedEnchantment.VERSION);
        transBuilder.add("gui.compendium.page1.line3", "Author ADHD_WLN");
        transBuilder.add("gui.compendium.page1.introduction1", "This book is mod complete");
        transBuilder.add("gui.compendium.page1.introduction2", "compendium. This mod adds");
        transBuilder.add("gui.compendium.page1.introduction3", "lots of powerful enchants");
        transBuilder.add("gui.compendium.page1.introduction4", "Read here to learn all mod");
        transBuilder.add("gui.compendium.page1.introduction5", "details.Learn advanced ");
        transBuilder.add("gui.compendium.page1.introduction6", "enchant basic rules.Understand");
        transBuilder.add("gui.compendium.page1.introduction7", "how all enchants function");
        transBuilder.add("gui.compendium.page1.contents", "CONTENTS");
        transBuilder.add("gui.compendium.page1.contents1", "1. Basic Intro");
        transBuilder.add("gui.compendium.page1.contents2", "2. Mechanisms");
        transBuilder.add("gui.compendium.page1.contents3", "3. Usage Guide");
        transBuilder.add("gui.compendium.page1.contents4", "(1) Smithing Table");
        transBuilder.add("gui.compendium.page1.contents5", "(2) Anvil");
        transBuilder.add("gui.compendium.page1.contents6", "(3) Grindstone");
        transBuilder.add("gui.compendium.page1.contents7", "4. Enchant List");
        transBuilder.add("gui.compendium.page1.contents8", "(1) Ordinary Tier");
        transBuilder.add("gui.compendium.page1.contents9", "(2) Advanced Tier");
        transBuilder.add("gui.compendium.page1.contents10", "(3) Rare Tier");
        transBuilder.add("gui.compendium.page1.contents11", "(4) Precious Tier");

        transBuilder.add("gui.compendium.page2.introduction1", "§aAdvanced Enchant§r is unique");
        transBuilder.add("gui.compendium.page2.introduction2", "special enchant.Its power beats");
        transBuilder.add("gui.compendium.page2.introduction3", "all vanilla enchantments.Its");
        transBuilder.add("gui.compendium.page2.introduction4", "obtain rules are totally different");
        transBuilder.add("gui.compendium.page2.introduction5", "Can't acquire from vanilla Enchant");
        transBuilder.add("gui.compendium.page2.introduction6", "Table No enchant books without");
        transBuilder.add("gui.compendium.page2.introduction7", "game cheats.Enchant names auto");
        transBuilder.add("gui.compendium.page2.introduction8", "bold with color code §cScroll");
        transBuilder.add("gui.compendium.page2.introduction9", "is survival's only enchant carrier");
        transBuilder.add("gui.compendium.page2.introduction10", "Scroll has four independent power");
        transBuilder.add("gui.compendium.page2.introduction11", "tiers §aOrdinary §6Advanced §bRare");
        transBuilder.add("gui.compendium.page2.introduction12", "§5Precious Higher tier brings ");
        transBuilder.add("gui.compendium.page2.introduction13", "stronger enchant effects Higher");
        transBuilder.add("gui.compendium.page2.introduction14", "tier also harder to be collected");
        transBuilder.add("gui.compendium.page2.introduction15", "Four tier upgrade orbs exist in");
        transBuilder.add("gui.compendium.page2.introduction16", "game Orbs can upgrade your scroll");
        transBuilder.add("gui.compendium.page2.introduction17", "tier rank Advanced enchants have");
        transBuilder.add("gui.compendium.page2.introduction18", "slot capacity cap Max enchant");
        transBuilder.add("gui.compendium.page2.introduction19", "slots differ per equipment Use");
        transBuilder.add("gui.compendium.page2.introduction20", "§6Slot Builder§r to add extra slots");
        transBuilder.add("gui.compendium.page2.introduction21", "Three grades, each adds one new");
        transBuilder.add("gui.compendium.page2.introduction22", "slot Slot Builder rarely generates");
        transBuilder.add("gui.compendium.page2.introduction23", "naturally Plan your enchant");
        transBuilder.add("gui.compendium.page2.introduction24", "combinations carefully");

        transBuilder.add("gui.compendium.page3.introduction1", "Battle enchants grant status");
        transBuilder.add("gui.compendium.page3.introduction2", "buff or debuff §aStatus§r to user");
        transBuilder.add("gui.compendium.page3.introduction3", "or attacking mobs Permanent buffs");
        transBuilder.add("gui.compendium.page3.introduction4", "once you equip gear Debuff enemies");
        transBuilder.add("gui.compendium.page3.introduction5", "that strike your armor Gain self");
        transBuilder.add("gui.compendium.page3.introduction6", " buffs while attacking foes");
        transBuilder.add("gui.compendium.page3.introduction7", "Utility enchant:auto ore smelting");
        transBuilder.add("gui.compendium.page3.introduction8", "Mined ores convert directly into");
        transBuilder.add("gui.compendium.page3.introduction9", "ingots Mob strength grows with");
        transBuilder.add("gui.compendium.page3.introduction10", "playtime passing §cMonsters get");
        transBuilder.add("gui.compendium.page3.introduction11", "stronger as time goes by§r Works");
        transBuilder.add("gui.compendium.page3.introduction12", "well with other mod weapons ");
        transBuilder.add("gui.compendium.page3.introduction13", "Natural structure: §6Dungeon");
        transBuilder.add("gui.compendium.page3.introduction14", "spawns underground Main way to");
        transBuilder.add("gui.compendium.page3.introduction15", "get advanced enchant scrolls");
        transBuilder.add("gui.compendium.page3.introduction16", "Clear mob waves to finish dungeon");
        transBuilder.add("gui.compendium.page3.introduction17", "challenge High-tier dungeons spawn");
        transBuilder.add("gui.compendium.page3.introduction18", "powerful mobs Advanced enchants");
        transBuilder.add("gui.compendium.page3.introduction19", "are necessary to survive");

        transBuilder.add("gui.compendium.page4.title", "1. Smithing Table");
        transBuilder.add("gui.compendium.page4.introduction1", "Use this recipe to craft a §6Advanced");
        transBuilder.add("gui.compendium.page4.introduction2", "§6Enchant Smithing Table§r, the only");
        transBuilder.add("gui.compendium.page4.introduction3", "way to apply Advanced Enchants to");
        transBuilder.add("gui.compendium.page4.introduction4", "items. It has three input slots:");
        transBuilder.add("gui.compendium.page4.introduction5", "Slot 1: the equipment to enchant,");
        transBuilder.add("gui.compendium.page4.introduction6", "Slot 2: an enchant scroll, Slot 3:");
        transBuilder.add("gui.compendium.page4.introduction7", "minerals. Take out the enchanted");
        transBuilder.add("gui.compendium.page4.introduction8", "item. The mineral type depends on");
        transBuilder.add("gui.compendium.page4.introduction9", "the scroll tier: Ordinary = iron,");
        transBuilder.add("gui.compendium.page4.introduction10", "Advanced = gold, Rare = emerald,");
        transBuilder.add("gui.compendium.page4.introduction11", "Precious = diamond. The amount");
        transBuilder.add("gui.compendium.page4.introduction12", "depends on the scroll's enchant");
        transBuilder.add("gui.compendium.page4.introduction13", "level. Also, taking out has a 35%");
        transBuilder.add("gui.compendium.page4.introduction14", "chance to fail. On failure, the");
        transBuilder.add("gui.compendium.page4.introduction15", "scroll and minerals are consumed,");
        transBuilder.add("gui.compendium.page4.introduction16", "but no finished item is given.");

        transBuilder.add("gui.compendium.page5.title", "2. Anvil");
        transBuilder.add("gui.compendium.page5.introduction1", "Use this recipe to craft a §6Advanced");
        transBuilder.add("gui.compendium.page5.introduction2", "§6Enchant Anvil§r. It can upgrade");
        transBuilder.add("gui.compendium.page5.introduction3", "Advanced Enchant levels and also the");
        transBuilder.add("gui.compendium.page5.introduction4", "enchant slots of items. It has three");
        transBuilder.add("gui.compendium.page5.introduction5", "input slots: Slot 1, the equipment");
        transBuilder.add("gui.compendium.page5.introduction6", "to upgrade; Slot 2, the upgrade");
        transBuilder.add("gui.compendium.page5.introduction7", "materials. Then take out the finished");
        transBuilder.add("gui.compendium.page5.introduction8", "item. Forging never fails here. To");
        transBuilder.add("gui.compendium.page5.introduction9", "upgrade an enchant's level, you need");
        transBuilder.add("gui.compendium.page5.introduction10", "the levelup ball of the matching");
        transBuilder.add("gui.compendium.page5.introduction11", "tier. Non-Advanced enchants can be");
        transBuilder.add("gui.compendium.page5.introduction12", "upgraded with ender pearls. If a tool");
        transBuilder.add("gui.compendium.page5.introduction13", "lacks enough Advanced Enchant slots,");
        transBuilder.add("gui.compendium.page5.introduction14", "use a Slot Builder to add slots. Each");
        transBuilder.add("gui.compendium.page5.introduction15", "level of the Slot Builder adds one");
        transBuilder.add("gui.compendium.page5.introduction16", "slot.");

        transBuilder.add("gui.compendium.page6.title", "3. Grindstone");
        transBuilder.add("gui.compendium.page6.introduction1", "Use this recipe to craft a §6Advanced");
        transBuilder.add("gui.compendium.page6.introduction2", "§6Enchant Grindstone§r. It can");
        transBuilder.add("gui.compendium.page6.introduction3", "transfer the enchant of an item into");
        transBuilder.add("gui.compendium.page6.introduction4", "a scroll, or reset useless enchant");
        transBuilder.add("gui.compendium.page6.introduction5", "scrolls. It has two input slots. To");
        transBuilder.add("gui.compendium.page6.introduction6", "transfer an enchant, put the item");
        transBuilder.add("gui.compendium.page6.introduction7", "in Slot 1 and an empty scroll in");
        transBuilder.add("gui.compendium.page6.introduction8", "Slot 2, then take out the enchant");
        transBuilder.add("gui.compendium.page6.introduction9", "scroll. Use the Advanced Enchant");
        transBuilder.add("gui.compendium.page6.introduction10", "Smithing Table to enchant items with");
        transBuilder.add("gui.compendium.page6.introduction11", "it, and the scroll's success chance");
        transBuilder.add("gui.compendium.page6.introduction12", "rises from 65% to 85%. To reset an");
        transBuilder.add("gui.compendium.page6.introduction13", "enchant, put the scroll to reset in");
        transBuilder.add("gui.compendium.page6.introduction14", "Slot 1 and minerals in Slot 2. It");
        transBuilder.add("gui.compendium.page6.introduction15", "turns into the matching enchant book.");
        transBuilder.add("gui.compendium.page6.introduction16", "Minerals follow the Advanced Enchant");
        transBuilder.add("gui.compendium.page6.introduction17", "Smithing Table rules. The amount is");
        transBuilder.add("gui.compendium.page6.introduction18", "(max enchant level - enchant level)");
        transBuilder.add("gui.compendium.page6.introduction19", "* 3. Experience cost is 66% less");
        transBuilder.add("gui.compendium.page6.introduction20", "than the mineral cost.");

        transBuilder.add("gui.compendium.enchants_list.max_level", "Max Lv:%s");
        transBuilder.add("gui.compendium.enchants_list.supported_items", "Items:");
        transBuilder.add("gui.compendium.enchants_list.chance", "Chance:");
        transBuilder.add("gui.compendium.enchants_list.linear", " pre level");

        this.addEnchantment(ModEnchantments.ZOMBIE_GUARD, "Zombie Guard", "Chance to summon zombies to assist when hit");
        this.addEnchantment(ModEnchantments.SKELETON_GUARD, "Skeleton Guard", "Chance to summon skeletons to assist when hit");
        this.addEnchantment(ModEnchantments.FAMINE, "Famine", "Chance to apply Hunger to targets on attack");
        this.addEnchantment(ModEnchantments.IGNITION, "Ignition", "Fire Aspect for axes");
        this.addEnchantment(ModEnchantments.MINING_EXPERIENCE, "Mining Experience", "Gain more experience when mining");
        this.addEnchantment(ModEnchantments.DEVOUR, "Devour", "Restore saturation when mining");
        this.addEnchantment(ModEnchantments.SHOVE, "Shove", "Knockback for axes");
        this.addEnchantment(ModEnchantments.FORCE_FIELD, "Force Field", "Chance to apply Levitation to targets on attack");
        this.addEnchantment(ModEnchantments.FROST, "Frost", "Chance to apply Slowness on attack");
        this.addEnchantment(ModEnchantments.APPLY_POISON, "Apply Poison", "Chance to apply Poison to attacker when hit");
        this.addEnchantment(ModEnchantments.CREEPER_BANE, "Creeper Bane", "Extra damage against creepers");
        this.addEnchantment(ModEnchantments.QUICK_DIGGING, "Quick Digger", "Chance to gain Haste while mining");
        this.addEnchantment(ModEnchantments.BOUNCE_BURN, "Bounce Burn", "Chance to ignite attacker when hit");
        this.addEnchantment(ModEnchantments.SWIFT_SWORD, "Swift Sword", "Chance to gain Haste on attack");
        this.addEnchantment(ModEnchantments.MAGNET, "Magnet", "Mined items auto-collect into inventory");
        this.addEnchantment(ModEnchantments.NAUSEA, "Nausea", "Chance to apply Nausea to targets on attack");
        this.addEnchantment(ModEnchantments.WITHER, "Wither", "Chance to apply Wither on attack");
        this.addEnchantment(ModEnchantments.REFORGE, "Reforge", "Chance to repair durability on hit or shot");
        this.addEnchantment(ModEnchantments.RIME, "Rime", "Chance to apply Slowness to targets hit by your arrows");
        this.addEnchantment(ModEnchantments.FREEZE, "Freeze", "Your hits knock targets back 50% less at Lv1, not at all at Lv2");
        this.addEnchantment(ModEnchantments.THUNDER_CALL, "Thunder Call", "Chance to summon lightning when shooting targets");
        this.addEnchantment(ModEnchantments.MIGHT, "Might", "Chance to inflict Mining Fatigue on hit");
        this.addEnchantment(ModEnchantments.TIRED, "Tired", "Chance to inflict Mining Fatigue when hit");
        this.addEnchantment(ModEnchantments.BLAZE, "Blaze", "Flame effect for crossbows");
        this.addEnchantment(ModEnchantments.CURVE_AXE, "Curve Axe", "Lets axes sweep and boosts sweep damage");
        this.addEnchantment(ModEnchantments.PIGLIN_GUARD, "Piglin Guard", "Chance to summon piglins to assist when hit");
        this.addEnchantment(ModEnchantments.BLAZE_GUARD, "Blaze Guard", "Chance to summon blazes to assist when hit");
        this.addEnchantment(ModEnchantments.VINDICATOR_GUARD, "Vindicator Guard", "Chance to summon vindicators to assist when hit");
        this.addEnchantment(ModEnchantments.SAP_ESSENCE, "Sap Essence", "Mobs drop more experience when killed");
        this.addEnchantment(ModEnchantments.CRITICAL, "Critical", "Critical damage +10% per level");
        this.addEnchantment(ModEnchantments.CREEPER_GUARD, "Creeper Guard", "Chance to summon creepers to assist when hit");
        this.addEnchantment(ModEnchantments.SUSTAIN, "Sustain", "Restore saturation when attacking");
        this.addEnchantment(ModEnchantments.WITHER_HOOK, "Wither Hook", "Applies Wither when hooking mobs");
        this.addEnchantment(ModEnchantments.POISON_HOOK, "Poison Hook", "Applies Poison when hooking mobs");
        this.addEnchantment(ModEnchantments.FIRE_HOOK, "Fire Hook", "Sets mobs on fire when hooking them");
        this.addEnchantment(ModEnchantments.LUCK, "Luck", "Increases fishing catch rate");
        this.addEnchantment(ModEnchantments.GAMBLER, "Gambler", "50% chance for +50% damage, otherwise -30%");
        this.addEnchantment(ModEnchantments.HEAVINESS, "Heaviness", "Chance to negate knockback when hit");
        this.addEnchantment(ModEnchantments.LAVA_WALKER, "Lava Walker", "Generates cobblestone when walking on lava");
        this.addEnchantment(ModEnchantments.GILLS, "Gills", "Permanent Water Breathing when wearing helmet");
        this.addEnchantment(ModEnchantments.BOMBARD, "Bombard", "Chance to make targets explode when shot");
        this.addEnchantment(ModEnchantments.RESTORE, "Restore", "Chance to gain Resistance and Regeneration at low HP");
        this.addEnchantment(ModEnchantments.SPIDER_BANE, "Spider Bane", "Extra damage against spiders");
        this.addEnchantment(ModEnchantments.POISON_ARROW, "Poison Arrow", "Applies Poison to targets hit by your arrows");
        this.addEnchantment(ModEnchantments.NIGHT_VISION, "Night Vision", "Permanent Night Vision when wearing helmet");
        this.addEnchantment(ModEnchantments.SMELTING, "Smelting", "Mined drops become smelted items");
        this.addEnchantment(ModEnchantments.WITHER_ARROW, "Wither Arrow", "Applies Wither to targets hit by your arrows");
        this.addEnchantment(ModEnchantments.BLOCK, "Block", "Chance to reduce incoming damage");
        this.addEnchantment(ModEnchantments.ZOMBIE_SKELETON_BANE, "Zombie Skeleton Bane", "Extra damage against zombies and skeletons");
        this.addEnchantment(ModEnchantments.LONG_BOW, "Long Bow", "Deals more damage to players with arrows");
        this.addEnchantment(ModEnchantments.RABBIT_JUMP, "Rabbit Jump", "Permanent Jump Boost when wearing boots");
        this.addEnchantment(ModEnchantments.BUSTER, "Buster", "Axe hits may cause Slowness and Mining Fatigue");
        this.addEnchantment(ModEnchantments.FIRE_WARD, "Fire Ward", "Chance to put yourself out; Fire Resistance at Lv3");
        this.addEnchantment(ModEnchantments.BLIND, "Blind", "Chance to apply Blindness to targets on attack");
        this.addEnchantment(ModEnchantments.SYPHILIS, "Syphilis", "Chance to apply Poison to targets on attack");
        this.addEnchantment(ModEnchantments.WITHER_THORN, "Wither Thorn", "Chance to apply Wither to attacker when hit");
        this.addEnchantment(ModEnchantments.REMAKE, "Remake", "Chance to restore durability while mining");
        this.addEnchantment(ModEnchantments.CHAIN, "Chain", "Mining while sneaking mines connected blocks");
        this.addEnchantment(ModEnchantments.NETHER_BANE, "Nether Bane", "Extra damage against nether mobs");
        this.addEnchantment(ModEnchantments.CHILL, "Chill", "Chance to apply Slowness to attacker when hit");
        this.addEnchantment(ModEnchantments.LACHES, "Laches", "Chance to apply Mining Fatigue to targets hit by your arrows");
        this.addEnchantment(ModEnchantments.ESCAPE, "Escape", "Grants Invisibility, Speed and Resistance at low HP");
        this.addEnchantment(ModEnchantments.POUND, "Pound", "Power effect for crossbows");
        this.addEnchantment(ModEnchantments.ENDER_BANE, "Ender Bane", "Extra damage against ender mobs");
        this.addEnchantment(ModEnchantments.BLOODSUCKING, "Blood Sucking", "Chance to heal self and damage enemy on attack");
        this.addEnchantment(ModEnchantments.SPEED, "Speed", "Permanent Speed when wearing boots");
        this.addEnchantment(ModEnchantments.PALSY, "Palsy", "Targets hit by your arrows are immobilized and blinded");
        this.addEnchantment(ModEnchantments.VIGOROUSLY, "Vigorously", "Chance to gain Strength on attack");
        this.addEnchantment(ModEnchantments.BLESS, "Bless", "Permanent Resistance when equipped");
        this.addEnchantment(ModEnchantments.RECAST, "Recast", "Armor may restore durability when hit");
        this.addEnchantment(ModEnchantments.DEMON_POISON, "Demonic Poison", "Axe attacks apply Weakness");
        this.addEnchantment(ModEnchantments.WEALTH_DIGGING, "Wealth Digger", "Extra ores when mining ore blocks");
        this.addEnchantment(ModEnchantments.COUNTERATTACK, "Counterattack", "Chance to deal damage to attacker when hit");
        this.addEnchantment(ModEnchantments.MONSTER_SLAYER, "Monster Slayer", "Extra damage to hostile mobs when shooting");
        this.addEnchantment(ModEnchantments.ADVANCING, "Advancing", "Hold empty hand to boost elytra flight");
        this.addEnchantment(ModEnchantments.LEAP_STRIKE, "Leap Strike", "All attacks are critical, no sweeping strike");
        this.addEnchantment(ModEnchantments.MORTAL_WARD, "Mortal Ward", "Resistance to damage from overworld mobs");
        this.addEnchantment(ModEnchantments.VOID_WARD, "Void Ward", "Resistance to damage from nether and end mobs");
        this.addEnchantment(ModEnchantments.SHARP_HOOK, "Sharp Hook", "Chance to damage mobs when hooked");
        this.addEnchantment(ModEnchantments.AXE_WARD, "Axe Ward", "Resistance to axe damage");
        this.addEnchantment(ModEnchantments.SWORD_WARD, "Sword Ward", "Resistance to sword damage");
        this.addEnchantment(ModEnchantments.ARROW_WARD, "Arrow Ward", "Resistance to arrow damage");
        this.addEnchantment(ModEnchantments.BANISH, "Banish", "Chance to clear your negative effects on attack");
        this.addEnchantment(ModEnchantments.GOLEM_GUARD, "Golem Guard", "Chance to summon iron golems to assist when hit");
        this.addEnchantment(ModEnchantments.PIERCE_ARMOR, "Pierce Armor", "Arrows bypass partial armor");
        this.addEnchantment(ModEnchantments.BLAST_WARD, "Blast Ward", "Resistance to explosion damage");
        this.addEnchantment(ModEnchantments.ARROW_RAIN, "Arrow Rain", "Missed arrows rain down at the landing spot");
        this.addEnchantment(ModEnchantments.SAP_VITAL, "Sap Vital", "Axe attacks may grant saturation");
        this.addEnchantment(ModEnchantments.COMBO, "Combo", "Chance to attack a second time");
        this.addEnchantment(ModEnchantments.BODY_FORT, "Body Fort", "Increases max health when equipped");
        this.addEnchantment(ModEnchantments.PIERCE, "Pierce", "Attacks bypass partial armor");
        this.addEnchantment(ModEnchantments.ARMOR_THICK, "Armor Thick", "Grants armor and Resistance with elytra");
        this.addEnchantment(ModEnchantments.MONSTER_KILLER, "Monster Killer", "Extra damage to hostile mobs on attack");
        this.addEnchantment(ModEnchantments.POISON_REBOUND, "Poison Rebound", "Shield may apply Poison to attacker on block");
        this.addEnchantment(ModEnchantments.WITHER_REBOUND, "Wither Rebound", "Shield may apply Wither to attacker on block");
        this.addEnchantment(ModEnchantments.SLOWNESS_REBOUND, "Slowness Rebound", "Shield may apply Slowness to attacker on block");
        this.addEnchantment(ModEnchantments.SHOCK_REBOUND, "Shock Rebound", "Shield may damage attacker on block");
        this.addEnchantment(ModEnchantments.FORTIFY, "Fortify", "Reduces shield durability consumption");
        this.addEnchantment(ModEnchantments.MAGIC_WARD, "Magic Ward", "Good effects last longer, bad effects shorter");
        this.addEnchantment(ModEnchantments.FINISHER, "Finisher", "Bow deals more damage the lower the target's HP");
        this.addEnchantment(ModEnchantments.SOUL_BIND, "SoulBind", "Items cannot be dropped, not a curse");

        transBuilder.add(ModTags.ADVANCED_ENCHANTS_SCROLL, "Advanced Enchant Scroll");
        transBuilder.add(ModTags.SLOT_BUILDER, "Slot Builder");
        transBuilder.add(ModTags.LEVELUP_BALL, "Levelup Ball");
        transBuilder.add(ModTags.MINERAL, "Advanced Enchant Material");
        transBuilder.add(ModTags.ENCHANTABLE_BOW, "Bow Enchantable");
        transBuilder.add(ModTags.ENCHANTABLE_ELYTRA, "Elytra Enchantable");
        transBuilder.add(ModTags.ENCHANTABLE_SHIELD, "Shield Enchantable");
        transBuilder.add(ModTags.BOW_AND_WEAPON, "Bow and Weapon Enchantable");

        transBuilder.add("chat.you_got", "You got:");
        transBuilder.add("chat.zombie_guard", "'s zombie guard");
        transBuilder.add("chat.skeleton_guard", "'s skeleton guard");
        transBuilder.add("chat.creeper_guard", "'s creeper guard");
        transBuilder.add("chat.vindicator_guard", "'s vindicator guard");
        transBuilder.add("chat.piglin_guard", "'s piglin guard");
        transBuilder.add("chat.blaze_guard", "'s blaze guard");
        transBuilder.add("chat.golem_guard", "'s golem guard");
        transBuilder.add("chat.dungeon_block.reset_time", "Time reset");
        transBuilder.add("chat.lottery_vault_win_item", "Congratulations on winning ");
        transBuilder.add("chat.battle_progress", "Battle Progress: %s mobs | Wave %s /10");
        transBuilder.add("chat.broadcast.welcome", "Welcome to the %s level dungeon!");
        transBuilder.add("chat.broadcast.welcome1", "Click the button to start the battle");
        transBuilder.add("chat.broadcast.fight", "In battle");
        transBuilder.add("chat.broadcast.cooling", "Cooling down, %s seconds left");
        transBuilder.add("chat.broadcast.break", "Pause the fight because the difficulty is on peaceful");
        transBuilder.add("chat.triggered", "triggered:");

        this.addAdvancement("root", "Advanced Enchant", "Ancient Creations");
        this.addAdvancement("smithing_table", "A Sharp Edge", "Get an Advanced Enchant Smithing Table");
        this.addAdvancement("smithing_success", "A Job Well Done", "Forge an Advanced Enchant");
        this.addAdvancement("smithing_fail", "Smooth Move", "Fail to enchant a scroll");
        this.addAdvancement("smithing_precious_fail", "Gone with the Scroll", "Fail to enchant a Precious scroll");
        this.addAdvancement("smithing_multiple_fail", "Tenth Time Unlucky", "Fail to enchant 10 times, then count your bad luck");
        this.addAdvancement("smithing_success_full", "Fully Loaded", "Make an item's remaining Advanced Enchant slots zero");
        this.addAdvancement("smithing_success_all_full", "Peerless", "Have at least 4 Precious and 3 Rare enchants on one item");
        this.addAdvancement("advanced_anvil", "Upgrade & Strengthen", "Get an Advanced Enchant Anvil");
        this.addAdvancement("levelup_enchant", "One Step Further", "Upgrade an Advanced Enchant with a levelup ball");
        this.addAdvancement("slot_builder_levelup", "Greener Magic", "Add Advanced Enchant slots with a Slot Builder");
        this.addAdvancement("slot_builder_levelup_full", "Peak Magic", "Raise an item's enchant slots to the max, then do as you please");
        this.addAdvancement("advanced_grindstone", "Transfer & Reset", "Get an Advanced Enchant Grindstone");
        this.addAdvancement("empty_scroll", "Blank Slate", "Craft an empty scroll");
        this.addAdvancement("enchanted_enchants_scroll", "Tactical Transfer", "Use the Advanced Enchant Smithing Table to transfer all enchants");
        this.addAdvancement("reset_enchants_scroll", "Do Over", "Reset an Advanced Enchant scroll");
        this.addAdvancement("dungeon", "Mysterious Structure", "Welcome to the Dungeon");
        this.addAdvancement("dungeon_win", "Rookie Fighter", "Defeat any dungeon");
        this.addAdvancement("dungeon_win_level3", "Skilled Fighter", "Defeat the third dungeon");
        this.addAdvancement("dungeon_win_level5", "Veteran Fighter", "Defeat the fifth dungeon, then you're invincible");
        this.addAdvancement("vault", "Vault's Hidden Treasure", "Do a vault lottery draw");
        this.addAdvancement("vault_high", "Jade Vault", "Do a Level 5 vault draw. Good luck!");
        this.addAdvancement("mob_levelup", "Bad Feeling", "Mobs start getting stronger");
        this.addAdvancement("mob_levelup2", "Zombie War", "Mobs enter phase 5");
        this.addAdvancement("mob_levelup3", "Doomsday", "Mobs reach max level");

    }

    private void addEnchantment(RegistryKey<Enchantment> key, String name, String desc) {
        addEnchantment(key, name, desc, transBuilder);
    }

    private void addAdvancement(String key, String name, String desc) {
        addAdvancement(key, name, desc, transBuilder);
    }

    public static void addEnchantment(RegistryKey<Enchantment> key, String name, String desc, TranslationBuilder transBuilder) {
        String formatting;
        formatting = switch (ModEnchantments.getEnchantmentLevelType(key)) {
            case ORDINARY -> "§a";
            case ADVANCED -> "§e";
            case RARE -> "§b";
            case PRECIOUS -> "§5";
            default -> "";
        };
        transBuilder.addEnchantment(key, formatting + (formatting.isEmpty() ? "" : "§l") + name);
        transBuilder.add("enchantment." + AdvancedEnchantment.MOD_ID + "." + key.getValue().getPath() + ".desc", desc);
    }

    public static void addAdvancement(String key, String name, String desc, TranslationBuilder transBuilder) {
        transBuilder.add("advancements." + AdvancedEnchantment.MOD_ID + key, name);
        transBuilder.add("advancements." + AdvancedEnchantment.MOD_ID + key + "_description", desc);
    }
}

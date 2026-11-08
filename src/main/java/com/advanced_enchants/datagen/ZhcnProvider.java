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
 * 中文语言文件生成器。
 *
 * @see EnusProvider
 */
public class ZhcnProvider extends FabricLanguageProvider {
    private static TranslationBuilder transBuilder;

    public ZhcnProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder transBuilder) {
        ZhcnProvider.transBuilder = transBuilder;
        transBuilder.add(ModItems.ADVANCED_ENCHANTS_COMPENDIUM, "高级附魔图鉴");
        transBuilder.add(ModItems.ORDINARY_ENCHANTS_BOOK, "平凡 附魔法书");
        transBuilder.add(ModItems.ADVANCED_ENCHANTS_BOOK, "高级 附魔法书");
        transBuilder.add(ModItems.RARE_ENCHANTS_BOOK, "稀有 附魔法书");
        transBuilder.add(ModItems.PRECIOUS_ENCHANTS_BOOK, "珍贵 附魔法书");
        transBuilder.add(ModItems.ORDINARY_ENCHANTS_SCROLL, "平凡 附魔卷轴");
        transBuilder.add(ModItems.ADVANCED_ENCHANTS_SCROLL, "高级 附魔卷轴");
        transBuilder.add(ModItems.RARE_ENCHANTS_SCROLL, "稀有 附魔卷轴");
        transBuilder.add(ModItems.PRECIOUS_ENCHANTS_SCROLL, "珍贵 附魔卷轴");
        transBuilder.add(ModItems.EMPTY_SCROLL, "空卷轴");
        transBuilder.add(ModItems.ENCHANTED_SCROLL, "附魔卷轴");
        transBuilder.add(ModItems.ORDINARY_LEVELUP_BALL, "普通 升级书球");
        transBuilder.add(ModItems.ADVANCED_LEVELUP_BALL, "高级 升级书球");
        transBuilder.add(ModItems.RARE_LEVELUP_BALL, "稀有 升级书球");
        transBuilder.add(ModItems.PRECIOUS_LEVELUP_BALL, "珍贵 升级书球");
        transBuilder.add(ModItems.LEVEL1_SLOT_BUILDER, "1级槽位构筑器");
        transBuilder.add(ModItems.LEVEL2_SLOT_BUILDER, "2级槽位构筑器");
        transBuilder.add(ModItems.LEVEL3_SLOT_BUILDER, "3级槽位构筑器");
        transBuilder.add(ModItems.DUNGEON1_KEY, "一级 地牢钥匙");
        transBuilder.add(ModItems.DUNGEON2_KEY, "二级 地牢钥匙");
        transBuilder.add(ModItems.DUNGEON3_KEY, "三级 地牢钥匙");
        transBuilder.add(ModItems.DUNGEON4_KEY, "四级 地牢钥匙");
        transBuilder.add(ModItems.DUNGEON5_KEY, "五级 地牢钥匙");
        transBuilder.add("item.advanced_slot", "§l§a总高级附魔槽位:§c%s，§l§6剩余§c%s");

        transBuilder.add(ModBlocks.ADVANCED_ENCHANTS_SMITHING_TABLE, "高级附魔锻造台");
        transBuilder.add(ModBlocks.ADVANCED_ENCHANTS_ANVIL, "高级附魔铁砧");
        transBuilder.add(ModBlocks.ADVANCED_ENCHANTS_GRINDSTONE, "高级附魔砂轮");
        transBuilder.add(ModBlocks.DUNGEON_VAULT, "地牢宝库");
        transBuilder.add(ModBlocks.DUNGEON_SPAWNER, "地牢刷怪笼");
        transBuilder.add(ModBlocks.DUNGEON_BROADCAST, "地牢主控方块");
        transBuilder.add(ModBlocks.PEBBLE, "鹅卵石");
        transBuilder.add(ModItemGroups.ADVANCED_ENCHANTS_GROUPS, "高级附魔相关栏");
        transBuilder.add("gui.advanced_enchants_anvil.text", "升级物品");
        transBuilder.add("gui.advanced_enchants_grindstone.text", "转移及重置");
        transBuilder.add("gui.advanced_enchants_smithing.text", "附魔物品");

        transBuilder.add("gui.compendium.page", "第%s页");
        transBuilder.add("gui.compendium.page1.line1", "    高 级 附 魔");
        transBuilder.add("gui.compendium.page1.line2", "版本 " + AdvancedEnchantment.VERSION);
        transBuilder.add("gui.compendium.page1.line3", "制作者 ADHD_WLN");
        transBuilder.add("gui.compendium.page1.introduction1", "这本书是高级附魔模组的图鉴");
        transBuilder.add("gui.compendium.page1.introduction2", "本MOD为游戏添加了许多附魔");
        transBuilder.add("gui.compendium.page1.introduction3", "此图鉴能让你详细了解此MOD");
        transBuilder.add("gui.compendium.page1.introduction4", "包括高级附魔简介、高级附魔");
        transBuilder.add("gui.compendium.page1.introduction5", "用处、高级附魔使用方法(建议");
        transBuilder.add("gui.compendium.page1.introduction6", "装载附魔描述MOD)");
        transBuilder.add("gui.compendium.page1.contents", "  目录");
        transBuilder.add("gui.compendium.page1.contents1", "1.高级附魔简介");
        transBuilder.add("gui.compendium.page1.contents2", "2.高级附魔用处");
        transBuilder.add("gui.compendium.page1.contents3", "3.高级附魔使用方法");
        transBuilder.add("gui.compendium.page1.contents4", "(1) 高级附魔锻造台");
        transBuilder.add("gui.compendium.page1.contents5", "(2) 高级附魔铁砧");
        transBuilder.add("gui.compendium.page1.contents6", "(3) 高级附魔砂轮");
        transBuilder.add("gui.compendium.page1.contents7", "4.高级附魔附魔列表");
        transBuilder.add("gui.compendium.page1.contents8", "(1) 平凡附魔");
        transBuilder.add("gui.compendium.page1.contents9", "(2) 高级附魔");
        transBuilder.add("gui.compendium.page1.contents10", "(3) 稀有附魔");
        transBuilder.add("gui.compendium.page1.contents11", "(4) 珍贵附魔");


        transBuilder.add("gui.compendium.page2.introduction1", "§a高级附魔§r是一种特殊附魔他的");
        transBuilder.add("gui.compendium.page2.introduction2", "功能普遍比普通附魔强大，但");
        transBuilder.add("gui.compendium.page2.introduction3", "是由于他的特殊这种附魔的获");
        transBuilder.add("gui.compendium.page2.introduction4", "取方式与普通附魔不一样，他");
        transBuilder.add("gui.compendium.page2.introduction5", "无法在附魔台上附魔到，同时");
        transBuilder.add("gui.compendium.page2.introduction6", "除作弊外，你无法获得他的附");
        transBuilder.add("gui.compendium.page2.introduction7", "魔书。高级附魔默认自带颜色");
        transBuilder.add("gui.compendium.page2.introduction8", "和粗体。在生存中，你会获得");
        transBuilder.add("gui.compendium.page2.introduction9", "名叫§c\"卷轴\"§r的附魔载体，它");
        transBuilder.add("gui.compendium.page2.introduction10", "分为四个等级，§a平凡，§6高级，");
        transBuilder.add("gui.compendium.page2.introduction11", "§b稀有，§5珍贵。等级越大。能力");
        transBuilder.add("gui.compendium.page2.introduction12", "越强，取的难度也会越大，同");
        transBuilder.add("gui.compendium.page2.introduction13", "时也有四个等级的升级书球，");
        transBuilder.add("gui.compendium.page2.introduction14", "他们可以升级你的附魔卷轴。");
        transBuilder.add("gui.compendium.page2.introduction15", "高级附魔有槽位限制，不同物");
        transBuilder.add("gui.compendium.page2.introduction16", "品默认槽位上限不同，你可以");
        transBuilder.add("gui.compendium.page2.introduction17", "用§6\"槽位构筑器\"§r增加槽位，");
        transBuilder.add("gui.compendium.page2.introduction18", "他有3个等级，每多一级就可");
        transBuilder.add("gui.compendium.page2.introduction19", "增加1个槽位，他非常难获取");
        transBuilder.add("gui.compendium.page2.introduction20", "所以搭配高级附魔要谨慎选择");

        transBuilder.add("gui.compendium.page3.introduction1", "大多数战斗型高级附魔都是给");
        transBuilder.add("gui.compendium.page3.introduction2", "与使用者或攻击者一个§a状态§a效");
        transBuilder.add("gui.compendium.page3.introduction3", "果，这些效果触发方式有穿戴");
        transBuilder.add("gui.compendium.page3.introduction4", "永久持有，受击给攻击者一个");
        transBuilder.add("gui.compendium.page3.introduction5", "负面效果，攻击时给自己一个");
        transBuilder.add("gui.compendium.page3.introduction6", "正面效果等，也有实用性高级");
        transBuilder.add("gui.compendium.page3.introduction7", "附魔，如熔炼，把挖掘的矿物");
        transBuilder.add("gui.compendium.page3.introduction8", "自动烧制矿锭。另外模组修改");
        transBuilder.add("gui.compendium.page3.introduction9", "了生物生成，§c怪物将随着时");
        transBuilder.add("gui.compendium.page3.introduction10", "间越变越§c强§r，用高级附魔配合");
        transBuilder.add("gui.compendium.page3.introduction11", "其他模组武器是一个不错选择");
        transBuilder.add("gui.compendium.page3.introduction12", "模组还有一种自然刷新的结构");
        transBuilder.add("gui.compendium.page3.introduction13", "§6\"地牢\"§r，他是高级附魔的获");
        transBuilder.add("gui.compendium.page3.introduction14", "取途径，要打怪通关，越高等");
        transBuilder.add("gui.compendium.page3.introduction15", "级怪越强，因此需要高级附魔");

        transBuilder.add("gui.compendium.page4.title", "1.高级附魔锻造台");
        transBuilder.add("gui.compendium.page4.introduction1", "用此配方可合成一个§6高级附魔");
        transBuilder.add("gui.compendium.page4.introduction2", "§6锻造台§r，他是使物品附魔高级");
        transBuilder.add("gui.compendium.page4.introduction3", "附魔的唯一途径。他有三个输");
        transBuilder.add("gui.compendium.page4.introduction4", "入槽，第一格放要附魔的装备");
        transBuilder.add("gui.compendium.page4.introduction5", "第二格放附魔卷轴，第三格放");
        transBuilder.add("gui.compendium.page4.introduction6", "矿物，即可取出附魔后的装备");
        transBuilder.add("gui.compendium.page4.introduction7", "其中矿物因附魔卷轴等级而异");
        transBuilder.add("gui.compendium.page4.introduction8", "平凡是铁锭，高级是金锭，稀");
        transBuilder.add("gui.compendium.page4.introduction9", "有是绿宝石，珍贵是钻石。数");
        transBuilder.add("gui.compendium.page4.introduction10", "量由卷轴附魔的等级而定。另");
        transBuilder.add("gui.compendium.page4.introduction11", "外，取出时有35%概率失败。");
        transBuilder.add("gui.compendium.page4.introduction12", "失败时，会扣除附魔卷轴和矿");
        transBuilder.add("gui.compendium.page4.introduction13", "物，但不给完成的物品。");

        transBuilder.add("gui.compendium.page5.title", "2.高级附魔铁砧");
        transBuilder.add("gui.compendium.page5.introduction1", "用此配方可合成一个§6高级附魔");
        transBuilder.add("gui.compendium.page5.introduction2", "§6铁砧§r，他可以升级高级附魔的");
        transBuilder.add("gui.compendium.page5.introduction3", "等级，也可以升级物品的附魔");
        transBuilder.add("gui.compendium.page5.introduction4", "槽位他有三个输入槽，第一格");
        transBuilder.add("gui.compendium.page5.introduction5", "放要升级的装备，第二格放升");
        transBuilder.add("gui.compendium.page5.introduction6", "级物品材料，即可取出成品。");
        transBuilder.add("gui.compendium.page5.introduction7", "其中锻造不会有概率失败想要");
        transBuilder.add("gui.compendium.page5.introduction8", "升级对应附魔的等级就需要对");
        transBuilder.add("gui.compendium.page5.introduction9", "应等级的升级书球，另外非高");
        transBuilder.add("gui.compendium.page5.introduction10", "级附魔可以用末影之眼升级工");
        transBuilder.add("gui.compendium.page5.introduction11", "具如果高级附魔槽位不足可以");
        transBuilder.add("gui.compendium.page5.introduction12", "用槽位构筑器升级槽位。槽位");
        transBuilder.add("gui.compendium.page5.introduction13", "构筑器几级就可以增加几格槽");
        transBuilder.add("gui.compendium.page5.introduction14", "位。");

        transBuilder.add("gui.compendium.page6.title", "3.高级附魔砂轮");
        transBuilder.add("gui.compendium.page6.introduction1", "用此配方可合成一个§6高级附魔");
        transBuilder.add("gui.compendium.page6.introduction2", "§6砂轮§r，他可以转移物品为附魔");
        transBuilder.add("gui.compendium.page6.introduction3", "卷轴，也可以重置没用的附魔");
        transBuilder.add("gui.compendium.page6.introduction4", "卷轴。他有两个输入槽如果想");
        transBuilder.add("gui.compendium.page6.introduction5", "要转移附魔，就把要转移的装");
        transBuilder.add("gui.compendium.page6.introduction6", "备放在第一格，第二格放空卷");
        transBuilder.add("gui.compendium.page6.introduction7", "轴，即可取出附魔卷轴可以用");
        transBuilder.add("gui.compendium.page6.introduction8", "高级附魔锻造台让物品附魔附");
        transBuilder.add("gui.compendium.page6.introduction9", "魔卷轴概率也从65%升到85%");
        transBuilder.add("gui.compendium.page6.introduction10", "如果想要重置附魔就把要重置");
        transBuilder.add("gui.compendium.page6.introduction11", "的附魔卷轴丢第一格第二格放");
        transBuilder.add("gui.compendium.page6.introduction12", "矿物，就可以变成对应附魔法");
        transBuilder.add("gui.compendium.page6.introduction13", "书。矿物参见高级附魔锻造台");
        transBuilder.add("gui.compendium.page6.introduction14", "数量是(最大附魔等级-附魔等");
        transBuilder.add("gui.compendium.page6.introduction15", "级)*3。经验消耗量比矿物消");
        transBuilder.add("gui.compendium.page6.introduction16", "耗要少66%");

        transBuilder.add("gui.compendium.enchants_list.max_level", "最大等级:%s");
        transBuilder.add("gui.compendium.enchants_list.supported_items", "适用物品:");
        transBuilder.add("gui.compendium.enchants_list.chance", "触发概率:");
        transBuilder.add("gui.compendium.enchants_list.linear", "每级增加%s");

        this.addEnchantment(ModEnchantments.ZOMBIE_GUARD, "僵尸守卫", "受击有概率召唤僵尸协助作战");
        this.addEnchantment(ModEnchantments.SKELETON_GUARD, "骷髅守卫", "受击有概率召唤骷髅协助作战");
        this.addEnchantment(ModEnchantments.IGNITION, "起燃", "斧子的火焰附加");
        this.addEnchantment(ModEnchantments.MINING_EXPERIENCE, "矿经", "挖矿时增加经验获取");
        this.addEnchantment(ModEnchantments.FAMINE, "饥荒", "攻击时有概率给敌方饥饿效果");
        this.addEnchantment(ModEnchantments.DEVOUR, "吞噬", "挖掘时会恢复饱食度");
        this.addEnchantment(ModEnchantments.SHOVE, "震击", "斧子的击退");
        this.addEnchantment(ModEnchantments.FORCE_FIELD, "力场", "攻击时有概率给敌方漂浮效果");
        this.addEnchantment(ModEnchantments.CREEPER_GUARD, "爬行者守卫", "受击有概率召唤爬行者协助作战");
        this.addEnchantment(ModEnchantments.SUSTAIN, "窃饥", "攻击时会恢复饱食度");
        this.addEnchantment(ModEnchantments.WITHER_HOOK, "枯钩", "勾住生物时给与凋零效果");
        this.addEnchantment(ModEnchantments.POISON_HOOK, "毒钩", "勾住生物时给与中毒效果");
        this.addEnchantment(ModEnchantments.FIRE_HOOK, "火钩", "勾住生物时使生物着火");
        this.addEnchantment(ModEnchantments.LUCK, "幸运", "增加钓鱼上钩概率");
        this.addEnchantment(ModEnchantments.GAMBLER, "赌徒", "50%率将剑斧伤害增加50%，50%率减少30%");
        this.addEnchantment(ModEnchantments.HEAVINESS, "沉重", "受击时有概率无击退");
        this.addEnchantment(ModEnchantments.LAVA_WALKER, "岩浆行者", "在岩浆上行走时会产生鹅卵石");
        this.addEnchantment(ModEnchantments.FROST, "寒霜", "攻击有概率给对面缓慢效果");
        this.addEnchantment(ModEnchantments.APPLY_POISON, "涂毒", "受击有概率给对面中毒效果");
        this.addEnchantment(ModEnchantments.QUICK_DIGGING, "速掘", "挖掘时有概率获得急迫");
        this.addEnchantment(ModEnchantments.CREEPER_BANE, "爬行者杀手", "增加对爬行者攻击的伤害");
        this.addEnchantment(ModEnchantments.BOUNCE_BURN, "反燃", "受击有概率使对面着火");
        this.addEnchantment(ModEnchantments.SWIFT_SWORD, "快刃", "攻击时有概率获得急迫");
        this.addEnchantment(ModEnchantments.MAGNET, "吸铁石", "挖掘后物品会自动进入背包");
        this.addEnchantment(ModEnchantments.WITHER, "凋零", "攻击时有概率给对面凋零效果");
        this.addEnchantment(ModEnchantments.FREEZE, "冻结", "攻击时1级减50%击退，2级无击退，可以消除震击击退附魔");
        this.addEnchantment(ModEnchantments.NAUSEA, "恶心", "攻击时有概率给对面反胃效果");
        this.addEnchantment(ModEnchantments.RIME, "冰霜", "射击目标有概率给对面缓慢效果");
        this.addEnchantment(ModEnchantments.REFORGE, "重锻", "弓和武器在射击或攻击后有概率恢复耐久");
        this.addEnchantment(ModEnchantments.THUNDER_CALL, "唤雷", "射击目标时有概率召唤闪电");
        this.addEnchantment(ModEnchantments.MIGHT, "大力", "攻击时有概率给对面挖掘疲劳");
        this.addEnchantment(ModEnchantments.TIRED, "疲劳", "受击时有概率给对面挖掘疲劳");
        this.addEnchantment(ModEnchantments.BLAZE, "烈焰", "弩的火矢附魔");
        this.addEnchantment(ModEnchantments.CURVE_AXE, "弯斧", "使斧头可以横扫，并增加横扫伤害");
        this.addEnchantment(ModEnchantments.PIGLIN_GUARD, "猪灵守卫", "受击有概率召唤猪灵协助作战");
        this.addEnchantment(ModEnchantments.BLAZE_GUARD, "烈焰守卫", "受击有概率召唤烈焰人协助作战");
        this.addEnchantment(ModEnchantments.VINDICATOR_GUARD, "道士守卫", "受击有概率召唤卫道士协助作战");
        this.addEnchantment(ModEnchantments.SAP_ESSENCE, "汲经", "杀死生物会掉落更多经验");
        this.addEnchantment(ModEnchantments.CRITICAL, "猛击", "使暴击伤害提升10%*附魔等级");
        this.addEnchantment(ModEnchantments.GILLS, "鱼鳃", "头盔穿戴时获得永久水下呼吸");
        this.addEnchantment(ModEnchantments.BOMBARD, "轰破", "射击目标时有概率使敌人爆炸");
        this.addEnchantment(ModEnchantments.RESTORE, "恢复", "残血时攻击有概率获得抗性提升和生命恢复");
        this.addEnchantment(ModEnchantments.SPIDER_BANE, "蜘蛛杀手", "增加对蜘蛛攻击的伤害");
        this.addEnchantment(ModEnchantments.POISON_ARROW, "毒矢", "射击目标时使目标获得中毒");
        this.addEnchantment(ModEnchantments.NIGHT_VISION, "夜视", "头盔穿戴时获得永久夜视");
        this.addEnchantment(ModEnchantments.SMELTING, "冶炼", "挖掘方块时使掉落物变成烧制后的状态");
        this.addEnchantment(ModEnchantments.WITHER_ARROW, "凋谢", "射击目标时使目标获得凋零");
        this.addEnchantment(ModEnchantments.BLOCK, "格挡", "有概率减少受击伤害");
        this.addEnchantment(ModEnchantments.ZOMBIE_SKELETON_BANE, "僵骷杀手", "增加对僵尸和骷髅类的伤害");
        this.addEnchantment(ModEnchantments.LONG_BOW, "长弓", "射击时增加对玩家造成的伤害");
        this.addEnchantment(ModEnchantments.RABBIT_JUMP, "兔跃", "靴子穿戴时获得永久跳跃提升");
        this.addEnchantment(ModEnchantments.BUSTER, "碎击", "斧子攻击时有概率给与缓慢和挖掘疲劳");
        this.addEnchantment(ModEnchantments.FIRE_WARD, "阻燃", "三级以下时受击有概率熄火，三级获得永久抗火");
        this.addEnchantment(ModEnchantments.BLIND, "致盲", "攻击时有概率给对面失明效果");
        this.addEnchantment(ModEnchantments.SYPHILIS, "霉毒", "攻击时有概率给对面中毒效果");
        this.addEnchantment(ModEnchantments.WITHER_THORN, "凋刺", "受击有概率给对面凋零效果");
        this.addEnchantment(ModEnchantments.REMAKE, "重塑", "挖掘时有概率恢复耐久");
        this.addEnchantment(ModEnchantments.CHAIN, "连锁", "蹲下挖掘时可连锁挖掘方块");
        this.addEnchantment(ModEnchantments.NETHER_BANE, "下界杀手", "增加对下界生物攻击的伤害");
        this.addEnchantment(ModEnchantments.ENDER_BANE, "末影杀手", "增加对末影生物攻击的伤害");
        this.addEnchantment(ModEnchantments.CHILL, "霜冻", "受击时有概率给对面缓慢效果");
        this.addEnchantment(ModEnchantments.LACHES, "懈怠", "射击目标时有概率使目标获得挖掘疲劳");
        this.addEnchantment(ModEnchantments.ESCAPE, "逃走", "残血时给自己隐身速度和抗性提升");
        this.addEnchantment(ModEnchantments.POUND, "力击", "弩的力量附魔");
        this.addEnchantment(ModEnchantments.BLOODSUCKING, "吸血", "攻击时有概率回血并使敌人扣血");
        this.addEnchantment(ModEnchantments.SPEED, "疾行", "靴子穿戴时获得永久速度");
        this.addEnchantment(ModEnchantments.PALSY, "麻痹", "射击目标时使敌人不能动且给与失明");
        this.addEnchantment(ModEnchantments.VIGOROUSLY, "巨力", "攻击有概率获得力量效果");
        this.addEnchantment(ModEnchantments.BLESS, "护佑", "穿戴时获得永久抗性提升");
        this.addEnchantment(ModEnchantments.RECAST, "重铸", "受击时盔甲有概率恢复耐久");
        this.addEnchantment(ModEnchantments.DEMON_POISON, "妖毒", "斧子攻击时给与虚弱效果");
        this.addEnchantment(ModEnchantments.WEALTH_DIGGING, "掘富", "挖掘矿物时获得更多矿物");
        this.addEnchantment(ModEnchantments.COUNTERATTACK, "反击", "受击时有概率使敌人获得一定伤害");
        this.addEnchantment(ModEnchantments.MONSTER_SLAYER, "怪物克星", "射击时增加对敌对生物的伤害");
        this.addEnchantment(ModEnchantments.ADVANCING, "推进", "鞘翅附魔后，空手长按就可以推进飞行");
        this.addEnchantment(ModEnchantments.LEAP_STRIKE, "顺劈", "使攻击永远为暴击，但会失去横扫");
        this.addEnchantment(ModEnchantments.VOID_WARD, "冥护", "增加对下界末地生物的攻击的抗性");
        this.addEnchantment(ModEnchantments.MORTAL_WARD, "凡护", "增加对主世界生物的攻击的抗性");
        this.addEnchantment(ModEnchantments.SHARP_HOOK, "利钩", "勾住生物时有概率使生物受伤");
        this.addEnchantment(ModEnchantments.ARROW_WARD, "御箭", "增加对箭矢伤害的抗性");
        this.addEnchantment(ModEnchantments.SWORD_WARD, "御刃", "增加对剑攻击伤害的抗性");
        this.addEnchantment(ModEnchantments.AXE_WARD, "御斧", "增加对斧攻击伤害的抗性");
        this.addEnchantment(ModEnchantments.BANISH, "驱邪", "攻击时有概率消除身上负面效果");
        this.addEnchantment(ModEnchantments.GOLEM_GUARD, "傀儡守卫", "受击有概率召唤铁傀儡协助作战");
        this.addEnchantment(ModEnchantments.PIERCE_ARMOR, "穿甲", "箭矢会无视一些护甲值造成伤害");
        this.addEnchantment(ModEnchantments.BLAST_WARD, "防爆", "增加爆炸伤害的抗性");
        this.addEnchantment(ModEnchantments.ARROW_RAIN, "箭雨", "未射击中目标时，在箭矢落地处下一场箭雨");
        this.addEnchantment(ModEnchantments.SAP_VITAL, "汲养", "斧头攻击时有概率获得一些饱和度");
        this.addEnchantment(ModEnchantments.COMBO, "连斩", "有概率攻击第二次");
        this.addEnchantment(ModEnchantments.BODY_FORT, "厚体", "穿戴时增加血量上限");
        this.addEnchantment(ModEnchantments.PIERCE, "贯甲", "攻击会无视一些护甲值造成伤害");
        this.addEnchantment(ModEnchantments.ARMOR_THICK, "装甲", "鞘翅附魔后，会获得一些护甲值和抗性提升");
        this.addEnchantment(ModEnchantments.MONSTER_KILLER, "怪物杀手", "攻击时增加对敌对生物的伤害");
        this.addEnchantment(ModEnchantments.POISON_REBOUND, "反毒", "盾牌受击时给与中毒效果");
        this.addEnchantment(ModEnchantments.WITHER_REBOUND, "反枯", "盾牌受击时给与凋零效果");
        this.addEnchantment(ModEnchantments.SLOWNESS_REBOUND, "反滞", "盾牌受击时给与缓慢效果");
        this.addEnchantment(ModEnchantments.SHOCK_REBOUND, "反震", "盾牌受击时使对面受伤");
        this.addEnchantment(ModEnchantments.FORTIFY, "加固", "减少盾牌耐久的消耗");
        this.addEnchantment(ModEnchantments.FINISHER, "终击", "敌人血量越低，弓额外造成的伤害越高");
        this.addEnchantment(ModEnchantments.SOUL_BIND, "魂缚", "使物品无法丢出物品栏，不是高级附魔");
        this.addEnchantment(ModEnchantments.MAGIC_WARD, "御魔", "获得正面效果时增加效果时间，负面效果减少时间");

        transBuilder.add(ModTags.ADVANCED_ENCHANTS_SCROLL, "高级附魔卷轴");
        transBuilder.add(ModTags.SLOT_BUILDER, "槽位构筑器");
        transBuilder.add(ModTags.LEVELUP_BALL, "升级书球");
        transBuilder.add(ModTags.MINERAL, "高级附魔附魔材料");
        transBuilder.add(ModTags.ENCHANTABLE_BOW, "弓弩附魔");
        transBuilder.add(ModTags.ENCHANTABLE_ELYTRA, "鞘翅附魔");
        transBuilder.add(ModTags.ENCHANTABLE_SHIELD, "盾附魔");
        transBuilder.add(ModTags.BOW_AND_WEAPON, "弓与武器附魔");

        transBuilder.add("chat.you_got", "你获得了:");
        transBuilder.add("chat.zombie_guard", "的僵尸守卫");
        transBuilder.add("chat.skeleton_guard", "的骷髅守卫");
        transBuilder.add("chat.creeper_guard", "的苦力怕守卫");
        transBuilder.add("chat.vindicator_guard", "的卫道士守卫");
        transBuilder.add("chat.piglin_guard", "的猪灵守卫");
        transBuilder.add("chat.blaze_guard", "的烈焰人守卫");
        transBuilder.add("chat.golem_guard", "的铁傀儡守卫");
        transBuilder.add("chat.dungeon_block.reset_time", "时间已重置");
        transBuilder.add("chat.lottery_vault_win_item", "恭喜获得 ");
        transBuilder.add("chat.battle_progress", "战斗进度: %s个怪物 | 第%s/10 波");
        transBuilder.add("chat.broadcast.welcome", "欢迎来到第%s地牢!");
        transBuilder.add("chat.broadcast.welcome1", "点击按钮开始战斗");
        transBuilder.add("chat.broadcast.fight", "战斗中");
        transBuilder.add("chat.broadcast.cooling", "冷却中,剩余%s秒");
        transBuilder.add("chat.broadcast.break", "战斗中断,因为难度为和平");
        transBuilder.add("chat.triggered", "触发了:");

        this.addAdvancement("root", "高级附魔", "古老的造物");
        this.addAdvancement("smithing_table", "必先利其器", "获得高级附魔锻造台");
        this.addAdvancement("smithing_success", "才可善其事", "锻造高级附魔");
        this.addAdvancement("smithing_fail", "哎呀", "附魔卷轴时失败");
        this.addAdvancement("smithing_precious_fail", "倒霉蛋", "附魔珍贵卷轴时失败");
        this.addAdvancement("smithing_multiple_fail", "老贝炸", "附魔失败10次，然后想想你有多倒霉");
        this.addAdvancement("smithing_success_full", "满载", "使物品剩余高级附魔槽位为零");
        this.addAdvancement("smithing_success_all_full", "举世无双", "使一个物品至少4个珍贵附魔和至少3个稀有附魔");
        this.addAdvancement("advanced_anvil", "升级与强化", "获得高级附魔铁砧");
        this.addAdvancement("levelup_enchant", "更进一步", "用升级书球升级高级附魔");
        this.addAdvancement("slot_builder_levelup", "魔力更翠", "用槽位构筑器增加高级附魔槽位");
        this.addAdvancement("slot_builder_levelup_full", "顶天魔力", "使物品附魔槽位升至最高，然后你可以随心所欲了");
        this.addAdvancement("advanced_grindstone", "转移与重置", "获得高级附魔砂轮");
        this.addAdvancement("empty_scroll", "白纸", "制作空卷轴");
        this.addAdvancement("enchanted_enchants_scroll", "战术转移", "用高级附魔锻造台转移全部附魔");
        this.addAdvancement("reset_enchants_scroll", "再来一次", "重置高级附魔卷轴");
        this.addAdvancement("dungeon", "神秘建筑", "欢迎来到地牢");
        this.addAdvancement("dungeon_win", "入门战斗家", "击败任意地牢");
        this.addAdvancement("dungeon_win_level3", "熟练战斗家", "击败第三地牢");
        this.addAdvancement("dungeon_win_level5", "资深战斗家", "击败第五地牢，然后你就无敌了");
        this.addAdvancement("vault", "宝库秘藏", "进行一次宝库抽奖");
        this.addAdvancement("vault_high", "玉珍宝库", "进行一次五级宝库抽奖，祝您好运");
        this.addAdvancement("mob_levelup", "感觉不妙", "怪物开始加强");
        this.addAdvancement("mob_levelup2", "战僵", "怪物们开启第五阶段");
        this.addAdvancement("mob_levelup3", "末日", "怪物们达到最高等级");
    }

    private void addEnchantment(RegistryKey<Enchantment> key, String name, String desc) {
        EnusProvider.addEnchantment(key, name, desc, transBuilder);
    }

    private void addAdvancement(String key, String name, String desc) {
        EnusProvider.addAdvancement(key, name, desc, transBuilder);
    }
}
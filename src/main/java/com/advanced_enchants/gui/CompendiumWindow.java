package com.advanced_enchants.gui;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.enchantment.effect.BaseEnchantmentEffect;
import com.advanced_enchants.register.ModAPI;
import com.advanced_enchants.register.ModEnchantments;
import com.advanced_enchants.register.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

/**
 * 附魔指南界面。
 *
 * @see OpenCompendium
 */
public class CompendiumWindow extends Screen {
    private static final int WINDOW_WIDTH = 146;
    private static final int WINDOW_HEIGHT = 180;
    private static final int WIDTH_RANGE = 118;
    private static boolean isEnglish = MinecraftClient.getInstance().options.language.startsWith("en");
    private static float titleTextScale = isEnglish ? 1.0f : 1.3f;
    private static float textScale = isEnglish ? 0.7f : 1.0f;
    private int page = 1;
    private final PlayerEntity player;
    private final int ordinaryEndPage = 6 + (ModEnchantments.ORDINARY_LIST.size() + 5) / 6;
    private final int advancedEndPage = ordinaryEndPage + (ModEnchantments.ADVANCED_LIST.size() + 5) / 6;
    private final int rareEndPage = advancedEndPage + (ModEnchantments.RARE_LIST.size() + 5) / 6;
    private final int maxPage = rareEndPage + (ModEnchantments.PRECIOUS_LIST.size() + 5) / 6;

    public CompendiumWindow(PlayerEntity player) {
        super(Text.literal("compendiumWindow"));
        this.player = player;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        isEnglish = MinecraftClient.getInstance().options.language.startsWith("en");
        titleTextScale = isEnglish ? 1.0f : 1.3f;
        textScale = isEnglish ? 0.7f : 1.0f;
        int guiX = (this.width - 2 * WINDOW_WIDTH) / 2;
        int guiY = (this.height - WINDOW_HEIGHT) / 2;
        final int locationX = guiX + 24;
        final int locationX2 = guiX + WINDOW_WIDTH * 2 - 42;
        final int locationY = guiY + WINDOW_HEIGHT - 20;
        context.fillGradient(0, 0, this.width, this.height, 0x80000000, 0x80000000);
        drawTexture(context, "left_compendium", guiX, guiY, WINDOW_WIDTH, WINDOW_HEIGHT);
        drawTexture(context, "right_compendium", guiX + WINDOW_WIDTH, guiY, WINDOW_WIDTH, WINDOW_HEIGHT);
        if (page == 1) drawPage1(context, guiX, guiY, mouseX, mouseY);
        else if (page == 2) drawPage2(context, guiX, guiY);
        else if (page == 3) drawPage3(context, guiX, guiY);
        else if (page == 4) drawPage4(context, guiX, guiY);
        else if (page == 5) drawPage5(context, guiX, guiY);
        else if (page == 6) drawPage6(context, guiX, guiY);
        else {
            if (page == 7) {
                ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents7").formatted(Formatting.RED, Formatting.BOLD), guiX + 20, guiY + 14, titleTextScale);
            }
            if (page <= ordinaryEndPage) drawOrdinaryEnchantsIntroduction(context, guiX, guiY, page);
            else if (page <= advancedEndPage) drawAdvancedEnchantsIntroduction(context, guiX, guiY, page);
            else if (page <= rareEndPage) drawRareEnchantsIntroduction(context, guiX, guiY, page);
            else drawPreciousEnchantsIntroduction(context, guiX, guiY, page);
        }
        if (!(page == 1)) {
            drawTexture(context, "arrow3", locationX, locationY, 18, 10);
            if (isInRect(mouseX, mouseY, locationX, locationY, 18, 10)) {
                drawTexture(context, "arrow4", locationX, locationY, 18, 10);
            }
        }
        if (!(page == maxPage)) {
            drawTexture(context, "arrow1", locationX2, locationY, 18, 10);
            if (isInRect(mouseX, mouseY, locationX2, locationY, 18, 10)) {
                drawTexture(context, "arrow2", locationX2, locationY, 18, 10);
            }
        }
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page", page), guiX + WINDOW_WIDTH - 40, guiY + 160, 0.8f, false, false, true);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int guiX = (this.width - 2 * WINDOW_WIDTH) / 2;
            int guiY = (this.height - WINDOW_HEIGHT) / 2;
            final int locationX = guiX + 24;
            final int locationX2 = guiX + WINDOW_WIDTH * 2 - 42;
            final int locationY = guiY + WINDOW_HEIGHT - 20;
            if (isInRect(mouseX, mouseY, locationX, locationY, 18, 10)) {
                page = Math.max(page - 1, 1);
                ModAPI.playSoundsToPlayer(SoundEvents.ITEM_BOOK_PAGE_TURN, player);
                return true;
            }
            if (isInRect(mouseX, mouseY, locationX2, locationY, 18, 10)) {
                page = Math.min(page + 1, maxPage);
                ModAPI.playSoundsToPlayer(SoundEvents.ITEM_BOOK_PAGE_TURN, player);
                return true;
            }
            if (page == 1) {
                int Page2GuiX = guiX + 146;
                boolean chance = false;
                if (isInRect(mouseX, mouseY, Page2GuiX + 20, guiY + 34, WIDTH_RANGE, 8)) {
                    page = 2;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 20, guiY + 44, WIDTH_RANGE, 8)) {
                    page = 3;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 20, guiY + 54, WIDTH_RANGE, 8)
                        || isInRect(mouseX, mouseY, Page2GuiX + 25, guiY + 64, WIDTH_RANGE, 8)) {
                    page = 4;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 25, guiY + 74, WIDTH_RANGE, 8)) {
                    page = 5;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 25, guiY + 84, WIDTH_RANGE, 8)) {
                    page = 6;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 20, guiY + 94, WIDTH_RANGE, 8)
                        || isInRect(mouseX, mouseY, Page2GuiX + 25, guiY + 104, WIDTH_RANGE, 8)) {
                    page = 7;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 25, guiY + 114, WIDTH_RANGE, 8)) {
                    page = ordinaryEndPage + 1;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 25, guiY + 124, WIDTH_RANGE, 8)) {
                    page = advancedEndPage + 1;
                    chance = true;
                }
                if (isInRect(mouseX, mouseY, Page2GuiX + 25, guiY + 134, WIDTH_RANGE, 8)) {
                    page = rareEndPage + 1;
                    chance = true;
                }
                if (chance) {
                    ModAPI.playSoundsToPlayer(SoundEvents.ITEM_BOOK_PAGE_TURN, player);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    public static void drawTexture(DrawContext context, String path, int x, int y, int width, int height) {
        context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/gui/" + path + ".png")
                , x, y, 0, 0, width, height, width, height);
    }

    public static boolean isInRect(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }

    private static void drawContextText(DrawContext context, int x, int y, int mouseX, int mouseY, String key) {
        MutableText text = Text.translatable(key);
        float scale = Math.min(1f, (float) WIDTH_RANGE / MinecraftClient.getInstance().textRenderer.getWidth(text));
        if (isInRect(mouseX, mouseY, x, y, WIDTH_RANGE, 8)) {
            ModAPI.drawScaledText(context, text.formatted(Formatting.GOLD), x, y, scale, true, true);
        } else
            ModAPI.drawScaledText(context, text.formatted(Formatting.BLUE), x, y, scale, false, false);
    }

    private static void drawPage1(DrawContext context, int guiX, int guiY, int mouseX, int mouseY) {
        context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/item/advanced_enchants_book.png"),
                guiX + 18, guiY + 30, 32, 32, 0.0f, 0.0f, 32, 32, 32, 32);
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.line1").formatted(Formatting.AQUA), guiX + 18, guiY + 18, isEnglish ? 0.9f : 1.3f, true, false);
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.line2").formatted(Formatting.GREEN), guiX + 55, guiY + 38, 1, true, false);
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.line3").formatted(Formatting.GOLD), guiX + 55, guiY + 50, isEnglish ? 0.9f : 1);
        ModAPI.drawLineText(context, "gui.compendium.page1.introduction", guiX + 15, guiY + 80, isEnglish ? 7 : 6, textScale);

        int Page2GuiX = guiX + 146;
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents").formatted(Formatting.BLUE), Page2GuiX + 47, guiY + 18, titleTextScale, true, false);
        drawContextText(context, Page2GuiX + 20, guiY + 34, mouseX, mouseY, "gui.compendium.page1.contents1");
        drawContextText(context, Page2GuiX + 20, guiY + 44, mouseX, mouseY, "gui.compendium.page1.contents2");
        drawContextText(context, Page2GuiX + 20, guiY + 54, mouseX, mouseY, "gui.compendium.page1.contents3");
        drawContextText(context, Page2GuiX + 25, guiY + 64, mouseX, mouseY, "gui.compendium.page1.contents4");
        drawContextText(context, Page2GuiX + 25, guiY + 74, mouseX, mouseY, "gui.compendium.page1.contents5");
        drawContextText(context, Page2GuiX + 25, guiY + 84, mouseX, mouseY, "gui.compendium.page1.contents6");
        drawContextText(context, Page2GuiX + 20, guiY + 94, mouseX, mouseY, "gui.compendium.page1.contents7");
        drawContextText(context, Page2GuiX + 25, guiY + 104, mouseX, mouseY, "gui.compendium.page1.contents8");
        drawContextText(context, Page2GuiX + 25, guiY + 114, mouseX, mouseY, "gui.compendium.page1.contents9");
        drawContextText(context, Page2GuiX + 25, guiY + 124, mouseX, mouseY, "gui.compendium.page1.contents10");
        drawContextText(context, Page2GuiX + 25, guiY + 134, mouseX, mouseY, "gui.compendium.page1.contents11");
    }

    private static void drawPage2(DrawContext context, int guiX, int guiY) {
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents1").formatted(Formatting.GOLD, Formatting.BOLD)
                , guiX + 20, guiY + 14, titleTextScale);
        ModAPI.drawLineText(context, "gui.compendium.page2.introduction", guiX + 15, guiY + 26, 13, textScale);
        ModAPI.drawLineText(context, "gui.compendium.page2.introduction", guiX + 161, guiY + 14, isEnglish ? 11 : 7, 13, textScale);
    }

    private static void drawPage3(DrawContext context, int guiX, int guiY) {
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents2").formatted(Formatting.AQUA, Formatting.BOLD), guiX + 20, guiY + 14, titleTextScale);
        ModAPI.drawLineText(context, "gui.compendium.page3.introduction", guiX + 15, guiY + 26, 13, textScale);
        ModAPI.drawLineText(context, "gui.compendium.page3.introduction", guiX + 161, guiY + 14, isEnglish ? 6 : 2, 13, textScale);
    }

    private static void drawPage4(DrawContext context, int guiX, int guiY) {
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents3").formatted(Formatting.GREEN, Formatting.BOLD), guiX + 20, guiY + 14, titleTextScale);
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page4.title").formatted(Formatting.RED), guiX + 22, guiY + 26, titleTextScale);
        context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/gui/craft1.png"),
                guiX + 18, guiY + 37, 114, 53, 0.0f, 0.0f, 114, 53, 114, 53);
        ModAPI.drawLineText(context, "gui.compendium.page4.introduction", guiX + 18, guiY + 91, 6, textScale);
        ModAPI.drawLineText(context, "gui.compendium.page4.introduction", guiX + 161, guiY + 14, isEnglish ? 10 : 7, 6, textScale);
    }

    private static void drawPage5(DrawContext context, int guiX, int guiY) {
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page5.title").formatted(Formatting.LIGHT_PURPLE), guiX + 22, guiY + 14, titleTextScale);
        context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/gui/craft2.png"),
                guiX + 18, guiY + 25, 114, 53, 0.0f, 0.0f, 114, 53, 114, 53);
        ModAPI.drawLineText(context, "gui.compendium.page5.introduction", guiX + 18, guiY + 80, 7, textScale);
        ModAPI.drawLineText(context, "gui.compendium.page5.introduction", guiX + 161, guiY + 14, isEnglish ? 9 : 7, 7, textScale);
    }

    private static void drawPage6(DrawContext context, int guiX, int guiY) {
        ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page6.title").formatted(Formatting.BLUE), guiX + 22, guiY + 14, titleTextScale);
        context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/gui/craft3.png"),
                guiX + 18, guiY + 25, 114, 53, 0.0f, 0.0f, 114, 53, 114, 53);
        ModAPI.drawLineText(context, "gui.compendium.page6.introduction", guiX + 18, guiY + 80, 7, textScale);
        ModAPI.drawLineText(context, "gui.compendium.page6.introduction", guiX + 161, guiY + 14, isEnglish ? 13 : 9, 7, textScale);
    }

    private void drawOrdinaryEnchantsIntroduction(DrawContext context, int guiX, int guiY, int page) {
        if (page == 7) {
            ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents8").formatted(Formatting.GREEN), guiX + 22, guiY + 26, titleTextScale);
        }
        for (int i = (page - 7) * 6; i <= (page - 6) * 6 - 1; i++) {
            RegistryKey<Enchantment> key;
            try {
                key = ModEnchantments.ORDINARY_LIST.get(i);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            switch (i % 6) {
                case 0 -> drawAnEnchantIntroduction(context, guiX + 18, (i == 0) ? guiY + 39 : guiY + 24, key);
                case 1 ->
                        drawAnEnchantIntroduction(context, guiX + 18, (i == 1) ? guiY + (isEnglish ? 79 : 81) : guiY + (isEnglish ? 67 : 72), key);
                case 2 ->
                        drawAnEnchantIntroduction(context, guiX + 18, guiY + (isEnglish ? ((i == 2) ? 120 : 110) : 126), key);
                case 3 -> drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + 24, key);
                case 4 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 68 : 79), key);
                case 5 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 111 : 131), key);
            }
        }
    }

    private void drawAdvancedEnchantsIntroduction(DrawContext context, int guiX, int guiY, int page) {
        if (page == ordinaryEndPage + 1) {
            ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents9").formatted(Formatting.GOLD), guiX + 22, guiY + 16, titleTextScale);
        }
        for (int i = (page - ordinaryEndPage - 1) * 6; i <= (page - ordinaryEndPage) * 6 - 1; i++) {
            RegistryKey<Enchantment> key;
            try {
                key = ModEnchantments.ADVANCED_LIST.get(i);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            switch (i % 6) {
                case 0 -> drawAnEnchantIntroduction(context, guiX + 18, (i == 0) ? guiY + 38 : guiY + 24, key);
                case 1 ->
                        drawAnEnchantIntroduction(context, guiX + 18, (i == 1) ? guiY + (isEnglish ? 79 : 81) : guiY + (isEnglish ? 67 : 72), key);
                case 2 ->
                        drawAnEnchantIntroduction(context, guiX + 18, guiY + (isEnglish ? ((i == 2) ? 120 : 110) : 126), key);
                case 3 -> drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + 24, key);
                case 4 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 68 : 79), key);
                case 5 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 111 : 131), key);
            }
        }
    }

    private void drawRareEnchantsIntroduction(DrawContext context, int guiX, int guiY, int page) {
        if (page == advancedEndPage + 1) {
            ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents10").formatted(Formatting.AQUA), guiX + 22, guiY + 16, titleTextScale);
        }
        for (int i = (page - advancedEndPage - 1) * 6; i <= (page - advancedEndPage) * 6 - 1; i++) {
            RegistryKey<Enchantment> key;
            try {
                key = ModEnchantments.RARE_LIST.get(i);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            switch (i % 6) {
                case 0 -> drawAnEnchantIntroduction(context, guiX + 18, (i == 0) ? guiY + 38 : guiY + 24, key);
                case 1 ->
                        drawAnEnchantIntroduction(context, guiX + 18, (i == 1) ? guiY + (isEnglish ? 79 : 81) : guiY + (isEnglish ? 67 : 72), key);
                case 2 ->
                        drawAnEnchantIntroduction(context, guiX + 18, guiY + (isEnglish ? ((i == 2) ? 120 : 110) : 126), key);
                case 3 -> drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + 24, key);
                case 4 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 68 : 79), key);
                case 5 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 111 : 131), key);
            }
        }
    }

    private void drawPreciousEnchantsIntroduction(DrawContext context, int guiX, int guiY, int page) {
        if (page == rareEndPage + 1) {
            ModAPI.drawScaledText(context, Text.translatable("gui.compendium.page1.contents11").formatted(Formatting.DARK_PURPLE), guiX + 22, guiY + 16, titleTextScale);
        }
        for (int i = (page - rareEndPage - 1) * 6; i <= (page - rareEndPage) * 6 - 1; i++) {
            RegistryKey<Enchantment> key;
            try {
                key = ModEnchantments.PRECIOUS_LIST.get(i);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            switch (i % 6) {
                case 0 -> drawAnEnchantIntroduction(context, guiX + 18, (i == 0) ? guiY + 38 : guiY + 24, key);
                case 1 ->
                        drawAnEnchantIntroduction(context, guiX + 18, (i == 1) ? guiY + (isEnglish ? 79 : 81) : guiY + (isEnglish ? 67 : 72), key);
                case 2 ->
                        drawAnEnchantIntroduction(context, guiX + 18, guiY + (isEnglish ? ((i == 2) ? 120 : 110) : 120), key);
                case 3 -> drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + 24, key);
                case 4 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 68 : 79), key);
                case 5 ->
                        drawAnEnchantIntroduction(context, guiX + WINDOW_WIDTH + 18, guiY + (isEnglish ? 111 : 131), key);
            }
        }
    }

    private static void drawAnEnchantIntroduction(DrawContext context, int x, int y, RegistryKey<Enchantment> enchantmentKey) {
        if (MinecraftClient.getInstance().getServer() == null) return;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        Enchantment enchantment = ModAPI.keyToEntry(enchantmentKey, MinecraftClient.getInstance().getServer()).value();
        {
            Text nameText = Text.literal(enchantment.description().getString().replace("§e", "§6"));
            Text levelText = Text.translatable("gui.compendium.enchants_list.max_level", enchantment.getMaxLevel());
            float headScale = Math.min(1f, WIDTH_RANGE / (textRenderer.getWidth(nameText) + 6 + textRenderer.getWidth(levelText) * 0.9f));
            ModAPI.drawScaledText(context, nameText, x, y - 3, headScale, true, false);
            ModAPI.drawScaledText(context, levelText, x + (int) (textRenderer.getWidth(nameText) * headScale) + 6, y - 3, 0.9f * headScale, false, false, true);
        }
        {
            Set<String> string = new HashSet<>();
            boolean head = false, chest = false, leg = false, foot = false;
            for (RegistryEntry<Item> item : enchantment.definition().supportedItems()) {
                if (item.isIn(ModTags.ADVANCED_ENCHANTS_SCROLL)) continue;
                if (item.isIn(ItemTags.HEAD_ARMOR_ENCHANTABLE)) head = true;
                if (item.isIn(ItemTags.CHEST_ARMOR_ENCHANTABLE)) chest = true;
                if (item.isIn(ItemTags.LEG_ARMOR_ENCHANTABLE)) leg = true;
                if (item.isIn(ItemTags.FOOT_ARMOR_ENCHANTABLE)) foot = true;
                if (item.isIn(ItemTags.SWORD_ENCHANTABLE)) {
                    string.add((isEnglish) ? "sword," : "剑，");
                }
                if (item.isIn(ItemTags.AXES)) {
                    string.add((isEnglish) ? "axe," : "斧，");
                }
                if (item.value().equals(Items.CROSSBOW)) {
                    string.add((isEnglish) ? "crossbow," : "弩，");
                }
                if (item.value().equals(Items.BOW)) {
                    string.add((isEnglish) ? "bow," : "弓，");
                }
                if (item.value().equals(Items.ELYTRA)) {
                    string.add((isEnglish) ? "elytra," : "鞘翅，");
                }
                if (item.value().equals(Items.SHIELD)) {
                    string.add((isEnglish) ? "shield," : "盾，");
                }
                if (item.value().equals(Items.FISHING_ROD)) {
                    string.add((isEnglish) ? "rod," : "钓鱼竿，");
                }
                if (item.isIn(ItemTags.PICKAXES)) {
                    string.add((isEnglish) ? "pickaxe," : "镐子，");
                } else if (item.isIn(ItemTags.MINING_ENCHANTABLE)) {
                    string.add((isEnglish) ? "tool," : "挖掘工具，");
                }
            }
            if (head && chest && leg && foot) {
                string.add((isEnglish) ? "armor," : "护甲，");
            } else {
                if (head) {
                    string.add((isEnglish) ? "helmet," : "头盔，");
                }
                if (chest) {
                    string.add((isEnglish) ? "chest," : "胸甲，");
                }
                if (leg) {
                    string.add((isEnglish) ? "legs," : "裤腿，");
                }
                if (foot) {
                    string.add((isEnglish) ? "boots," : "靴子，");
                }
            }
            StringBuilder builder = new StringBuilder();
            string.forEach(builder::append);
            if (!builder.isEmpty()) builder.setLength(builder.length() - 1);
            Text itemsText = Text.translatable("gui.compendium.enchants_list.supported_items").append(builder.toString());
            ModAPI.drawScaledText(context, itemsText, x, y + 7, Math.min(0.8f, (float) WIDTH_RANGE / textRenderer.getWidth(itemsText)), false, false, true);
        }
        {
            MutableText text = Text.empty();
            BaseEnchantmentEffect.Chance chance = ModEnchantments.getChance(enchantmentKey, 1);
            final float v = getRoundedChance(chance.chance());
            final String formatString = formatPercent(v);
            switch (chance.type()) {
                case SWITCH -> {
                    int maxLevel = enchantment.getMaxLevel();
                    int groups = 0;
                    MutableText brief = Text.empty();
                    for (int i = 1; i <= maxLevel; i++) {
                        float value = getRoundedChance(ModEnchantments.getChance(enchantmentKey, i).getChance(i));
                        int end = i;
                        while (end < maxLevel) {
                            if (getRoundedChance(ModEnchantments.getChance(enchantmentKey, end + 1).getChance(end + 1)) != value)
                                break;
                            end++;
                        }
                        String percent = formatPercent(value);
                        if (isEnglish) {
                            text.append("Lv").append(String.valueOf(i)).append(i == end ? "" : "~" + end).append(" ").append(percent).append("%").append((end == maxLevel) ? "" : ",");
                        } else {
                            text.append(String.valueOf(i)).append(i == end ? "" : "~" + end).append("级").append(percent).append("%").append((end == maxLevel) ? "" : "，");
                        }
                        brief.append(percent).append("%").append((end == maxLevel) ? "" : "/");
                        groups++;
                        i = end;
                    }
                    if (groups == maxLevel && textRenderer.getWidth(Text.translatable("gui.compendium.enchants_list.chance").append(text)) * 0.7f > WIDTH_RANGE)
                        text = brief;
                }
                case LINEAR ->
                        text = isEnglish ? Text.literal(formatString).append(Text.translatable("gui.compendium.enchants_list.linear"))
                                : (Text.translatable("gui.compendium.enchants_list.linear", formatString).append("%"));
                default -> text = Text.literal(formatString).append("%");
            }
            Text chanceText = Text.translatable("gui.compendium.enchants_list.chance").append(text);
            ModAPI.drawScaledText(context, chanceText, x, y + 17, Math.min(chance.type() == BaseEnchantmentEffect.ChanceType.SWITCH
                    ? 0.7f : 0.8f, (float) WIDTH_RANGE / textRenderer.getWidth(chanceText)), false, false, true);
        }
        {
            String in = Text.translatable("enchantment." + AdvancedEnchantment.MOD_ID + "." + enchantmentKey.getValue().getPath() + ".desc").getString();
            for (int i = 0; i < 2 && !in.isEmpty(); i++) {
                int cut = in.length();
                while (cut > 1 && textRenderer.getWidth(in.substring(0, cut)) > 147) cut--;
                if (cut < in.length()) {
                    int space = in.lastIndexOf(' ', cut);
                    if (space > 0) cut = space;
                }
                ModAPI.drawScaledText(context, Text.literal(in.substring(0, cut)), x, y + 25 + i * (isEnglish ? 7 : 8), 0.8f, false, false, true);
                in = in.substring(cut).trim();
            }
        }
    }

    private static float getRoundedChance(float chanceValue) {
        return Math.round(chanceValue * 1000) / 10f;
    }

    private static String formatPercent(float value) {
        return (value == (int) value) ? String.valueOf((int) value) : String.valueOf(value);
    }
}
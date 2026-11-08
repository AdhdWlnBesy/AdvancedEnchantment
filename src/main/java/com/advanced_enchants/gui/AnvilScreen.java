package com.advanced_enchants.gui;

import com.advanced_enchants.AdvancedEnchantment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * 高级铁砧界面。
 *
 * @see AnvilScreenHandler
 */
public class AnvilScreen extends HandledScreen<AnvilScreenHandler> {
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {//绘制窗口
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - backgroundWidth) / 2;
        int y = (this.height - backgroundHeight) / 2;
        context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/gui/anvil_window.png")
                , x, y, 0, 0, backgroundWidth, backgroundHeight, backgroundWidth, backgroundHeight);
        if (handler.getSlot(0).hasStack() && handler.getSlot(1).hasStack() && !handler.getSlot(2).hasStack()) {
            context.drawGuiTexture(Identifier.ofVanilla("container/anvil/error"), x + 99, y + 45, 28, 21);
        }
    }

    public AnvilScreen(AnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}

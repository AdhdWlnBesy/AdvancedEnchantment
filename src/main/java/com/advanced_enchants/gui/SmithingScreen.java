package com.advanced_enchants.gui;

import com.advanced_enchants.AdvancedEnchantment;
import com.advanced_enchants.event.SmithingNetWorking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * 高级锻造台界面。
 *
 * @see SmithingScreenHandler
 */
public class SmithingScreen extends HandledScreen<SmithingScreenHandler> {
    public SmithingScreen(SmithingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleX = 50;
        this.titleY = 18;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {//绘制窗口
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - backgroundWidth) / 2;
        int y = (this.height - backgroundHeight) / 2;
        context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/gui/smithing_window.png"),
                x, y, 0, 0, backgroundWidth, backgroundHeight, backgroundWidth, backgroundHeight);
        boolean success = SmithingNetWorking.SMITH_SUCCESS;
        boolean fail = SmithingNetWorking.SMITH_FAIL;
        if (handler.player.getWorld().getTime() - SmithingNetWorking.SMITH_TICK > 80) {
            success = false;
            fail = false;
        }
        if (success) {
            context.drawTexture(Identifier.of(AdvancedEnchantment.MOD_ID, "textures/gui/smithing_success.png"), x + 99, y + 45,
                    28, 21, 0, 0, 28, 21, 28, 21);
        } else if (fail || (handler.getSlot(0).hasStack() && handler.getSlot(1).hasStack() &&
                handler.getSlot(2).hasStack() && !handler.getSlot(3).hasStack())) {
            context.drawGuiTexture(Identifier.ofVanilla("container/anvil/error"), x + 99, y + 45, 28, 21);
        }
    }
}

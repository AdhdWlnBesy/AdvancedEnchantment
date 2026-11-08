package com.advanced_enchants.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 附魔触发提示的客户端渲染。
 *
 * @see com.advanced_enchants.event.EnchantTrigNetWorking
 */
public class EnchantTrigRenderer {
    public final static Map<Text, Integer> texts = new HashMap<>();

    // 注册附魔触发提示的客户端渲染。
    public static void mainEvent() {
        HudRenderCallback.EVENT.register(((drawContext, renderTickCounter) -> {
            int i = 0;
            for (Text text : texts.keySet()) {
                RenderSystem.enableBlend();
                float alpha = (texts.get(text) > 25) ? 1 : (float) texts.get(text) / 25;
                RenderSystem.setShaderColor(1, 1, 1, alpha);
                drawContext.drawText(MinecraftClient.getInstance().textRenderer, text, 10, 4 + i * 10, Colors.WHITE, false);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.disableBlend();
                i++;
            }
        }));
        ClientTickEvents.END_CLIENT_TICK.register((client -> {
            Iterator<Map.Entry<Text, Integer>> iterator = texts.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Text, Integer> entry = iterator.next();
                int remain = entry.getValue() - 1;
                if (remain <= 0) {
                    iterator.remove();
                } else {
                    entry.setValue(remain);
                }
            }
        }));
    }
}

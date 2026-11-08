package com.advanced_enchants.event;

import com.advanced_enchants.register.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

/**
 * 首次进服给予附魔指南。
 *
 * @see com.advanced_enchants.event.OpenCompendium
 */
public class GiveCompendium {
    // 首次进服时发放附魔指南。
    public static void mainEvent() {
        ServerPlayerEvents.JOIN.register((player -> {
            if (!player.getCommandTags().contains("has_compendium")) {
                player.giveItemStack(ModItems.ADVANCED_ENCHANTS_COMPENDIUM.getDefaultStack());
                player.addCommandTag("has_compendium");
            }
        }));
    }
}

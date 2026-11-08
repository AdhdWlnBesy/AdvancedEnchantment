package com.advanced_enchants.event;

import com.advanced_enchants.datagen.AdvancementProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

/**
 * 简单进度触发器。
 *
 * @see AdvancementProvider
 */
public class SimpleCriterion extends AbstractCriterion<SimpleCriterion.Conditions> {
    // 触发一次进度。
    public void trigger(ServerPlayerEntity player, String eventType) {
        this.trigger(player, conditions -> conditions.eventType().equals(eventType));
    }

    @Override
    public Codec<Conditions> getConditionsCodec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("event_type").forGetter(Conditions::eventType),
                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player)
        ).apply(instance, Conditions::new));
    }

    public record Conditions(String eventType,
                             Optional<LootContextPredicate> player) implements AbstractCriterion.Conditions {
        public static AdvancementCriterion<Conditions> create(String eventType) {
            return AdvancementProvider.SIMPLE_TRIGGER.create(new Conditions(eventType, Optional.empty()));
        }
    }
}

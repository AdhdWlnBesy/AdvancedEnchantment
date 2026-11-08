package com.advanced_enchants.register;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 模组公共工具方法
 * 可用于其他模组使用
 */
public class ModAPI {
    /**
     * 用server把附魔KEY转成附魔ENTRY
     *
     * @param enchantment 要转的附魔KEY
     * @param server      服务器
     */
    public static RegistryEntry<Enchantment> keyToEntry(RegistryKey<Enchantment> enchantment, MinecraftServer server) {
        return server.getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(enchantment);
    }

    /**
     * 检测stack是否有该附魔
     *
     * @param enchantment 附魔key
     * @param server      服务器
     * @param stack       物品栈
     * @return 是否有附魔
     */
    public static boolean hasEnchantment(RegistryKey<Enchantment> enchantment, MinecraftServer server, ItemStack stack) {
        RegistryEntry<Enchantment> entry = ModAPI.keyToEntry(enchantment, server);
        if (entry == null) return false;
        return EnchantmentHelper.getLevel(entry, stack) > 0;
    }

    /**
     * 获取物品栈所有附魔等级的累计
     *
     * @param stack 物品栈
     * @return 物品栈所有附魔等级的累计
     */
    public static int getEnchantmentsLevel(ItemStack stack) {
        AtomicInteger level = new AtomicInteger();
        stack.getEnchantments().getEnchantments().forEach((enchantment ->
                level.addAndGet(stack.getEnchantments().getLevel(enchantment))));
        return level.get();
    }

    /**
     * 消除附魔
     *
     * @param enchantment 附魔entry
     * @param stack       物品栈
     */
    public static void removeEnchantment(RegistryEntry<Enchantment> enchantment, ItemStack stack) {
        EnchantmentHelper.apply(stack, builder -> builder.remove(enchantmentEntry -> enchantmentEntry.equals(enchantment)));
    }

    /**
     * 用世界获取指定附魔的最大等级
     *
     * @param enchantment 指定附魔
     * @param world       世界
     */
    public static int getEnchantmentMaxLevel(RegistryKey<Enchantment> enchantment, World world) {
        return world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getOrThrow(enchantment).getMaxLevel();
    }

    /**
     * 在玩家位置播放声音
     *
     * @param sound  声音
     * @param player 播放玩家
     */
    public static void playSoundsToPlayer(SoundEvent sound, PlayerEntity player) {
        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    /**
     * 在方块位置播放声音
     *
     * @param sound 声音
     * @param pos 播放位置
     */
    public static void playSoundsToBlock(SoundEvent sound, World world, BlockPos pos, float pitch) {
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0f, pitch);
    }

    /**
     * 修改最大生命并回满血
     *
     * @param entity 实体
     * @param maxHealth 最大生命值
     */
    public static void setMaxHealth(LivingEntity entity, float maxHealth) {
        if (entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH) != null) {
            Objects.requireNonNull(entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(maxHealth);
            entity.setHealth(maxHealth);
        }
    }

    /**
     * 在范围内查找指定类型的方块实体
     * 类似于{@code world.getBlockEntity(pos)}
     * @param box 查找范围
     * @param blockEntityClass 方块实体类型
     * @param world 世界
     * @param lookupOnce 是否只查找一次
     */
    public static <T extends BlockEntity> List<T> getBlockEntities(Box box, Class<T> blockEntityClass, World world, boolean lookupOnce) {
        var posStream = IntStream.rangeClosed((int) box.minX, (int) box.maxX).boxed()
                .flatMap(x -> IntStream.rangeClosed((int) box.minY, (int) box.maxY).boxed()
                        .flatMap(y -> IntStream.rangeClosed((int) box.minZ, (int) box.maxZ)
                                .mapToObj(z -> new BlockPos(x, y, z)))).map(world::getBlockEntity)
                .filter(Objects::nonNull).filter(blockEntityClass::isInstance)
                .map(blockEntityClass::cast);
        if (lookupOnce) {
            return posStream.findFirst().map(List::of).orElse(List.of());
        } else {
            return posStream.collect(Collectors.toList());
        }
    }


    /**
     * 绘制字体
     *
     * @param context   画笔
     * @param text      绘制text
     * @param x         绘制起始x位置
     * @param y         绘制起始y位置
     * @param textScale 字号
     * @param bold      是否粗体
     * @param underLine 是否有下划线
     * @param isBlack   是否黑色
     * @apiNote 此函数可无颜色可有色，若想要显示颜色，请把isBlack设成false
     */
    public static void drawScaledText(DrawContext context, Text text, int x, int y, float textScale, boolean bold, boolean underLine, boolean isBlack) {
        var textRenderer = MinecraftClient.getInstance().textRenderer;
        if (textRenderer == null) return;
        Text styledText = text.copy().setStyle(text.getStyle().withBold(bold).withUnderline(underLine));
        context.getMatrices().push();
        context.getMatrices().scale(textScale, textScale, 1);
        int black = -1;
        if (isBlack) {
            black = 0x000000;
        }
        context.drawText(textRenderer, styledText, (int) (x / textScale), (int) (y / textScale), black, false);
        context.getMatrices().pop();
    }

    /**
     * 绘制带有颜色字体
     *
     * @apiNote 此函数是drawScaledText的省略，指定颜色方法在text后面加{@code .formatted(Formatting.颜色)}
     */
    public static void drawScaledText(DrawContext context, Text text, int x, int y, float textScale, boolean bold, boolean underLine) {
        drawScaledText(context, text, x, y, textScale, bold, underLine, false);
    }

    public static void drawScaledText(DrawContext context, Text text, int x, int y, float textScale) {
        drawScaledText(context, text, x, y, textScale, false, false);
    }

    public static void drawLineText(DrawContext context, String texts, int x, int y, int size, int sizePlus, float textScale) {
        for (int i = 0; i < size; i++) {
            Text text = Text.translatable(texts + (i + 1 + sizePlus));
            float width = MinecraftClient.getInstance().textRenderer.getWidth(text) * textScale;
            drawScaledText(context, text, x, y + 10 * i, width > 118 ? 118 * textScale / width : textScale, false, false, true);
        }
    }

    /**
     * 按行绘制多行文字，行距取默认值
     * @param context 画笔
     * @param texts 绘制text的key
     * @param x x坐标
     * @param y y坐标
     * @param size 绘制行数
     * @param textScale 字体大小
     */
    public static void drawLineText(DrawContext context, String texts, int x, int y, int size, float textScale) {
        drawLineText(context, texts, x, y, size, 0, textScale);
    }

    /**
     * 绘制一个物品，兼容方块，带数字
     *
     * @param stack 绘制物品栈
     * @param pos   绘制位置
     * @param world 绘制世界
     * @apiNote 需在WorldRenderEvents或BlockEntityRenderer等仅客户端每刻执行的地方调用
     */
    public static void showItem(ItemStack stack, BlockPos pos, World world) {
        MinecraftClient mc = MinecraftClient.getInstance();
        MatrixStack matrix = new MatrixStack();
        if (stack == null || pos == null || world == null) return;
        matrix.push();
        Camera camera = mc.getEntityRenderDispatcher().camera;
        double cx = pos.getX() - camera.getPos().x;
        double cy = pos.getY() - camera.getPos().y;
        double cz = pos.getZ() - camera.getPos().z;
        boolean isBlock = stack.getItem() instanceof BlockItem;
        VertexConsumerProvider.Immediate immediate = mc.getBufferBuilders().getEntityVertexConsumers();
        matrix.translate(cx + 0.5, cy + 0.6D, cz + 0.5);
        if (isBlock) {
            matrix.scale(0.5f, 0.5f, 0.5f);
            BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            VertexConsumer vc = immediate.getBuffer(RenderLayer.getCutoutMipped());
            mc.getBlockRenderManager().renderBlock(state, pos, mc.world, matrix, vc, false, world.random);
        } else {
            matrix.scale(0.8f, 0.8f, 0.8f);
            matrix.multiply(camera.getRotation());
            mc.getItemRenderer().renderItem(stack, ModelTransformationMode.GUI, 15728880, 0, matrix, immediate, world, 0);
        }
        matrix.pop();
        if (stack.getCount() > 1) {
            MatrixStack textMatrix = new MatrixStack();
            textMatrix.push();
            textMatrix.translate(cx + 0.5, cy + 0.1, cz + 0.5);
            if (isBlock) {
                textMatrix.translate(0.5D, 0.5D, 0.5D);
                textMatrix.multiply(new Quaternionf(0, camera.getRotation().y, 0, camera.getRotation().w).normalize());
            } else {
                textMatrix.translate(0.2D, 0.2D, 0.2D);
                textMatrix.multiply(camera.getRotation());
            }
            textMatrix.scale(0.04f, 0.04f, 0.04f);
            textMatrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
            mc.textRenderer.draw(String.valueOf(stack.getCount()), 0, 0, 0xffffff, true, textMatrix.peek().getPositionMatrix(),
                    immediate, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
            textMatrix.pop();
        }
        immediate.draw();
    }
    /**
     * 以文字中心为旋转中心绘制，避免文字绕左上角旋转
     * @param text 绘制文字
     * @param pos   绘制位置
     * @param matrixStack 绘制MatrixStack
     * @apiNote 需在WorldRenderEvents或BlockEntityRenderer等仅客户端每刻执行的地方调用
     */
    public static void drawTextCentered(Text text, Vec3d pos, MatrixStack matrixStack) {
        matrixStack.push();
        MinecraftClient mc = MinecraftClient.getInstance();
        VertexConsumerProvider.Immediate immediate = mc.getBufferBuilders().getEntityVertexConsumers();
        matrixStack.translate(pos.getX(), pos.getY(), pos.getZ());
        matrixStack.scale(0.04f, 0.04f, 0.04f);
        matrixStack.multiply(MinecraftClient.getInstance().getEntityRenderDispatcher().camera.getRotation());
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        float x = -mc.textRenderer.getWidth(text) / 2f;
        float y = -mc.textRenderer.fontHeight / 2f;
        mc.textRenderer.draw(text, x, y, 0xFFFFFFFF, false,
                matrixStack.peek().getPositionMatrix(), immediate, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
        matrixStack.pop();
        immediate.draw();
    }
}

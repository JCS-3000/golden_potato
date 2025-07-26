package org.jcs.goldenpotato.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.Random;

public class PotatOSItem extends Item {
    private static final String COOLDOWN_KEY = "potatos_cooldown";
    private static final int COOLDOWN_TICKS = 80; // 4 seconds

    private static final ResourceLocation[] SOUNDS = new ResourceLocation[] {
            new ResourceLocation("goldenpotato", "potatos_1"),
            new ResourceLocation("goldenpotato", "potatos_2"),
            new ResourceLocation("goldenpotato", "potatos_3"),
            new ResourceLocation("goldenpotato", "potatos_4"),
            new ResourceLocation("goldenpotato", "potatos_5"),
            new ResourceLocation("goldenpotato", "potatos_6"),
            new ResourceLocation("goldenpotato", "potatos_7"),
    };

    public PotatOSItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) return InteractionResultHolder.pass(player.getItemInHand(hand));

        long now = level.getGameTime();
        long last = player.getPersistentData().getLong(COOLDOWN_KEY);

        if (now - last < COOLDOWN_TICKS) {
            player.sendSystemMessage(Component.literal("Moron."));
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        player.getPersistentData().putLong(COOLDOWN_KEY, now);

        int idx = new Random().nextInt(SOUNDS.length);
        ResourceLocation soundRL = SOUNDS[idx];
        SoundEvent sound = SoundEvent.createVariableRangeEvent(soundRL);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);

        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}

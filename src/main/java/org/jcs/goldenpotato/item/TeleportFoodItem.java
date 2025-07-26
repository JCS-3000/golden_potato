package org.jcs.goldenpotato.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.sounds.SoundSource;
import org.jcs.goldenpotato.config.GoldenPotatoConfig;
import org.jcs.goldenpotato.data.TeleportCooldowns;
import org.jcs.goldenpotato.data.TeleportCooldownsProvider;
import org.jcs.goldenpotato.registry.ModItems;

public class TeleportFoodItem extends Item {
    private final TeleportType type;
    private final boolean isEnchanted;

    public TeleportFoodItem(Properties properties, TeleportType type, boolean isEnchanted) {
        super(properties);
        this.type = type;
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnchanted || super.isFoil(stack);
    }

    private void playTeleportEffects(Level level, double x, double y, double z) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(
                    net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK,
                    x, y + 1.0, z,
                    40,
                    0.5, 0.75, 0.5,
                    0.15
            );
        }
        level.playSound(
                null,
                x, y, z,
                net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.PLAYERS,
                1.0f,
                1.0f
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof ServerPlayer player)) {
            return super.finishUsingItem(stack, level, entity);
        }

        var cooldownCap = player.getCapability(TeleportCooldownsProvider.COOLDOWN_CAPABILITY);
        if (cooldownCap.isPresent()) {
            TeleportCooldowns cooldowns = cooldownCap.orElseThrow(() -> new IllegalStateException("TeleportCooldowns capability missing!"));
            long now = System.currentTimeMillis();

            switch (type) {
                case DEATH -> {
                    long lastUsed = cooldowns.getLastDeathTp();
                    long cooldownMillis = GoldenPotatoConfig.COOLDOWN_GOLDEN_POTATO_MINUTES.get() * 60_000L;

                    if (now - lastUsed >= cooldownMillis) {
                        player.getLastDeathLocation().ifPresentOrElse(globalPos -> {
                            if (player.level().dimension() == globalPos.dimension()) {
                                BlockPos pos = globalPos.pos();

                                playTeleportEffects(level, player.getX(), player.getY(), player.getZ());
                                player.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                                playTeleportEffects(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

                                cooldowns.setLastDeathTp(now);
                                player.getCooldowns().addCooldown(this, (int)(GoldenPotatoConfig.COOLDOWN_GOLDEN_POTATO_MINUTES.get() * 60 * 20));
                                player.sendSystemMessage(Component.literal("Teleported to last death location!"));

                                // Advancement counter logic
                                if (!level.isClientSide) {
                                    int uses = player.getPersistentData().getInt("goldenpotato_death_uses") + 1;
                                    player.getPersistentData().putInt("goldenpotato_death_uses", uses);
                                    if (uses == 10) {
                                        var adv = player.server.getAdvancements().getAdvancement(
                                                new ResourceLocation("goldenpotato", "sounds_like_a_skill_issue")
                                        );
                                        if (adv != null) {
                                            player.getAdvancements().award(adv, "golden_potato_skill_issue");
                                        }
                                    }
                                }
                            } else {
                                player.sendSystemMessage(Component.literal("Death occurred in a different dimension."));
                            }
                        }, () -> {
                            player.sendSystemMessage(Component.literal("No death location recorded."));
                        });
                    } else {
                        player.sendSystemMessage(Component.literal("Golden Potato is on cooldown."));
                    }
                }

                case BED -> {
                    long lastUsed = cooldowns.getLastBedTp();
                    long cooldownMillis = GoldenPotatoConfig.COOLDOWN_GOLDEN_SPUD_MINUTES.get() * 60_000L;
                    BlockPos bed = player.getRespawnPosition();
                    if (now - lastUsed >= cooldownMillis && bed != null) {
                        playTeleportEffects(level, player.getX(), player.getY(), player.getZ());
                        player.teleportTo(bed.getX() + 0.5, bed.getY(), bed.getZ() + 0.5);
                        playTeleportEffects(level, bed.getX() + 0.5, bed.getY(), bed.getZ() + 0.5);

                        cooldowns.setLastBedTp(now);
                        player.getCooldowns().addCooldown(this, (int)(GoldenPotatoConfig.COOLDOWN_GOLDEN_SPUD_MINUTES.get() * 60 * 20));
                        player.sendSystemMessage(Component.literal("Teleported to bed!"));
                    } else if (bed == null) {
                        player.sendSystemMessage(Component.literal("No bed or respawn point set."));
                    } else {
                        player.sendSystemMessage(Component.literal("Golden Spud is on cooldown."));
                    }
                }

                case RANDOM -> {
                    long lastUsed = cooldowns.getLastRandomTp();
                    long cooldownMillis = GoldenPotatoConfig.COOLDOWN_GOLDEN_CHORUS_FRUIT_MINUTES.get() * 60_000L;

                    if (now - lastUsed < cooldownMillis) {
                        player.sendSystemMessage(Component.literal("Golden Chorus Fruit is on cooldown."));
                        break;
                    }

                    final int maxTries = 10;
                    final int radius = 500; // 1000x1000 area
                    final net.minecraft.util.RandomSource rand = player.getRandom();
                    boolean found = false;

                    for (int i = 0; i < maxTries; i++) {
                        double dx = (rand.nextDouble() - 0.5) * 2 * radius;
                        double dz = (rand.nextDouble() - 0.5) * 2 * radius;
                        double x = player.getX() + dx;
                        double z = player.getZ() + dz;
                        BlockPos samplePos = BlockPos.containing(x, player.getY(), z);
                        BlockPos topPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, samplePos);
                        BlockState surface = level.getBlockState(topPos);

                        if (surface.isAir() || surface.getBlock() instanceof LiquidBlock) continue;

                        BlockPos head = topPos.above();
                        if (level.isEmptyBlock(head)) {
                            playTeleportEffects(level, player.getX(), player.getY(), player.getZ());
                            player.teleportTo(x, head.getY(), z);
                            playTeleportEffects(level, x, head.getY(), z);

                            cooldowns.setLastRandomTp(now);
                            player.getCooldowns().addCooldown(this, (int)(GoldenPotatoConfig.COOLDOWN_GOLDEN_CHORUS_FRUIT_MINUTES.get() * 60 * 20));
                            player.sendSystemMessage(Component.literal("Randomly teleported!"));
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        player.sendSystemMessage(Component.literal("No safe location found for teleport."));
                    }
                }
            }
        } else {
            player.sendSystemMessage(Component.literal("Teleport capability not found!"));
        }

        return super.finishUsingItem(stack, level, entity);
    }
}

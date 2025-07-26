package org.jcs.goldenpotato.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TeleportCooldownsProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<TeleportCooldowns> COOLDOWN_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final TeleportCooldowns instance = new TeleportCooldowns();

    @Override
    public @NotNull CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(@NotNull CompoundTag tag) {
        instance.deserializeNBT(tag);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == COOLDOWN_CAPABILITY ? LazyOptional.of(() -> instance).cast() : LazyOptional.empty();
    }

    public static void attach(Player player, AttachCapabilitiesEvent<?> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new net.minecraft.resources.ResourceLocation("goldenpotato", "cooldowns"),
                    new TeleportCooldownsProvider());
        }
    }
}

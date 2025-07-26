package org.jcs.goldenpotato.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jcs.goldenpotato.data.TeleportCooldownsProvider;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;
import org.jcs.goldenpotato.registry.ModItems;

public class ModEvents {
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            TeleportCooldownsProvider.attach(player, event);
        }
    }
    @SubscribeEvent
    public void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event) {
        var player = event.getEntity();
        var adv = event.getAdvancement().getId();
        if (adv.equals(new ResourceLocation("goldenpotato", "sounds_like_a_skill_issue"))) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("When life gives you \"lemons\"... "));
            player.addItem(new net.minecraft.world.item.ItemStack(ModItems.POTAT_OS.get()));
        }
    }
    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        event.getOriginal().getCapability(TeleportCooldownsProvider.COOLDOWN_CAPABILITY).ifPresent(oldStore -> {
            event.getEntity().getCapability(TeleportCooldownsProvider.COOLDOWN_CAPABILITY).ifPresent(newStore -> {
                newStore.setLastDeathTp(oldStore.getLastDeathTp());
                newStore.setLastBedTp(oldStore.getLastBedTp());
                newStore.setLastRandomTp(oldStore.getLastRandomTp());
            });
        });
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }
}

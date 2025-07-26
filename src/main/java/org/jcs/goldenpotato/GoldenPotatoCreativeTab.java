package org.jcs.goldenpotato;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jcs.goldenpotato.config.GoldenPotatoConfig;
import org.jcs.goldenpotato.registry.ModItems;

public class GoldenPotatoCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GoldenPotato.MODID);

    public static final RegistryObject<CreativeModeTab> GOLDEN_POTATO_TAB =
            CREATIVE_TABS.register("golden_potato_tab", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.goldenpotato.main"))
                            .icon(() -> new ItemStack(ModItems.GOLDEN_POTATO.get()))
                            .displayItems((params, output) -> {
                                if (GoldenPotatoConfig.ENABLE_GOLDEN_POTATO.get()) output.accept(ModItems.GOLDEN_POTATO.get());
                                if (GoldenPotatoConfig.ENABLE_ENCHANTED_GOLDEN_POTATO.get()) output.accept(ModItems.ENCHANTED_GOLDEN_POTATO.get());
                                if (GoldenPotatoConfig.ENABLE_GOLDEN_SPUD.get()) output.accept(ModItems.GOLDEN_SPUD.get());
                                if (GoldenPotatoConfig.ENABLE_ENCHANTED_GOLDEN_SPUD.get()) output.accept(ModItems.ENCHANTED_GOLDEN_SPUD.get());
                                if (GoldenPotatoConfig.ENABLE_GOLDEN_CHORUS_FRUIT.get()) output.accept(ModItems.GOLDEN_CHORUS_FRUIT.get());
                                if (GoldenPotatoConfig.ENABLE_ENCHANTED_GOLDEN_CHORUS_FRUIT.get()) output.accept(ModItems.ENCHANTED_GOLDEN_CHORUS_FRUIT.get());
                            })
                            .build()
            );

    public static void register(IEventBus bus) {
        CREATIVE_TABS.register(bus);
    }
}

package org.jcs.goldenpotato;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import org.jcs.goldenpotato.registry.ModEvents;
import org.jcs.goldenpotato.registry.ModItems;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.jcs.goldenpotato.config.GoldenPotatoConfig;

@Mod(GoldenPotato.MODID)
public class GoldenPotato {
    public static final String MODID = "goldenpotato";

    public GoldenPotato() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modBus);
        modBus.addListener(this::addItemsToTabs);
        GoldenPotatoCreativeTab.register(modBus);
        ModEvents.register();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GoldenPotatoConfig.SPEC);
    }

    private void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == net.minecraft.world.item.CreativeModeTabs.FOOD_AND_DRINKS) {
            if (GoldenPotatoConfig.ENABLE_GOLDEN_POTATO.get()) event.accept(ModItems.GOLDEN_POTATO);
            if (GoldenPotatoConfig.ENABLE_ENCHANTED_GOLDEN_POTATO.get()) event.accept(ModItems.ENCHANTED_GOLDEN_POTATO);
            if (GoldenPotatoConfig.ENABLE_GOLDEN_SPUD.get()) event.accept(ModItems.GOLDEN_SPUD);
            if (GoldenPotatoConfig.ENABLE_ENCHANTED_GOLDEN_SPUD.get()) event.accept(ModItems.ENCHANTED_GOLDEN_SPUD);
            if (GoldenPotatoConfig.ENABLE_GOLDEN_CHORUS_FRUIT.get()) event.accept(ModItems.GOLDEN_CHORUS_FRUIT);
            if (GoldenPotatoConfig.ENABLE_ENCHANTED_GOLDEN_CHORUS_FRUIT.get()) event.accept(ModItems.ENCHANTED_GOLDEN_CHORUS_FRUIT);
        }
    }
}

package org.jcs.goldenpotato.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import org.jcs.goldenpotato.GoldenPotato;
import org.jcs.goldenpotato.config.GoldenPotatoConfig;
import org.jcs.goldenpotato.item.PotatOSItem;
import org.jcs.goldenpotato.item.TeleportFoodItem;
import org.jcs.goldenpotato.item.TeleportType;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GoldenPotato.MODID);

    private static final FoodProperties TELEPORT_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationMod(0.3f).alwaysEat().build();

    public static final RegistryObject<Item> GOLDEN_POTATO =
            ITEMS.register("golden_potato", () ->
                    new TeleportFoodItem(
                            new Item.Properties()
                                    .food(TELEPORT_FOOD)
                                    .stacksTo(64)
                                    .rarity(Rarity.RARE),
                            TeleportType.DEATH,
                            false
                    ));

    public static final RegistryObject<Item> ENCHANTED_GOLDEN_POTATO =
            ITEMS.register("enchanted_golden_potato", () ->
                    new TeleportFoodItem(
                            new Item.Properties()
                                    .food(TELEPORT_FOOD)
                                    .stacksTo(64)
                                    .rarity(Rarity.EPIC),
                            TeleportType.DEATH,
                            true
                    ));

    public static final RegistryObject<Item> GOLDEN_SPUD =
            ITEMS.register("golden_spud", () ->
                    new TeleportFoodItem(
                            new Item.Properties()
                                    .food(TELEPORT_FOOD)
                                    .stacksTo(64)
                                    .rarity(Rarity.RARE),
                            TeleportType.BED,
                            false
                    ));

    public static final RegistryObject<Item> ENCHANTED_GOLDEN_SPUD =
            ITEMS.register("enchanted_golden_spud", () ->
                    new TeleportFoodItem(
                            new Item.Properties()
                                    .food(TELEPORT_FOOD)
                                    .stacksTo(64)
                                    .rarity(Rarity.EPIC),
                            TeleportType.BED,
                            true
                    ));

    public static final RegistryObject<Item> GOLDEN_CHORUS_FRUIT =
            ITEMS.register("golden_chorus_fruit", () ->
                    new TeleportFoodItem(
                            new Item.Properties()
                                    .food(TELEPORT_FOOD)
                                    .stacksTo(64)
                                    .rarity(Rarity.RARE),
                            TeleportType.RANDOM,
                            false
                    ));

    public static final RegistryObject<Item> ENCHANTED_GOLDEN_CHORUS_FRUIT =
            ITEMS.register("enchanted_golden_chorus_fruit", () ->
                    new TeleportFoodItem(
                            new Item.Properties()
                                    .food(TELEPORT_FOOD)
                                    .stacksTo(64)
                                    .rarity(Rarity.EPIC),
                            TeleportType.RANDOM,
                            true
                    ));
    public static final RegistryObject<Item> POTAT_OS =
            ITEMS.register("potat_os", () -> new PotatOSItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

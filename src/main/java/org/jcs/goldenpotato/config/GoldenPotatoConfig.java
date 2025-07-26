package org.jcs.goldenpotato.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GoldenPotatoConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue ENABLE_GOLDEN_POTATO;
    public static final ForgeConfigSpec.BooleanValue ENABLE_ENCHANTED_GOLDEN_POTATO;
    public static final ForgeConfigSpec.BooleanValue ENABLE_GOLDEN_SPUD;
    public static final ForgeConfigSpec.BooleanValue ENABLE_ENCHANTED_GOLDEN_SPUD;
    public static final ForgeConfigSpec.BooleanValue ENABLE_GOLDEN_CHORUS_FRUIT;
    public static final ForgeConfigSpec.BooleanValue ENABLE_ENCHANTED_GOLDEN_CHORUS_FRUIT;

    public static final ForgeConfigSpec.IntValue COOLDOWN_GOLDEN_POTATO_MINUTES;
    public static final ForgeConfigSpec.IntValue COOLDOWN_GOLDEN_SPUD_MINUTES;
    public static final ForgeConfigSpec.IntValue COOLDOWN_GOLDEN_CHORUS_FRUIT_MINUTES;

    public static final ForgeConfigSpec.BooleanValue LOOT_GOLDEN_POTATO_VILLAGES;
    public static final ForgeConfigSpec.BooleanValue LOOT_GOLDEN_SPUD_ANCIENT_CITIES;
    public static final ForgeConfigSpec.BooleanValue LOOT_GOLDEN_CHORUS_FRUIT_END_CITIES;

    static {
        BUILDER.push("Item Settings");

        ENABLE_GOLDEN_POTATO = BUILDER.comment("Enable Golden Potato")
                .define("enableGoldenPotato", true);
        ENABLE_ENCHANTED_GOLDEN_POTATO = BUILDER.comment("Enable Enchanted Golden Potato")
                .define("enableEnchantedGoldenPotato", false);
        ENABLE_GOLDEN_SPUD = BUILDER.comment("Enable Golden Spud")
                .define("enableGoldenSpud", true);
        ENABLE_ENCHANTED_GOLDEN_SPUD = BUILDER.comment("Enable Enchanted Golden Spud")
                .define("enableEnchantedGoldenSpud", false);
        ENABLE_GOLDEN_CHORUS_FRUIT = BUILDER.comment("Enable Golden Chorus Fruit")
                .define("enableGoldenChorusFruit", true);
        ENABLE_ENCHANTED_GOLDEN_CHORUS_FRUIT = BUILDER.comment("Enable Enchanted Golden Chorus Fruit")
                .define("enableEnchantedGoldenChorusFruit", false);

        BUILDER.pop();
        BUILDER.push("Cooldowns (in minutes)");

        COOLDOWN_GOLDEN_POTATO_MINUTES = BUILDER.comment("Golden Potato cooldown in minutes")
                .defineInRange("goldenPotatoCooldown", 0, 0, 1440);
        COOLDOWN_GOLDEN_SPUD_MINUTES = BUILDER.comment("Golden Spud cooldown in minutes")
                .defineInRange("goldenSpudCooldown", 5, 0, 1440);
        COOLDOWN_GOLDEN_CHORUS_FRUIT_MINUTES = BUILDER.comment("Golden Chorus Fruit cooldown in minutes")
                .defineInRange("goldenChorusFruitCooldown", 60, 0, 1440);

        LOOT_GOLDEN_POTATO_VILLAGES = BUILDER.define("lootGoldenPotatoVillages", true);
        LOOT_GOLDEN_SPUD_ANCIENT_CITIES = BUILDER.define("lootGoldenSpudAncientCities", true);
        LOOT_GOLDEN_CHORUS_FRUIT_END_CITIES = BUILDER.define("lootGoldenChorusFruitEndCities", true);

        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}

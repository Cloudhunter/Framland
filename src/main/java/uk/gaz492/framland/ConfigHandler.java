package uk.gaz492.framland;

import net.minecraftforge.common.config.Config;
import uk.gaz492.framland.util.ModInformation;

@Config(modid = ModInformation.MOD_ID)
public class ConfigHandler {

    public static SubCategory framlandGeneral = new SubCategory();

    public static class SubCategory {
        @Config.RequiresMcRestart
        @Config.Comment({
                "Enabled/Disables crafting the thunder hoe",
                "NOT IMPLEMENTED YET"
        })
        public boolean allowCraftingThunderHoe = false;

        @Config.RequiresMcRestart
        @Config.Comment({
                "Growth speed multiplier"
        })
        public int growthSpeedMultiplier = 2;
    }
}

package uk.gaz492.framland;

import net.minecraftforge.common.config.Config;
import uk.gaz492.framland.util.ModInformation;

@Config(modid = ModInformation.MOD_ID)
public class ConfigHandler {

    @Config.Comment({"Settings for the Drit block"})
    public static DritCategory dritConfig = new DritCategory();
    @Config.Comment({"Settings for the Framland block"})
    public static FramlandCategory framlandConfig = new FramlandCategory();

    public static class DritCategory {

        @Config.Comment({"Maximum RF the drit block can hold"})
        public int maxRF = 50000;

        @Config.Comment({"RF needed per tick to grow crops"})
        public int rfToGrow = 1000;

        @Config.Comment({"Sets the minimum amount of ticks where crops can grow"})
        public int minGrowthTicks = 400;

        @Config.Comment({"Sets the maximum amount of ticks where crops can grow"})
        public int maxGrowthTicks = 600;
    }

    public static class FramlandCategory {

        @Config.Comment({"Maximum RF the drit block can hold"})
        public int maxRF = 1000000;

        @Config.Comment({"RF needed pertick to grow crops"})
        public int rfToGrow = 100000;

        @Config.Comment({"Sets the minimum amount of ticks where crops can grow"})
        public int minGrowthTicks = 20;

        @Config.Comment({"Sets the maximum amount of ticks where crops can grow"})
        public int maxGrowthTicks = 120;
    }
}

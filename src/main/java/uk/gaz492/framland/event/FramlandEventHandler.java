package uk.gaz492.framland.event;

import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import uk.gaz492.framland.ModBlocks;
import uk.gaz492.framland.blocks.BlockDrit;
import uk.gaz492.framland.blocks.BlockFramland;
import uk.gaz492.framland.util.ModInformation;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ModInformation.MOD_ID)
public class FramlandEventHandler {

    public static final ArrayList<EntityLightningBolt> BOLTS = new ArrayList<>();

    @SubscribeEvent
    public static void onEntityConstructed(EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityLightningBolt) {
            BOLTS.add((EntityLightningBolt) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !BOLTS.isEmpty()) {
            for (EntityLightningBolt bolt : BOLTS) {
                BlockPos pos = new BlockPos(bolt);

                for (int z = -2; z <= 2; z++) {
                    for (int x = -2; x <= 2; x++) {
                        for (int y = -1; y <= 1; y++) {
                            BlockPos pos1 = pos.add(x, y, z);
                            IBlockState state = bolt.world.getBlockState(pos1);

                            if (state.getBlock() instanceof BlockDrit) {
//                                bolt.world.spawnParticle(EnumParticleTypes.NOTE, pos1.getX() + 1.0D, pos1.getY() + 1.5D, pos1.getZ() + 1.0D, 24.0D, 0.0D, 0.0D);
//                                bolt.world.playEvent(1033, pos1.up(1), 0);
                                bolt.world.setBlockState(pos, ModBlocks.blockFramland.getDefaultState(), 3);
                            }
                        }
                    }
                }
            }
            BOLTS.clear();
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ModInformation.MOD_ID)) {
            ConfigManager.sync(ModInformation.MOD_ID, Config.Type.INSTANCE);
        }
    }
}

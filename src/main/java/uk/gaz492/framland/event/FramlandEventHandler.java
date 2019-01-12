package uk.gaz492.framland.event;

import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import uk.gaz492.framland.ModBlocks;
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

                            if (state.getBlock() instanceof BlockFarmland) {
                                bolt.world.setBlockState(pos1, ModBlocks.blockFramland.getDefaultState(), 3);
                                bolt.world.playEvent(1033, pos1, 0);
                            }
                        }
                    }
                }
            }
            BOLTS.clear();
        }
    }

    @SubscribeEvent
    public static void onBlockEvent(EntityEvent event) {
//        Entity eventEnity = event.getEntity();
//        if (eventEnity instanceof EntityLightningBolt) {
//            System.out.println(eventEnity);
//            System.out.println(String.format("Lightning Location X:%s Y:%s Z:%s", eventEnity.posX, eventEnity.posY, eventEnity.posZ));
//        }
    }
}

package itsamysterious.mods.reallifemod.core.items.vehicles;

import itsamysterious.mods.reallifemod.core.RLMExtItem;
import itsamysterious.mods.reallifemod.core.entities.EntityPylon;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemPylon extends RLMExtItem {

	public ItemPylon() {
		super("itemPylon");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock() == Blocks.air) {
			EntityPylon p = new EntityPylon(worldIn, pos.getX(), pos.getY(), pos.getZ());
			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(p);
			}
		}
		return true;
	}

}

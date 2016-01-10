package itsamysterious.mods.reallifemod.core.items.lifesystem;

import itsamysterious.mods.reallifemod.core.RLMExtItem;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBottle extends Item {

	public ItemBottle() {
		setUnlocalizedName("item_Bottle");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);

		if (movingobjectposition == null) {
			return stack;
		}

		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			BlockPos blockpos = movingobjectposition.getBlockPos();
			if (worldIn.getBlockState(blockpos).getBlock() == Blocks.water) {
				playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);
				playerIn.inventory.addItemStackToInventory(new ItemStack(new ItemBottle_Filled(), 1));
			}
		}

		return stack;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {

		return false;
	}

}

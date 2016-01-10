package itsamysterious.mods.reallifemod.core.items.lifesystem;

import itsamysterious.mods.reallifemod.core.RLMExtItem;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBottle_Filled extends Item {

	public ItemBottle_Filled() {
		this.setUnlocalizedName("bottle_Filled");
		GameRegistry.registerItem(this, "bottle_Filled");
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		playerIn.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		RLMPlayerProps.get(playerIn).WaterLevel += 5f;
		return super.onItemUseFinish(stack, worldIn, playerIn);
	}
}

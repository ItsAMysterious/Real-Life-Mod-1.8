package itsamysterious.mods.reallifemod.core.items;

import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCamera extends Item {
	public ItemCamera() {
		setUnlocalizedName("itemCamera");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
		setCreativeTab(RealLifeMod.Technologie);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.GREEN+"LATER YOU WILL BE ABLE TO");
		tooltip.add("Use your camera to make better screenshots.");
		tooltip.add("You can view them with your digital picture frame!");
		
	}
}

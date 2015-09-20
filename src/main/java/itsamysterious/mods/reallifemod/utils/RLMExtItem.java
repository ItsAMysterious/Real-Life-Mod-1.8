package itsamysterious.mods.reallifemod.utils;

import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RLMExtItem extends Item{
	
	public RLMExtItem(String theName) {
		this.setUnlocalizedName(theName);
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
	}

}

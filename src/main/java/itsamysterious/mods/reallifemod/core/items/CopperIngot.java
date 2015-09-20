package itsamysterious.mods.reallifemod.core.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CopperIngot extends Item{
	public CopperIngot() {
		setUnlocalizedName("CopperIngot");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));	}

}

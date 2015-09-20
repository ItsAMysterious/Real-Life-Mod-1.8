package itsamysterious.mods.reallifemod.core.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCoil extends Item{
	public ItemCoil() {
		setUnlocalizedName("itemCoil");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));	}

}

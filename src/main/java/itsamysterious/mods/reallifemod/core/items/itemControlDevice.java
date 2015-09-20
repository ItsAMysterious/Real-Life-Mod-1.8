package itsamysterious.mods.reallifemod.core.items;

import itsamysterious.mods.reallifemod.RealLifeMod;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class itemControlDevice extends Item{
	public itemControlDevice() {
		setUnlocalizedName("controlDevice");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
		setCreativeTab(RealLifeMod.Technologie);
	}

}

package itsamysterious.mods.reallifemod.core.items;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.init.Reference;
import itsamysterious.mods.reallifemod.utils.RLMExtItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCircuitBoard extends Item {


	public ItemCircuitBoard() {
		setUnlocalizedName("itemCircuitboard");
		setCreativeTab(RealLifeMod.Technologie);
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));

	}

}

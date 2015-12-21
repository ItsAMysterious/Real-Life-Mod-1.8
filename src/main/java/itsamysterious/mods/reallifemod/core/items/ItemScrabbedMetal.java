package itsamysterious.mods.reallifemod.core.items;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMExtItem;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemScrabbedMetal extends Item{
	private static final String name = "scrabbedmetal";

	public ItemScrabbedMetal() {
		setFull3D();
		setUnlocalizedName(name);
		setCreativeTab(RealLifeMod.Technologie);
		GameRegistry.registerItem(this, name );
	}

}

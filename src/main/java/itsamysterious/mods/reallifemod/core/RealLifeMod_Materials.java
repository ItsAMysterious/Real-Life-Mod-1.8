package itsamysterious.mods.reallifemod.core;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RealLifeMod_Materials extends Material{
	public static Material marmor=new Material(MapColor.sandColor);
	public static Material plastic=new Material(MapColor.grayColor);
	public static ArmorMaterial FireProtection = EnumHelper.addArmorMaterial("heat protection suite", "fireprotection", 17, new int[] {3, 8, 6, 3}, 0);

	public RealLifeMod_Materials(MapColor color) {
		super(color);
	}

}

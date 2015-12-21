package itsamysterious.mods.reallifemod.core.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomCreativeTab extends CreativeTabs{

	public CustomCreativeTab(String lable) {
		super(lable);
	}

	@Override
	public Item getTabIconItem() {
		return Items.stone_hoe;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return this.getTabLabel();
    }

}

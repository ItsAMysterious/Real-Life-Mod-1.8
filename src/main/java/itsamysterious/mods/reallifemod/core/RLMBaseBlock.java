package itsamysterious.mods.reallifemod.core;

import java.util.Random;

import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RLMBaseBlock extends Block{

	public RLMBaseBlock(Material material, String unlocalizedname){
		super(material);
		setUnlocalizedName(unlocalizedname);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return RealLifeMod_Items.copperIngot;
    }

}

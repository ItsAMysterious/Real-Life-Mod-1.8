package itsamysterious.mods.reallifemod.core.blocks;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_IronFence;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockIronFence extends RLMBlockContainer{

	public BlockIronFence(Material materialIn) {
		super(materialIn);
		setUnlocalizedName("BlockIronFence");
		setCreativeTab(RealLifeMod.RLMDeco);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_IronFence();
	}

}

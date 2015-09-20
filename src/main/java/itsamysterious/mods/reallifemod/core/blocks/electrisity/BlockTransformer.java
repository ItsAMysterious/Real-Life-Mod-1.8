package itsamysterious.mods.reallifemod.core.blocks.electrisity;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RLMBlockContainer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Transformer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTransformer extends RLMBlockContainer{

	public BlockTransformer() {
		super(Material.iron);
		setUnlocalizedName("blockTransformer");
		setCreativeTab(RealLifeMod.Technologie);
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntity_Transformer();
	}
	
	
}

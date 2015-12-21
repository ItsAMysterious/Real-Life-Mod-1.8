package itsamysterious.mods.reallifemod.core.blocks;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Tarmac;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Block_Tarmac extends Block {

	public Block_Tarmac(Material materialIn) {
		super(Material.rock);
		setCreativeTab(RealLifeMod.Cars);
		setUnlocalizedName("blocktarmac");
		GameRegistry.registerBlock(this, getUnlocalizedName().substring(5));
		setBlockBounds(0, 0	, 0, 1,1 ,1);

	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
		/*TileEntityTarmac t = (TileEntityTarmac) worldIn.getTileEntity(pos);
		entityIn.posY=(float)t.getHeight((float)entityIn.posX+0.001f%1,(float)entityIn.posZ+0.001f%1);
		t.entities.clear();
		t.entities.add(entityIn);*/
	}
	


	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public int getRenderType() {
		return super.getRenderType();
	}
	
}

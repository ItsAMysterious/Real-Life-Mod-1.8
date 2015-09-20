package itsamysterious.mods.reallifemod.core.roads.signs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Signs {
	public static List<Signfile> signs = new ArrayList<Signfile>();
	
	
	public static void addSign(File f){
		signs.add(new Signfile(f));
	}


	public static void setupSigns() {
		for(Signfile i:signs){
			registerSign(i);
		}
	}
	
	public static void registerSign(final Signfile f) {
		class newBlock extends BlockContainer {
			public newBlock() {
				super(Material.iron);
				setUnlocalizedName("Block_" + f.name);
				setCreativeTab(RealLifeMod.Cars);
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
				return new TileEntity_Sign();
			}


			@Override
			public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
					ItemStack stack) {
				super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
				TileEntity_Sign s=(TileEntity_Sign) worldIn.getTileEntity(pos);
				s.rotation=MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
				s.signname=f.name;
			}

		}
		Block b = new newBlock();
		RealLifeMod_Blocks.blockList.add(b);
	}
}

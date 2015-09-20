package itsamysterious.mods.reallifemod.core.blocks;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntityTarmac;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BlockCustomCollision extends Block implements ITileEntityProvider {
	public static BufferedImage heightfile;

	public BlockCustomCollision(Material materialIn, ResourceLocation heightfile) {
		super(materialIn);
		try {
			InputStream imagefile = Minecraft.getMinecraft().getResourceManager().getResource(heightfile)
					.getInputStream();
			this.heightfile = ImageIO.read(imagefile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn != null && worldIn.getTileEntity(pos) != null
				&& worldIn.getTileEntity(pos) instanceof TileEntityTarmac) {
			if (!worldIn.isRemote) {
				TileEntityTarmac t = (TileEntityTarmac) worldIn.getTileEntity(pos);
				t.entities.clear();
				t.entities.add(entityIn);
				System.out.println("Entities have been set!");
			}
		} else {
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

}

package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.BlockIronFence;
import itsamysterious.mods.reallifemod.core.blocks.BlockRailing;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_IronFence;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Railing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

public class RenderRailing extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Railing.obj"));
	private final ResourceLocation texture = new ResourceLocation("minecraft:textures/blocks/planks_oak.png");

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		TileEntity_Railing tile = (TileEntity_Railing) t;
		if (!tile.isInvalid()) {

			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y-1, z + 0.5);
			bindTexture(texture);
			

			Block right = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(1,-1,0))).getBlock();
			Block left = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(-1,0,0))).getBlock();
			Block front = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(0,0,1))).getBlock();
			Block back = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(0,0,-1))).getBlock();
			
			if(!(left instanceof BlockAir)&&!(left instanceof BlockRailing)){
				model.renderPart("left");
			}else
			if(!(right instanceof BlockAir)&&!(right instanceof BlockRailing)){
				model.renderPart("right");
			}else
			model.renderPart("middle");
			GL11.glPopMatrix();

		}
	}

}

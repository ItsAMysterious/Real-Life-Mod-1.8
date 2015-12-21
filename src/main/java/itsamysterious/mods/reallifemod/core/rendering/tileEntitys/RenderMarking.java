package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.Block_IronFence;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_IronFence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

public class RenderMarking extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Cube.obj"));

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		TileEntity_IronFence tile = (TileEntity_IronFence) t;
		if (!tile.isInvalid()) {

			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y, z + 0.5);
			

			Block right = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(1,0,0))).getBlock();
			Block left = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(-1,0,0))).getBlock();
			Block front = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(0,0,1))).getBlock();
			Block back = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(0,0,-1))).getBlock();
			
			if(front instanceof Block_IronFence||!(front instanceof BlockAir)){
				bindTexture(new ResourceLocation("reallifemod:textures/models/block/texture_Iron.png"));
			}
			if(back instanceof Block_IronFence||!(back instanceof BlockAir)){
				model.renderPart("back");
			}
			
			if(left instanceof Block_IronFence||!(left instanceof BlockAir)){
				model.renderPart("left");
			}
			if(right instanceof Block_IronFence||!(right instanceof BlockAir)){
				model.renderPart("right");
			}
			model.renderPart("post");
			GL11.glPopMatrix();

		}
	}

}

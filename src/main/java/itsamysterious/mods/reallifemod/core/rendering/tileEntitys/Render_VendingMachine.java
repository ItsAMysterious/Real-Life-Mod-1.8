package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_VendingMachine;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class Render_VendingMachine extends TileEntitySpecialRenderer{
	private final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_VendingMachine.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_VendingMachine.png");
			
	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z,
			float f, int i) {
		TileEntity_VendingMachine tile = (TileEntity_VendingMachine)t;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		double height=1-tile.getWorld().getBlockState(tile.getPos().add(0, -0.6, 0)).getBlock().getBlockBoundsMaxY();
		GL11.glTranslated(x+0.5, y-height, z+1);
		GL11.glRotated(tile.rotation, 0, 1, 0);
		bindTexture(texture);
		model.renderAll();
		GL11.glPopMatrix();
		
		
	}

}

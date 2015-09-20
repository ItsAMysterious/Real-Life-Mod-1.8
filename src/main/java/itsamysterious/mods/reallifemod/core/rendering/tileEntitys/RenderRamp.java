package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntityRamp;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_VendingMachine;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderRamp extends TileEntitySpecialRenderer{
	private final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_ramp.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/blocks/tarmac.png");
			
	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z,
			float f, int i) {
		TileEntityRamp tile = (TileEntityRamp)t;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslated(x+0.5, y, z+0.5);
		GL11.glRotated(tile.rotation*90, 0, 1, 0);
		bindTexture(texture);
		model.renderAll();
		GL11.glPopMatrix();
		
		
	}

}

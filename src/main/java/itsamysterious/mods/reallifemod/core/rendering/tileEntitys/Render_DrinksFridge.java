package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DrinksFridge;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class Render_DrinksFridge extends TileEntitySpecialRenderer{
	private final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_DrinksFridge.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_DrinksFridge.png");
			
	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z,
			float f, int i) {
		GL11.glPushMatrix();
		TileEntity_DrinksFridge tile = (TileEntity_DrinksFridge)t;
		double height=1-tile.getWorld().getBlockState(tile.getPos().add(0, -0.6, 0)).getBlock().getBlockBoundsMaxY();
		GL11.glTranslated(x+0.5, y-height, z+0.5);
		GL11.glRotated(-tile.rotation, 0, 1, 0);
		bindTexture(texture);
		model.renderAll();
		GL11.glPopMatrix();
		
		
	}

}

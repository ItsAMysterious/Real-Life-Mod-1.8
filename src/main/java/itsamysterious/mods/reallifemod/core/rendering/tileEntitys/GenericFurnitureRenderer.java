package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GenericFurnitureRenderer extends TileEntitySpecialRenderer{
	public static  Class<? extends RLMTileEntity> theClass;
	private IModelCustom model;
	private ResourceLocation texture;
	
	public GenericFurnitureRenderer(String modelLoc, String textureLoc) {
		texture = new ResourceLocation(Reference.ID+":textures/"+textureLoc);
		model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.ID+":models/"+modelLoc));
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x,
			double y, double z, float p_180535_8_, int p_180535_9_) {
		if(tile.getClass().isInstance(theClass)){
			renderTileEntityAt(tile.getClass().cast(theClass), x, y, z, p_180535_8_, p_180535_9_);
		}
	}
	
	public void renderTileEntityAt(RLMTileEntity tile, double x,
			double y, double z, float p_180535_8_, int p_180535_9_) {
		{
			glPushMatrix();
	        glDisable(GL_CULL_FACE);
	        glEnable(GL_ALPHA_TEST);
	         
			glTranslatef((float) x+0.5f, (float)y, (float) z+0.5f);
			glRotatef(tile.getBlockMetadata() * 90, 0.0F, 1.0F, 0.0F);
			if(tile.getBlockMetadata()*90==180||tile.getBlockMetadata()*90==0){
				glRotatef(180, 0.0F, 1.0F, 0.0F);
			}
			
	        this.bindTexture(texture);
	        model.renderAll();
        glPopMatrix();
		}
	}


}

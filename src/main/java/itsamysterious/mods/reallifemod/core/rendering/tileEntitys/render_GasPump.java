package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasPump;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class render_GasPump extends TileEntitySpecialRenderer{
	private final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_GasPump.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_GasPump.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ,
			float p_180535_8_, int p_180535_9_) {
		TileEntity_GasPump tile = (TileEntity_GasPump)te;
		glPushMatrix();
	        glDisable(GL_CULL_FACE);
	        glEnable(GL_ALPHA_TEST);
			glTranslatef((float) posX+0.5f, (float)posY, (float) posZ+0.5f);
	        bindTexture(texture);
	        model.renderAll();
    glPopMatrix();
	}

}

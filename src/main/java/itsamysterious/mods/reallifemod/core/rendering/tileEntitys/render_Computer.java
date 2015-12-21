package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import itsamysterious.mods.reallifemod.RealLifeModConfig;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Computer;
import itsamysterious.mods.reallifemod.core.models.ModelComputer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class render_Computer extends TileEntitySpecialRenderer {

	private final ModelComputer model = new ModelComputer();
	private IModelCustom modelHD;
	private final ResourceLocation textureMC; 
	private final ResourceLocation texture;

	public render_Computer(){
		modelHD = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/furniture/model_Computer.obj"));
		textureMC = new ResourceLocation("reallifemod:textures/tiles/furniture/texture_ComputerMC.png");
		texture = new ResourceLocation("reallifemod:textures/tiles/furniture/texture_Computer.png");
	}

	public void renderTileEntityAt(TileEntity_Computer te, double x, double y, double z,
			float scale, int i) {
		glPushMatrix();
		if(RealLifeModConfig.minecraftstyle){
			glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			glRotatef(180, 0.0F, 0.0F, 1.0F);
			glRotatef(te.getBlockMetadata() * 90, 0.0F, 1.0F, 0.0F);
			bindTexture(textureMC);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}else
		{
			glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
			glRotatef(te.getBlockMetadata() * 90, 0.0F, 1.0F, 0.0F);
			this.bindTexture(texture);
			this.modelHD.renderAll();
		}
		glPopMatrix();
	}
	

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float scale, int i) {
		renderTileEntityAt((TileEntity_Computer)te, x, y, z, scale, i);
	}

}

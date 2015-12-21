package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import itsamysterious.mods.reallifemod.RealLifeModConfig;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.init.Reference;

import javax.vecmath.Vector3d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RLMBaseRenderer extends TileEntitySpecialRenderer {
	private Vector3d offset;
	private Vector3d scale;
	private Vector3d rotation;
	private ResourceLocation HDTexture;
	private ResourceLocation MCTexture;
	private ModelBase MCModel;
	private IModelCustom CustomModel;
	/**
	 * Takes in the name of the HD Model(.obj in default!!),
	 * the related texturename(.png), the location of the .java modelfile in 
	 * mcstyle and its texturename.
	 * @param HDModelname
	 */
	public RLMBaseRenderer(String HDModelname, String HDTexture, ModelBase MCModel, String MCTexture) {
		this.MCModel = MCModel;
		this.CustomModel=AdvancedModelLoader.loadModel(new ResourceLocation(Reference.ID+":"+"models/"+HDModelname));
		this.HDTexture = new ResourceLocation(Reference.ID+":"+"textures/"+MCTexture);
		this.MCModel = MCModel;
		this.MCTexture=new ResourceLocation(Reference.ID+":"+"textures/"+MCTexture);
		offset=new Vector3d(0,0,0);
		rotation=new Vector3d(0,0,0);
		scale=new Vector3d(1,1,1);

	}

	public void setOffset(double x, double y, double z) {
		this.offset = new Vector3d(x, y, z);
	}

	public void setScale(double x, double y, double z) {
		this.scale = new Vector3d(x, y, z);
	}

	public void setRotation(double x, double y, double z) {
		this.rotation = new Vector3d(x, y, z);
	}
	
	/**
	 * Called every rendertick when the 3D-setting are on HD.
	 */
	public static void renderHDModel(TileEntity thetile, double posX, double posY,
			double posZ, float p_180535_8_, int p_180535_9_){
		
	}

	/**
	 * Called every rendertick when the 3D-setting are on Minecraftstyle.
	 */
	public static void renderMCModel(TileEntity thetile, double posX, double posY,
			double posZ, float p_180535_8_, int p_180535_9_){
		
	}

	@Override
	public void renderTileEntityAt(TileEntity p_180535_1_, double posX,
			double posY, double posZ, float p_180535_8_, int p_180535_9_) {	
		GL11.glPushMatrix();
		GL11.glTranslated(posX + 0.5+offset.x, posY +0.5+ offset.y, posZ +0.5+ offset.z);
		GL11.glRotated(rotation.x, 1, 0, 0);
		GL11.glRotated(rotation.y, 0, 1, 0);
		GL11.glRotated(rotation.z, 0, 0, 1);

		if (RealLifeModConfig.minecraftstyle) {
			this.bindTexture(this.MCTexture);
			renderMCModel(p_180535_1_, posX, posY, posZ, p_180535_8_,
					p_180535_9_);
			this.MCModel.render((Entity)null,(float) posX,(float) posY,(float) posZ, 1, 1, 0.0369f	);
		} else {
			renderHDModel(p_180535_1_, posX, posY, posZ, p_180535_8_,p_180535_9_);
			this.bindTexture(this.HDTexture);
			this.CustomModel.renderAll();
		}
		GL11.glPopMatrix();
	}

}

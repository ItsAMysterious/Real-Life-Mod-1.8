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
import itsamysterious.mods.reallifemod.core.blocks.furniture.Block_TVTable;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TV;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

public class render_TV extends TileEntitySpecialRenderer {

	private final IModelCustom model;
	private ResourceLocation texture = new ResourceLocation("reallifemod:textures/tiles/furniture/texture_TV.png");

	public render_TV() {
		this.model = AdvancedModelLoader.loadModel(new ResourceLocation("reallifemod:models/furniture/model_TV.obj"));
	}

	public void renderModelAt(TileEntity_TV tile, double x, double y, double z, float f) {
		glPushMatrix();
		glDisable(GL_CULL_FACE);
		glEnable(GL_ALPHA_TEST);
		Block b = tile.getWorld().getBlockState(tile.getPos().subtract(new Vec3i(0, 1, 0))).getBlock();
		glTranslatef((float) x + 0.5f, (float) y, (float) z + 0.5f);
		if (b instanceof Block_TVTable) {
			glTranslatef(0, -0.5f, 0);
		}
		glRotatef(tile.rotation * 90, 0.0F, 1.0F, 0.0F);
		if (tile.rotation == 0 || tile.rotation == 2) {
			glRotatef(180, 0.0F, 1.0F, 0.0F);
		}
		this.bindTexture(texture);
		this.model.renderAll();
		glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double posX, double posZ, double p_180535_6_,
			float p_180535_8_, int p_180535_9_) {
		renderModelAt((TileEntity_TV) tileEntity, posX, posZ, p_180535_6_, p_180535_8_);

	}

}

package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.models.ModelToilet;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class render_Toilet extends TileEntitySpecialRenderer {
	private IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation(Reference.ID + ":models/obj/block/model_Toilet.obj"));

	public render_Toilet() {
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float p_180535_8_, int p_180535_9_) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glRotated(((RLMTileEntity)tile).rotation*90, 0, 1, 0);
		GL11.glScaled(1.5, 1.5, 1.5);
		bindTexture(new ResourceLocation("minecraft:textures/blocks/log_oak.png"));
		model.renderOnly("lid");
		bindTexture(new ResourceLocation("minecraft:textures/blocks/planks_oak.png"));
		model.renderOnly("seat");
		bindTexture(new ResourceLocation("minecraft:textures/blocks/iron_block.png"));
		model.renderOnly("corps");
		GL11.glPopMatrix();

	}

}

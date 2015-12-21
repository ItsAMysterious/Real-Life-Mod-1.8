package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.Block_IronFence;
import itsamysterious.mods.reallifemod.core.blocks.Block_Railing;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DartBoard;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_IronFence;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Railing;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

public class RenderDartboard extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Dartboard.obj"));
	private final ResourceLocation texture = new ResourceLocation(
			"reallifemod:textures/models/block/texture_Dartboard.png");

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		TileEntity_DartBoard tile = (TileEntity_DartBoard) t;
		if (!tile.isInvalid()) {
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y, z + 0.5);
			GL11.glRotated(tile.rotation*90, 0, 1, 0);
			bindTexture(texture);
			model.renderAll();
			GL11.glPopMatrix();

		}
	}

}

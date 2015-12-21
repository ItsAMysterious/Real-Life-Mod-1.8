package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.furniture.Block_Table;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Table;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class RenderTable extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Table.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_Wood.png");

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		TileEntity_Table tile = (TileEntity_Table) t;
		RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
		if (!tile.isInvalid()) {

			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y, z + 0.5);
			bindTexture(new ResourceLocation("reallifemod:textures/models/block/texture_Wood.png"));
			Block right = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(1, 0, 0))).getBlock();
			Block left = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(-1, 0, 0))).getBlock();
			Block front = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(0, 0, 1))).getBlock();
			Block back = tile.getWorld().getBlockState(tile.getPos().add(new Vec3i(0, 0, -1))).getBlock();

			if (!(left instanceof Block_Table)) {
				if (!(front instanceof Block_Table)) {
					model.renderPart("left");
				}

				if (!(back instanceof Block_Table)) {
					model.renderPart("backleft");
				}
			}

			if (!(right instanceof Block_Table)) {
				if (!(front instanceof Block_Table)) {
					model.renderPart("right");
				}

				if (!(back instanceof Block_Table)) {
					model.renderPart("backright");
				}
			}

			if (!(front instanceof Block_Table)) {
				if (!(left instanceof Block_Table)) {
					model.renderPart("left");

				}
				if (!(right instanceof Block_Table)) {
					model.renderPart("right");

				}

			}

			if (!(back instanceof Block_Table)) {

				if (!(left instanceof Block_Table)) {
					model.renderPart("backleft");

				}
				if (!(right instanceof Block_Table)) {
					model.renderPart("backright");

				}

			}

			model.renderPart("Plate");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(x, y + 1.2, z);
			GL11.glScaled(0.5, 0.5, 0.5);
			ItemStack stack1 = tile.getStackInSlot(0);
			ItemStack stack2 = tile.getStackInSlot(1);
			ItemStack stack3 = tile.getStackInSlot(2);
			GL11.glPushMatrix();
			GL11.glTranslated(0.55, 0, 1);
			if (stack1 != null)
				renderer.renderItemModel(stack1);

			GL11.glTranslated(0.9, 0, 0);
			if (stack2 != null)
				renderer.renderItemModel(stack2);
			GL11.glTranslated(-0.45, 0.5, 0);
			if (stack3 != null)
				renderer.renderItemModel(stack3);

			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}

}

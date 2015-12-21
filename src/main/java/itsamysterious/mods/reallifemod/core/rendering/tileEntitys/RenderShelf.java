package itsamysterious.mods.reallifemod.core.rendering.tileEntitys;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import itsamysterious.mods.reallifemod.api.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.api.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Shelf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import scala.util.Random;

public class RenderShelf extends TileEntitySpecialRenderer {
	private final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation("reallifemod:models/obj/block/model_Shelf.obj"));
	private final ResourceLocation texture = new ResourceLocation("reallifemod:textures/models/block/texture_Wood.png");

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f, int i) {
		TileEntity_Shelf tile = (TileEntity_Shelf) t;
		if (!tile.isInvalid()) {

			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y, z + 0.5);
			GL11.glRotated(-tile.rotation * 90, 0, 1, 0);
			bindTexture(texture);
			model.renderAll();

			ItemStack stack1 = tile.getStackInSlot(0);
			ItemStack stack2 = tile.getStackInSlot(1);
			ItemStack stack3 = tile.getStackInSlot(2);
			ItemStack stack4 = tile.getStackInSlot(3);
			ItemStack stack5 = tile.getStackInSlot(4);
			ItemStack stack6 = tile.getStackInSlot(5);
			ItemStack stack7 = tile.getStackInSlot(6);
			ItemStack stack8 = tile.getStackInSlot(7);
			ItemStack stack9 = tile.getStackInSlot(8);
			ItemStack stack10 = tile.getStackInSlot(9);
			ItemStack stack11 = tile.getStackInSlot(10);
			ItemStack stack12 = tile.getStackInSlot(11);
			ItemStack stack13 = tile.getStackInSlot(12);
			ItemStack stack14 = tile.getStackInSlot(13);
			ItemStack stack15 = tile.getStackInSlot(14);
			if (stack1 != null)
				renderStack(stack1, -0.6, 0.35, -0.15);
			if (stack2 != null)
				renderStack(stack2, 0.6, 0, 0);
			if (stack3 != null)
				renderStack(stack3, 0.6, 0, 0);

			GL11.glTranslated(-0.6, 0, 0);
			if (stack4 != null)
				renderStack(stack4, -0.6, 0.65, 0);
			if (stack5 != null)
				renderStack(stack5, 0.6, 0, 0);
			if (stack6 != null)
				renderStack(stack6, 0.6, 0, 0);

			GL11.glTranslated(-0.6, 0.09, 0);
			if (stack7 != null)
				renderStack(stack7, -0.6, 0.65, 0);
			if (stack8 != null)
				renderStack(stack8, 0.6, 0, 0);
			if (stack9 != null)
				renderStack(stack9, 0.6, 0, 0);
			
			GL11.glTranslated(-0.6, 0.115, 0);
			if (stack10 != null)
				renderStack(stack10, -0.6, 0.65, 0);
			if (stack11 != null)
				renderStack(stack11, 0.6, 0, 0);
			if (stack12 != null)
				renderStack(stack12, 0.6, 0, 0);
			
			GL11.glTranslated(-0.6, 0.095, 0);
			if (stack13 != null)
				renderStack(stack13, -0.6, 0.65, 0);
			if (stack14 != null)
				renderStack(stack14, 0.6, 0, 0);
			if (stack15 != null)
				renderStack(stack15, 0.6, 0, 0);


			GL11.glPopMatrix();

		}
	}

	private void renderStack(ItemStack stack, double x, double y, double z) {
		RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
		GL11.glTranslated(x, y, z);
		GL11.glPushMatrix();
		GlStateManager.disableLighting();
		GL11.glRotated(-180,0,1,0);
		if ((Block.getBlockFromItem(stack.getItem()) != null
				&& Block.getBlockFromItem(stack.getItem()) == Blocks.glass_pane)||stack.getMaxStackSize()==1) {
			GL11.glTranslated(0, 0, 0.225);
			if(!stack.getItem().isItemTool(stack))
			GL11.glRotated(25, 0.5, 0, 0);
			GL11.glScaled(0.5, 0.5, 0.5);
			renderer.renderItemModel(stack);
		} else if (!stack.getItem().isItemTool(stack)&&Block.getBlockFromItem(stack.getItem()) == null) {
			GL11.glScaled(0.5, 0.5, 0.5);
			for (int i = 0; i < stack.stackSize / 5; i++) {
				GL11.glPushMatrix();
				GL11.glTranslated(0, -0.425+i * 0.05, 0);
				GL11.glRotated(Math.sin(i)*90, 0, 1, 0);
				GL11.glRotated(90, 1, 0, 0);
				renderer.renderItemModel(stack);
				GL11.glPopMatrix();

			}

		} else
		{
			if(Block.getBlockFromItem(stack.getItem())==null){
				GL11.glScaled(0.5, 0.5, 0.5);
			}
			renderer.renderItemModel(stack);
		}
		GlStateManager.enableLighting();

		GL11.glPopMatrix();
	}

}

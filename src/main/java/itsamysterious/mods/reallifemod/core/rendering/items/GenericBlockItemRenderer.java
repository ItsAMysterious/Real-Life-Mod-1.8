package itsamysterious.mods.reallifemod.core.rendering.items;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;

import itsamysterious.mods.reallifemod.RealLifeModConfig;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TV;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class GenericBlockItemRenderer implements IItemRenderer {
	TileEntitySpecialRenderer render;

	private TileEntity entity;

	public GenericBlockItemRenderer(TileEntitySpecialRenderer render,
			TileEntity entity) {
		this.entity = entity;
		this.render = render;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY)
			glTranslatef(-0.5F, 0.0F, -0.5F);
		glPushMatrix();
		if (entity instanceof TileEntity_TV) {
			glTranslatef(-0.15F, -0.35F, -0.25F);
			glScaled(0.9, 0.9, 0.9);
		}
		TileEntityRendererDispatcher.instance.renderTileEntityAt(entity, 0.0D,0.0D, 0.0D, 0.0F);
		glPopMatrix();
	}

}
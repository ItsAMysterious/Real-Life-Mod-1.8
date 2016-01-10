package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiDropdownMenu extends Gui {
	/** Holds all the sub-items */
	public List<GuiMenuItem> contents = new ArrayList<GuiMenuItem>();

	public int positionX, positionY;
	public int width, scrolloffset;
	public int maxHeight;
	public String labelText;
	FontRenderer fontRenderObj;

	/** Icon stuff */
	private ResourceLocation iconLocation;
	private ItemStack iconItem;

	/** Temporary variables */
	public boolean isOpened = false;
	public boolean isItemIcon = false;

	public GuiMenuItem selected;

	public GuiDropdownMenu(int x, int y, int width, String text) {
		this.positionX = x;
		this.positionY = y;
		this.width = width;
		this.labelText = text;
		this.fontRenderObj = Minecraft.getMinecraft().fontRendererObj;

	}

	public void onUpdate() {
		doScrolling();
	}

	/** Rendering part */
	public void draw(int mouseX, int mouseY) {
		int textwidth = fontRenderObj.getStringWidth(labelText) + 5;

		if (!isOpened) {
			drawRect(positionX + textwidth, positionY, positionX + textwidth + width, positionY + 15,
					Color.black.getRGB());

			drawRect(positionX + textwidth + 1, positionY + 1, positionX + textwidth + width - 1, positionY + 14,
					Color.gray.getRGB());
		} else {
			// Drawing MainBar
			drawRect(positionX + textwidth, positionY, positionX + textwidth + width, positionY + 15,
					Color.black.getRGB());
			drawRect(positionX + textwidth, positionY, positionX + textwidth + width,
					positionY + 15 + maxHeight * 14 + 1, Color.black.getRGB());

			drawRect(positionX + textwidth + 1, positionY + 1, positionX + textwidth + width - 17,
					positionY + maxHeight * 14 - 1, Color.gray.getRGB());
		}

		/** Drawing the Button */
		drawRect(positionX + textwidth + width - 16, positionY + 1, positionX + textwidth + width - 1, positionY + 14,
				Color.black.getRGB());
		drawRect(positionX + textwidth + width - 15, positionY + 1, positionX + textwidth + width - 1, positionY + 14,
				new Color(150, 150, 150).getRGB());

		if (isMouseHoovering(mouseX, mouseY)) {
			drawRect(positionX + textwidth + width - 17, positionY, positionX + textwidth + width, positionY + 15,
					Color.black.getRGB());
			drawRect(positionX + textwidth + width - 16, positionY, positionX + textwidth + width, positionY + 15,
					new Color(150, 150, 150).getRGB());

		}

		drawString(fontRenderObj, isOpened ? "▲" : "▼", positionX + textwidth + width - 9, positionY + 5,
				Color.yellow.getRGB());
		drawString(fontRenderObj, this.labelText, positionX, positionY + 3, Color.white.getRGB());

		drawString(fontRenderObj, this.selected == null ? EnumChatFormatting.RED + "NONE" : this.selected.text,
				positionX + textwidth + 5, positionY + 3, Color.white.getRGB());

		if (isOpened) {
			for (GuiMenuItem item : contents) {
				item.drawEntry(mouseX, mouseY);
			}

			int scrollBarY = (14 * (maxHeight)) / (contents.size() - 1) * scrolloffset;
			int scrollbarheight = maxHeight * 14 / (contents.size() - 1);

			drawRect(positionX + textwidth + width - 15, positionY + 16 + scrollBarY, positionX + textwidth + width - 1,
					positionY + 15 + scrollBarY + scrollbarheight, Color.white.getRGB());
		}

		this.renderIconAndItem(mouseX, mouseX);
	}

	private void renderIconAndItem(int mouseX, int mouseX2) {
		RenderItem renderer = Minecraft.getMinecraft().getRenderItem();

		if (isItemIcon && iconItem != null && iconItem.getItem() != null) {
			GL11.glPushMatrix();
			renderer.renderItemIntoGUI(iconItem, 0, 0);
			GL11.glPopMatrix();
		}
	}

	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (isMouseHoovering(mouseX, mouseY) && button == 0) {
			this.isOpened = !this.isOpened;
		}

		if (this.isOpened) {
			for (GuiMenuItem e : contents) {
				e.onMouseClicked(mouseX, mouseY, button);
			}
		}
	}

	public boolean isMouseHoovering(int x, int y) {
		int textwidth = fontRenderObj.getStringWidth(labelText) + 5;
		return x > this.positionX + textwidth && x < this.positionX + textwidth + this.width && y > this.positionY
				&& y < positionY + 20;
	}

	public void doScrolling() {
		int prevScrollOffset = scrolloffset;
		GuiMenuItem lastItem = contents.get(contents.size() - 1);
		for (GuiMenuItem i : this.contents) {
			i.y += prevScrollOffset * 14;
		}

		scrolloffset -= Mouse.getDWheel() / 2.5 / contents.size();

		if (scrolloffset < 0) {
			scrolloffset = 0;
		}
		if (scrolloffset > contents.size() - maxHeight) {
			scrolloffset = contents.size() - maxHeight;
		}

		for (GuiMenuItem i : this.contents) {
			i.y -= scrolloffset * 14;
		}

	}

	public void setMaxHeight(int height) {
		this.maxHeight = height;
	}

	public void setIcon(ResourceLocation loc) {
		this.iconLocation = loc;
	}

	public void setIcon(ItemStack stack) {
		this.iconItem = stack;
		this.isItemIcon = true;
	}

	public void setSelected(GuiMenuItem item) {
		this.selected = item;
		this.isOpened = false;
	}

}

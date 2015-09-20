package itsamysterious.mods.reallifemod.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuiBlockList extends GuiScrollingList{

	public GuiBlockList(Minecraft client, int width, int height, int top,
			int bottom, int left, int entryHeight) {
		super(client, width, height, top, bottom, left, entryHeight);
	}

	@Override
	protected int getSize() {
		return 0;
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
	}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected void drawBackground() {
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4,
			Tessellator var5) {
	}

}

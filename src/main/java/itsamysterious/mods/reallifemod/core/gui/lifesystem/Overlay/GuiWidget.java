package itsamysterious.mods.reallifemod.core.gui.lifesystem.Overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiWidget extends Gui {

	public int positionX, positionY, width, height;
	public int tick;
	public boolean shouldDraw;
	public ScaledResolution res;

	public GuiWidget(int x, int y, int width, int height) {
		this.positionX = x;
		this.positionY = y;
		this.width = width;
		this.height = height;
	}

	public void update() {
		this.tick++;
	}

	public void drawWidget(int x, int y, float partialTicks) {

	}

	public void bindTexture(ResourceLocation texture) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}
}

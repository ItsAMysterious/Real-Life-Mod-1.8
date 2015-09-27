package itsamysterious.mods.reallifemod.core.gui.phone;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class AppTile extends Gui {
	public int x, y, width, height;
	public int appID;
	private Color color;
	public int actualX;
	public int actualY;
	public boolean updatedPos;

	public AppTile(int x, int y, int width, int height, int appID) {
		this.x = x;
		this.y = y;
		this.actualX = x;
		this.actualY = y;
		this.width = width;
		this.height = height;
		this.appID = appID;
		this.color = new Color((int) (Math.random() * 0x1000000));
		updatedPos = false;
	}

	public IApp getApp() {
		return PhoneRegistry.getAppFromID(this.appID);
	}

	public void drawTile() {
		if (this.x > height-125)
		drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.color.getRGB());
		GlStateManager.color(1, 1, 1);
		Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(getIcon());
		drawModalRectWithCustomSizedTexture(x+3, y+2, 0, 0, 34, 34, 128 / 4, 128 / 4);
		GlStateManager.pushMatrix();
		GL11.glScaled(0.5, 0.5, 0.5);
		drawCenteredString(Minecraft.getMinecraft().fontRendererObj, PhoneRegistry.getAppFromID(appID).name,
				(x + width / 2)*2, (y + height -5)*2, Color.white.getRGB());
		GlStateManager.popMatrix();
	}

	private ResourceLocation getIcon() {
		ResourceLocation r = new ResourceLocation(
				"reallifemod:textures/gui/apps/" + PhoneRegistry.getAppFromID(appID).name + ".png");
		try {
			if (Minecraft.getMinecraft().getResourceManager().getResource(r).getInputStream() != null) {
				return r;
			}
		} catch (IOException e) {
		}
		return new ResourceLocation("reallifemod:textures/gui/apps/defaultIcon.png");
	}

	public void open(int x, int y, int id, GuiPhone guiPhone) {
		if (x > this.x && y > this.y && x < this.x + 41 && y < this.y + 41) {
			System.out.println("Clicked mouse with coords: " + x + " ," + y + "!");
			IApp app = PhoneRegistry.getAppFromID(appID);
			app.parent = guiPhone;
			app.offsetX=guiPhone.phonePosX;
			app.offsetY=guiPhone.phonePosY;
			Minecraft.getMinecraft().displayGuiScreen(app);
		}
	}

}

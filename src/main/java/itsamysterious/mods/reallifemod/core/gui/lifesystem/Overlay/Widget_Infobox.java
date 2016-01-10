package itsamysterious.mods.reallifemod.core.gui.lifesystem.Overlay;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;

public class Widget_Infobox extends GuiWidget {
	public String text;
	public boolean hiding;
	public float alpha;

	public Widget_Infobox(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void update() {
		super.update();
		if (this.tick < 10) {
			this.alpha += 25.6;
		}
	}

	@Override
	public void drawWidget(int x, int y, float partialTicks) {
		super.drawWidget(x, y, partialTicks);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4d(1, 0.5, 0.5, alpha);
		GL11.glPushMatrix();
		int stringwidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.text);
		drawRect(x, y, x + stringwidth+6, y + 20, (int) (Color.black.getAlpha()+alpha*256));
		drawRect(x+1, y+1, x + stringwidth +5, y + 19, Color.white.getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawString(text, x+3, y+5, Color.black.getRGB(), false);
		GL11.glPopMatrix();
	}

}

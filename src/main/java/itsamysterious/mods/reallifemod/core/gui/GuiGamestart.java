package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

public class GuiGamestart extends RLMCustomGui {
	private int lengthshown = 0;
	private GuiButton nextButton;

	public void initGui() {
		nextButton = new GuiButton(0, width /4, height-height/5, width/2, 20, "Start playing");
		buttonList.add(nextButton);
		int size = width / 4;

		buttonList.add(new GuiButton(1, 0, height - 20, size, 20, EnumChatFormatting.WHITE + "Our "
				+ EnumChatFormatting.RED + "You" + EnumChatFormatting.RESET + "Tube"));
		buttonList.add(new GuiButton(3, 1 * size, height - 20, size, 20,
				EnumChatFormatting.WHITE + "Our " + EnumChatFormatting.YELLOW + "Website"));
		buttonList.add(new GuiButton(2, 2 * size, height - 20, size, 20,
				EnumChatFormatting.WHITE + "Our " + EnumChatFormatting.AQUA + "Twitter"));
		buttonList.add(new GuiButton(4, 3 * size, height - 20, size, 20, EnumChatFormatting.GREEN + "Forumpage"));

	}

	public void updateScreen() {
		super.updateScreen();
		lengthshown++;
	}

	public void drawScreen(int mouseX, int mouseY, float f) {
		drawBackground(0);
		GlStateManager.pushMatrix();
		GL11.glScaled(1.5, 1.5, 1.5);
		drawCenteredString(fontRendererObj,
				"Welcome to the new Minecraft " + EnumChatFormatting.GOLD + RLMPlayerProps.getFullname(mc.thePlayer),
				width / 2 - width / 6, height / 2 - 110, Color.white.getRGB());
		GlStateManager.popMatrix();
		drawString(fontRendererObj, "Thank you for downloading our Mod!", 20, height / 2 - 50, Color.WHITE.getRGB());
		drawString(fontRendererObj,
				"In the left corner you can see the " + EnumChatFormatting.ITALIC + "HUD " + EnumChatFormatting.RESET
						+ "It contains your " + EnumChatFormatting.AQUA + "water" + EnumChatFormatting.GRAY + ", toilet"
						+ EnumChatFormatting.RESET + " and " + EnumChatFormatting.YELLOW + "money"
						+ EnumChatFormatting.RESET + " information.",
				20, height / 2 - 40, Color.white.getRGB());
		drawString(fontRendererObj, "THIS MOD CURRENTLY IS IN BETA", 20, height / 2 - 25, Color.WHITE.getRGB());
		drawString(fontRendererObj, "Please report " + EnumChatFormatting.UNDERLINE + "ANY" + EnumChatFormatting.RESET
				+ " bug/glitch you find.", 20, height / 2 - 15, Color.WHITE.getRGB());
		drawString(fontRendererObj, "Greetings " + EnumChatFormatting.GOLD + "ItsAMysterious", 20, height / 2 + 10,
				Color.WHITE.getRGB());
		drawString(fontRendererObj, "To keep up to the latest updates - be sure to check out the following pages", 20,
				height / 2 + 45, Color.WHITE.getRGB());

		super.drawScreen(mouseX, mouseY, f);
	}

	public void actionPerformed(GuiButton b) {
		if (b.id == 0) {
			mc.displayGuiScreen(null);
		}
		if (b.id == 1) {
			URI uri = URI.create("https://www.youtube.com/channel/UCyKlPlyI9lZi3vATCv8zKgA?subconfirmation=1");
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (b.id == 3) {
			URI uri = URI.create("http://cloudolympus.ca/");

			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (b.id == 2) {
			URI uri = URI.create("https://twitter.com/EmeraldMinors");
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (b.id == 4) {
			URI uri = URI.create(
					"http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2196915-real-life-mod-make-minecraft-more-realistic-better");
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		confirmClicked(true, b.id);
	}
}

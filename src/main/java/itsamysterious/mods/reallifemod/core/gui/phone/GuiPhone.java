package itsamysterious.mods.reallifemod.core.gui.phone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiPhone extends GuiScreen {
	public static List<AppTile> apps = new ArrayList<AppTile>();
	public int scrollForce, phonePosX, phonePosY;
	private ResourceLocation casetexture = new ResourceLocation("reallifemod:textures/gui/phoneCase.png");

	public GuiPhone() {

	}

	public void initGui() {
		super.initGui();
		this.phonePosX = width - 125;
		this.phonePosY = height - 200;
		int appPosX = 0, appPosY = 0;
		int appSize = 41;
		apps.clear();
		for (IApp app : PhoneRegistry.getListApps()) {
			apps.add(new AppTile(width - 125 + 13 + appPosX, height - 200 + 20 + appPosY, appSize - 1, appSize - 1,
					app.ID));
			appPosY += appSize;
			if (appPosY / appSize == 4) {
				appPosX += appSize;
				appPosY = 0;
			}
		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	};

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		super.drawScreen(x, y, partialTicks);
		this.drawApps(x, y, partialTicks);
		this.drawCase(x, y, partialTicks);

	}

	public void drawApps(int x, int y, float partialTicks) {
		for (AppTile app : apps) {
			if (app.y > phonePosY) {
				app.drawTile();
			}
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		scrollForce += Mouse.getDWheel() / (height / 3);
		for (AppTile app : apps) {
			app.y += scrollForce;
			app.updatedPos = true;
		}
		scrollForce *= 0.999991;
	}

	public void drawCase(int x, int y, float partialTicks) {
		bindTexture(this.casetexture);
		drawModalRectWithCustomSizedTexture(phonePosX, phonePosY, 0, 0, 435 / 4, 800 / 4, 435 / 4, 800 / 4);
	}

	private void bindTexture(ResourceLocation resource) {
		Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(resource);
	}

	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
	}

	public void handleKeyboardInput() throws IOException {
		super.handleKeyboardInput();
	}

	protected void mouseClicked(int mouseX, int mouseY, int id) throws IOException {
		super.mouseClicked(mouseX, mouseY, id);
		for (AppTile app : apps) {
			app.open(mouseX, mouseY, id, this);
		}
	}

}

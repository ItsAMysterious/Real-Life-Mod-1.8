package itsamysterious.mods.reallifemod.core.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRadioGroup extends Gui {

	static List<GuiRadiobutton> buttonList = new ArrayList<GuiRadiobutton>();
	public int xPos, yPos;
	public static boolean singleChoice;
	public static GuiRadiobutton currentButton;
	public boolean horizontal;

	public GuiRadioGroup(int xp, int yp) {
		this.xPos = xp;
		this.yPos = yp;
	}

	public void draw(int x, int y, float partialTicks) {
		if (buttonList.size() > 0) {
			for (GuiRadiobutton b : buttonList) {
				b.draw();
			}
		}
	}

	public static void doUpdate() {
	}

	public void onMouseClicked(int x, int y, int id) {
		for (GuiRadiobutton b : this.buttonList) {
			if (b.isMouseHoovering(x, y)) {
				currentButton = b;
				b.setChecked(true);
			}
		}
		for (GuiRadiobutton b1 : buttonList) {
			if (b1 != currentButton) {
				b1.setChecked(false);
			}
		}
	}

	public void addButton(GuiRadiobutton guiRadiobutton) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int allButtonsHeight = 0;
		int allbuttonswidth = 0;

		if (!horizontal) {
			for (GuiRadiobutton b : buttonList) {
				allButtonsHeight += b.getHeight() + 1;
			}
			guiRadiobutton.setPosition(xPos, yPos + allButtonsHeight + 1);
			buttonList.add(guiRadiobutton);
		} else {
			for (GuiRadiobutton b1 : buttonList) {
				allbuttonswidth += b1.getFullWidth() + 15;
				if (allbuttonswidth > res.getScaledWidth()) {
					allButtonsHeight += b1.getHeight() + 1;
					allbuttonswidth = 0;
				}
			}
			guiRadiobutton.setPosition(xPos + allbuttonswidth + 1, yPos + allButtonsHeight + 1);
			buttonList.add(guiRadiobutton);
		}
	}

	private boolean isMouseInBounds(int x, int y) {
		System.out.print("Is called" + String.valueOf(
				(x > xPos && x < xPos + getLargestButtonWidth() && y > yPos && y < yPos + getFullHeight() + 20)));
		return x > xPos && x < xPos + getLargestButtonWidth() && y > yPos && y < yPos + getFullHeight() + 20;
	}

	private int getLargestButtonWidth() {
		int latestWidth = 0;

		for (GuiRadiobutton b : buttonList) {
			if (b.getFullWidth() > latestWidth) {
				latestWidth = b.getFullWidth();
			}
		}
		return latestWidth;
	}

	private int getFullHeight() {
		int allButtonsHeight = 0;
		for (GuiRadiobutton b : buttonList) {
			allButtonsHeight += b.getHeight() + 1;
		}
		return allButtonsHeight;
	}

	public GuiRadiobutton getSelectedbutton() {
		return this.currentButton;
	}

}

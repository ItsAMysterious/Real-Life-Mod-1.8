package itsamysterious.mods.reallifemod.core.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRadioGroup extends Gui {

	static List<GuiRadiobutton> buttonList = new ArrayList<GuiRadiobutton>();
	public int xPos, yPos;
	public static boolean singleChoice;
	public static GuiRadiobutton currentButton;

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
		if (isMouseInBounds(x, y)) {
			for (GuiRadiobutton b : this.buttonList) {
				if (b.isMouseHoovering(x, y)) {
					b.onMouseClicked(x, y, id);
					currentButton = b;
				}
				for(GuiRadiobutton b1:buttonList){
					if(b1!=currentButton){
						b1.setChecked(false);
					}
				}
			}
		}
	}

	public void addButton(GuiRadiobutton guiRadiobutton) {
		int allButtonsHeight = 0;
		for (GuiRadiobutton b : buttonList) {
			allButtonsHeight += b.getHeight() + 1;
		}
		guiRadiobutton.setPosition(xPos, yPos + allButtonsHeight + 1);
		buttonList.add(guiRadiobutton);
	}

	private boolean isMouseInBounds(int x, int y) {
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

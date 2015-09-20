package itsamysterious.mods.reallifemod.core.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.Gui;

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
		if (buttonList.size() != 0) {
			for (GuiRadiobutton b : buttonList) {
				b.draw();
			}
		}
	}

	public static void doUpdate() {
//		if (singleChoice)
//			/for (GuiRadiobutton b : buttonList) {
//				if (b != currentButton)
//					b.setChecked(false);
//			}
	}

	public void onMouseClicked(int x, int y, int id) {
		if(isMouseInBounds(x, y)){
			if (singleChoice) {
				for (GuiRadiobutton b : buttonList) {
					if (b.isChecked() && !b.isMouseHoovering(x, y)) {
						b.setChecked(false);
					}
				}
			}
			for (GuiRadiobutton b : this.buttonList) {
				if(b.isMouseHoovering(x, y))
				this.currentButton=b;
				b.onMouseClicked(x, y, id);
			}
		}
	}

	public GuiRadiobutton getSelectedbutton() {
		return this.currentButton;
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
		return x > xPos && x < xPos + getLargestButtonWidth() && y > yPos && y < yPos + getFullHeight()+20;
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

}

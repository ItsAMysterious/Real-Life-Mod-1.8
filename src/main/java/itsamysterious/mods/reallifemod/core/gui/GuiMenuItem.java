package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;

import net.minecraft.client.gui.Gui;

public class GuiMenuItem extends Gui {
	private GuiDropdownMenu parent;
	public int x, y, id;
	public String text;

	public GuiMenuItem(GuiDropdownMenu parent, int id, String text) {
		this.parent = parent;
		this.x = parent.positionX + parent.fontRenderObj.getStringWidth(parent.labelText) + 5;
		this.y = parent.positionY + 15 + id * 14;
		this.text = text;
	}

	public void drawEntry(int mouseX, int mouseY) {
		boolean insideMaxBounds = y-parent.scrolloffset > parent.positionY&&this.y<parent.positionY+15+parent.maxHeight*14;
		if(insideMaxBounds){
			if(isMouseHoovering(mouseX, mouseY)){
				drawRect(mouseX+20, mouseY, mouseX+200, mouseY+50, Color.blue.darker().getRGB());
			}
			
			drawRect(x, y, x + parent.width - 16, y + 15, Color.black.getRGB());
			drawRect(x + 1, y + 1, x + parent.width - 17, y + 14,isMouseHoovering(mouseX, mouseY) ? Color.lightGray.getRGB() : Color.gray.getRGB());
			drawString(parent.fontRenderObj, text, x + 2, y + 3, Color.white.getRGB());
		}
		
		

	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {
		if (isMouseHoovering(mouseX, mouseY) && button == 0) {
			parent.setSelected(this);
		}
	}

	public boolean isMouseHoovering(int x, int y) {
		return x > this.x && x < this.x + parent.width - 16 && y > this.y && y < this.y + 20;
	}

}

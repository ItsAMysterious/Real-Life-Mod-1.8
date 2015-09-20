package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiNameselection extends GuiScreen{
	private GuiModInit guiModInit;
	
	public GuiNameselection(GuiModInit themenu) {
		this.guiModInit=themenu;
	}
	
	public void initGui(){
		buttonList.add(new GuiButton(0, width-105, width-25,100,20, "Apply"));
	}
	
	public void drawScreen(int x, int y, float f){
		super.drawScreen(x, y, f);
		drawBackground(0);
	}
	
	public void updateScreen(){
		super.updateScreen();
	}
	
	public void actionPerformed(GuiButton b){
		switch (b.id) {
		case 0:
			Minecraft.getMinecraft().currentScreen=guiModInit;
			break;
		default:
			break;
		}
	}
	
	

}

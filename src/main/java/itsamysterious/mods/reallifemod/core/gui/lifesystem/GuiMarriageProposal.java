package itsamysterious.mods.reallifemod.core.gui.lifesystem;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiMarriageProposal extends GuiScreen{
	private EntityPlayer thePlayer;
	
	public GuiMarriageProposal(EntityPlayer p) {
		this.thePlayer=p;
	}
	
	
	public void initGui(){
		buttonList.add(new GuiButton(0, width/2-100, height/2-10, 200, 20, "Send marriage request"));
	}
	
	public void drawScreen(int i, int j, float f){
		super.drawScreen(i, j, f);
		drawCenteredString(fontRendererObj, "Interacting with "+thePlayer.getName(), width/2, height/2-30, Color.yellow.getRGB());
		
	}
	
}

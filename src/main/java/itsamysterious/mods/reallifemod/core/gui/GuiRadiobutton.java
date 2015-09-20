package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GuiRadiobutton extends Gui{
	private FontRenderer fontrenderObj;
	private int xPos,yPos,width,height;
	private boolean checked;
	private int mouseX;
	private int mouseY;
	private String text;
	private int id;
	
	public GuiRadiobutton(int id,int xPos, int yPos, int width, int height, String text)
	{
		this.id=id;
		this.xPos=xPos;
		this.yPos=yPos;
		this.width=width;
		this.height=height;
		this.text=text;
		this.fontrenderObj=Minecraft.getMinecraft().fontRendererObj;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void draw(){
		int tw = fontrenderObj.getStringWidth(text);
			drawRect(xPos+2+tw, yPos,xPos+tw+width,yPos+height, Color.gray.getRGB());
			if(checked){
				drawRect(xPos+3+tw, yPos+1,xPos+tw+1+width-2,yPos+1+height-2, Color.green.getRGB());
			}else{
				drawRect(xPos+3+tw, yPos+1,xPos+tw+1+width-2,yPos+1+height-2, Color.black.getRGB());
			}
			drawString(fontrenderObj, text, xPos+1, yPos+1, Color.white.getRGB());	
		}
	
	
	public boolean isChecked(){
		return checked;
	}
	
	public void onMouseClicked(int x, int y, int id){
		if(isMouseHoovering(x, y)){
			checked=!checked;
		}
	}

	public boolean isMouseHoovering(int x, int y) {
		int tw = fontrenderObj.getStringWidth(text);
		return x>xPos&&x<xPos+tw+width&&y>yPos&&y<yPos+height;
	}
	
	public String toString(){
		return this.text.toLowerCase();
	}

	public void setChecked(boolean b) {
		this.checked=b;
	}

	public int getHeight() {
		return  this.height;
	}
	
	public int getFullWidth(){
		return this.width+fontrenderObj.getStringWidth(text);
	}

	public void setPosition(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	
}

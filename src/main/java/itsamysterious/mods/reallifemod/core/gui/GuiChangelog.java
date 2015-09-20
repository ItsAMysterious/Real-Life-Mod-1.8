package itsamysterious.mods.reallifemod.core.gui;

import itsamysterious.mods.reallifemod.init.ConfigGui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

public class GuiChangelog extends GuiScreen{
	private GuiScreen parentScreen;
	private GuiTextField changes;
    private static final Logger logger = LogManager.getLogger();
	private List Lines;
	private int position;
	private boolean scroll;

	public GuiChangelog(GuiScreen parent) {
			this.parentScreen=parent;
	}
	public void initGui(){
		short short1 = 274;
		 if (this.Lines == null)
	        {
	            this.Lines = Lists.newArrayList();
	        try {
	        	String s;
				BufferedReader bufferedreader = new BufferedReader(new FileReader(new File(
						mc.mcDataDir.getCanonicalPath().replace("eclipse", "src/main/resources/changelog.txt"))));
				while ((s = bufferedreader.readLine()) != null)
	            {
					this.Lines.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s, short1));
					this.Lines.add("");		
	            }
				bufferedreader.close();
			} catch (IOException e) {
	            logger.error("Couldn\'t load credits", e);

			}}
		this.buttonList.add(new GuiButton(0, width-75, height-25, 75, 20, "OK"));
		this.buttonList.add(new GuiButton(1, width-55, height-45, 50, 20, "Down"));
		this.buttonList.add(new GuiButton(2, width-55, 45, 50, 20, "^"));
	}
	
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		drawBackground(width);
		if(scroll)
		position--;
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(0, 769);
        worldrenderer.startDrawingQuads();
        worldrenderer.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        int j1 = this.width;
        int k1 = this.height;
        worldrenderer.addVertexWithUV(0.0D, (double)k1, (double)this.zLevel, 0.0D, 1.0D);
        worldrenderer.addVertexWithUV((double)0, (double)k1, (double)this.zLevel, 1.0D, 1.0D);
        worldrenderer.addVertexWithUV((double)0, 0.0D, (double)this.zLevel, 1.0D, 0.0D);
        worldrenderer.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, 0.0D, 0.0D);
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, position, 0.0F);
		for(int i=0;i<Lines.size();i++){
			String s=(String)Lines.get(i);
			if(s.startsWith("[C][B]"))
				this.drawCenteredString(fontRendererObj, EnumChatFormatting.BOLD+s.substring(6), width/2, 45+i*5, Color.white.getRGB());
			else
				if(s.startsWith("[C]"))
				drawCenteredString(fontRendererObj, s.substring(3), width/2, 45+i*5, Color.white.getRGB());
				else
			if(s.startsWith("[B]"))
			this.drawString(fontRendererObj, EnumChatFormatting.BOLD+s.substring(3), 125, 45+i*5, Color.white.getRGB());
			else
				this.drawString(fontRendererObj, s, 125, 45+i*5, Color.white.getRGB());

		}
		 GlStateManager.popMatrix();
		this.drawGradientRect(0, 0, width, 45, Color.black.getRGB(), Color.white.getTransparency());
		drawCenteredString(fontRendererObj, "Changelog", width/2, 25, Color.white.getRGB());
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
		
	
    public void actionPerformed(GuiButton button){
    	if(button.id==0){
    		if(parentScreen instanceof ConfigGui){
    			((ConfigGui)this.parentScreen).openchangelog=0;
    	        ConfigGui parentConfigGui = (ConfigGui) this.parentScreen;
    	        parentConfigGui.needsRefresh = true;
    	        parentConfigGui.initGui();
    	        parentConfigGui.mc.displayGuiScreen(parentConfigGui);
    	    }
    	
    	    if (!(this.parentScreen instanceof ConfigGui))
    	        Keyboard.enableRepeatEvents(false);
		}
    	if(button.id==1){
    		this.scroll=!this.scroll;
    		//this.position--;
    	}
    	if(button.id==2){
    		if(this.position<0)
    		this.position++;
    	}

    	
    }
}

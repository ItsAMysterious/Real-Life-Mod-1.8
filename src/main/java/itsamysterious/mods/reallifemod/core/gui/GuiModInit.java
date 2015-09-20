package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Button;
import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.handlers.ClientHandler;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.packets.SetPropertiesPackage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GuiModInit extends GuiScreen {
	public static final int ID = 0;
	private GuiMainMenu menu;
	private int timeshown;
	private GuiButton nextButton;
	private GuiTextField namefield, surnamefield;

	private String ErrorMessage;
	private int ErrorColor = 0;
	private int ErrorDisplayLength = 0;
	private int ErrorPos = 0;
	boolean backwardsanimation;
	
	private GuiRadioGroup genderGroup;

	public GuiModInit() {
		Keyboard.enableRepeatEvents(true);
		nextButton = new GuiButton(1, width / 2 - 25, height / 2 + 10, "Next");
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(0, width - 105, height - 25, 100, 20, "Apply and continue"));
		namefield = new GuiTextField(1, fontRendererObj, 55, 110, 100, 10);
		surnamefield = new GuiTextField(2, fontRendererObj, 55, 125, 100, 10);
		namefield.setCanLoseFocus(true);
		surnamefield.setCanLoseFocus(true);
		genderGroup  = new GuiRadioGroup(5, height/2+50);
		genderGroup.buttonList.clear();
		genderGroup.addButton(new GuiRadiobutton(0,  0, 0,15,15,"Male"));
		genderGroup.addButton(new GuiRadiobutton(1,  0, 0,15,15,"Female"));
		genderGroup.singleChoice=true;
		genderGroup.xPos = 5;
		genderGroup.yPos=height/2+25;
	}
	
	public void updateScreen() {
		super.updateScreen();
		namefield.updateCursorCounter();
		surnamefield.updateCursorCounter();
		genderGroup.doUpdate();  
	}

	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		GL11.glPushMatrix();
		GL11.glScaled(1.5, 1.5, 1);
		GL11.glTranslated(-width / 6, -5, 0);
		drawCenteredString(fontRendererObj, "Thank you for using the Real Life Mod!", width / 2, 15, Color.yellow.getRGB());
		GL11.glScaled(1, 1, 1);
		GL11.glPopMatrix();

		drawString(fontRendererObj, "This is the automatic help function of the RLM. It will now lead you", 5, 35,Color.white.getRGB());
		drawString(fontRendererObj, "through the Mod step by step. To bring it up, just press "+ EnumChatFormatting.ITALIC + "'H' " + EnumChatFormatting.RESET + "while hovering over an", 5, 45,Color.white.getRGB());
		drawString(fontRendererObj,EnumChatFormatting.ITALIC + "Item/Block" + EnumChatFormatting.ITALIC + EnumChatFormatting.ITALIC+ " or " + EnumChatFormatting.ITALIC + "GuiScreen " + EnumChatFormatting.RESET+ "to get information about it.",5, 55, Color.white.getRGB());
		drawString(fontRendererObj, EnumChatFormatting.BOLD + "First of all ", 5, 75, Color.white.getRGB());
		drawString(fontRendererObj, "Set your Name & Surname:", 5, 90, Color.white.getRGB());
		drawString(fontRendererObj, "Name:", 5, 110, Color.white.getRGB());
		drawString(fontRendererObj, "Surname:", 5, 125, Color.white.getRGB());

		if (timeshown < 80) {
			timeshown++;
		}
		namefield.drawTextBox();
		surnamefield.drawTextBox();
		genderGroup.draw(x, y, f);
		super.drawScreen(x, y, f);
		displayErrorMessage();

	}

	@Override
	public void actionPerformed(GuiButton b) {
		if (b.id == 0) {
			if (!(namefield.getText().equals("")) && !(surnamefield.getText().equals(""))&&this.genderGroup.getSelectedbutton()!=null) {
				if (RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer) != null) {
					RLMPlayerProps props = RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer);
					props.setName(namefield.getText());
					props.setSurname(surnamefield.getText());
					props.setGender(genderGroup.getSelectedbutton().getText());
					RealLifeMod.network.sendToServer(new SetPropertiesPackage(Minecraft.getMinecraft().thePlayer.getEntityId(), surnamefield.getText(), genderGroup.getSelectedbutton().getText(),namefield.getText()));
					confirmClicked(true, 0);
				}
				this.mc.displayGuiScreen(new GuiGamestart());
			} else
				ErrorMessage = "Please fill both fields!";
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		namefield.textboxKeyTyped(typedChar, keyCode);
		surnamefield.textboxKeyTyped(typedChar, keyCode);
		if (keyCode == Keyboard.KEY_TAB) {
			surnamefield.setFocused(!surnamefield.isFocused());
			namefield.setFocused(!namefield.isFocused());
		}

		if (keyCode == Keyboard.KEY_RETURN) {
			if (namefield.getText().isEmpty() || surnamefield.getText().isEmpty()) {
				ErrorMessage = "Please fill both fields!";
			} else {
				actionPerformed((GuiButton) buttonList.get(0));
			}
		}
		if (keyCode != Keyboard.KEY_ESCAPE)
			super.keyTyped(typedChar, keyCode);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		genderGroup.onMouseClicked(mouseX, mouseY, mouseButton);
		namefield.mouseClicked(mouseX, mouseY, mouseButton);
		surnamefield.mouseClicked(mouseX, mouseY, mouseButton);
	}

	private void displayErrorMessage() {
		{
			if (backwardsanimation) {
				if (ErrorColor > Color.yellow.getRGB()) {
					ErrorColor -= 50;
				} else {
					backwardsanimation = false;
				}
			} else if (!backwardsanimation) {
				if (ErrorColor <= Color.green.getRGB()) {
					ErrorColor += 50;
				} else
					backwardsanimation = true;
			}
		}
		drawRect(0, height - 25, width - 105, height - 5, Color.black.getRGB());
		drawString(fontRendererObj, ErrorMessage, 5, height - 20, ErrorColor);
	}

}

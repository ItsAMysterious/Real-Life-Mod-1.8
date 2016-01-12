package itsamysterious.mods.reallifemod.core.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.Iterator;
import java.util.stream.Stream;

import org.apache.commons.lang3.EnumUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.lifesystem.EnumJob;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import itsamysterious.mods.reallifemod.core.lifesystem.enums.EnumGender;
import itsamysterious.mods.reallifemod.core.packets.SetPropertiesPackage;
import itsamysterious.mods.reallifemod.core.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

public class GuiModInit extends GuiScreen {
	public static final int ID = 100;
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
	private GuiDropdownMenu jobMenu;

	public GuiModInit() {
		Keyboard.enableRepeatEvents(true);
		nextButton = new GuiButton(1, width / 2 - 25, height / 2 + 10, "Next");
	}

	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, width - 105, height - 25, 100, 20, "Apply and continue"));
		super.initGui();
		namefield = new GuiTextField(1, fontRendererObj, 55, 110, 100, 10);
		surnamefield = new GuiTextField(2, fontRendererObj, 55, 125, 100, 10);
		namefield.setCanLoseFocus(true);
		surnamefield.setCanLoseFocus(true);
		genderGroup = new GuiRadioGroup(surnamefield.xPosition, surnamefield.yPosition + surnamefield.height + 5);
		jobMenu = new GuiDropdownMenu(5, genderGroup.yPos + 20, surnamefield.getWidth() + 10, "Job:      ");
		genderGroup.buttonList.clear();
		genderGroup.horizontal = true;
		genderGroup.addButton(new GuiRadiobutton(0, 0, 0, 10, 10, "Male"));
		genderGroup.addButton(new GuiRadiobutton(1, 0, 0, 10, 10, "Female"));
		genderGroup.singleChoice = true;

		jobMenu.setMaxHeight(4);
		int enumid = 0;
		for (EnumJob job : EnumUtils.getEnumList(EnumJob.class)) {
			jobMenu.contents.add(new GuiMenuItem(jobMenu, enumid, job.name()));
			enumid++;
		}

	}

	public void updateScreen() {
		super.updateScreen();
		namefield.updateCursorCounter();
		surnamefield.updateCursorCounter();
		genderGroup.doUpdate();

		if (namefield.getText().isEmpty() || surnamefield.getText().isEmpty()
				|| genderGroup.getSelectedbutton() == null) {
			((GuiButton) buttonList.get(0)).enabled = false;
		} else {
			((GuiButton) buttonList.get(0)).enabled = true;
		}

		if (namefield.isFocused()) {
			ErrorMessage = "The Name other players will see";
		}

		ErrorColor++;
		ErrorPos++;
		jobMenu.onUpdate();
	}

	public void drawScreen(int x, int y, float f) {
		drawBackground(0);
		jobMenu.draw(x, y);
		GL11.glPushMatrix();
		GL11.glScaled(1.5, 1.5, 1);
		GL11.glTranslated(-width / 6, -5, 0);
		drawCenteredString(fontRendererObj, "Thank you for using the Real Life Mod!", width / 2, 15,
				Color.yellow.getRGB());
		GL11.glScaled(1, 1, 1);
		GL11.glPopMatrix();

		drawString(fontRendererObj, "This is the automatic help function of the RLM. It will now lead you", 5, 35,
				Color.white.getRGB());
		drawString(fontRendererObj, "through the Mod step by step. To bring it up, just press "
				+ EnumChatFormatting.ITALIC + "'H' " + EnumChatFormatting.RESET + "while hovering over an", 5, 45,
				Color.white.getRGB());
		drawString(fontRendererObj,
				EnumChatFormatting.ITALIC + "Item/Block" + EnumChatFormatting.ITALIC + EnumChatFormatting.ITALIC
						+ " or " + EnumChatFormatting.ITALIC + "GuiScreen " + EnumChatFormatting.RESET
						+ "to get information about it.",
				5, 55, Color.white.getRGB());
		int jobMenuFullWidth = fontRendererObj.getStringWidth(jobMenu.labelText) + jobMenu.width;
		if (jobMenu.selected != null) {
			renderAlignedText(getJobDescription(jobMenu.selected.text), jobMenu.positionX + jobMenuFullWidth + 10,
					jobMenu.positionY, 250);
		}
		drawString(fontRendererObj, EnumChatFormatting.BOLD + "First of all ", 5, 75, Color.white.getRGB());
		drawString(fontRendererObj, "Set your name & surname:", 5, 90, Color.white.getRGB());
		drawString(fontRendererObj, "Name:", 5, 110, Color.white.getRGB());
		drawString(fontRendererObj, "Surname:", 5, 125, Color.white.getRGB());

		if (timeshown < 80) {
			timeshown++;
		}

		namefield.drawTextBox();
		surnamefield.drawTextBox();
		genderGroup.draw(x, y, f);
		RenderUtils.renderEntityAtPos(width - 50, height - 50, 60, 0, 0, Minecraft.getMinecraft().thePlayer);
		super.drawScreen(x, y, f);
		displayErrorMessage();
		GL11.glColor3f(1, 1, 1);

	}

	private void renderAlignedText(String text, int x, int y, int maxWidth) {
		/** Constant variables */
		String[] words;
		String[] lines = new String[] {};
		FontRenderer renderer = fontRendererObj;
		/** temporary variables */
		StringBuilder sb = new StringBuilder();
		String currentline = "";
		int linenumber = 0;
		/** Splitting the text up */
		words = text.split(" ");
		int currentlinewidth = 0;
		for (String word : words) {
			if ((currentlinewidth + renderer.getStringWidth(word)) < maxWidth) {
				sb.append(" " + word);
				if (linenumber == 0) {
					currentline = String.valueOf(sb);
				} else {
					currentline = String.valueOf(sb).split("/")[linenumber];
				}
			} else {
				sb.append("/" + word);
				currentline = word;
				linenumber++;
			}
			currentlinewidth = renderer.getStringWidth(currentline);

		}

		linenumber = String.valueOf(sb).split("/").length;
		lines = String.valueOf(sb).split("/");

		/** Rendering the text at the given position */
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			drawString(fontRendererObj, line, x, y + i * 10, Color.white.getRGB());
		}

	}

	private final String getJobDescription(String text) {
		InputStream textfile;
		String description = "";
		StringBuilder sb = new StringBuilder();
		boolean CopyToTextField = false;

		try {
			textfile = new FileInputStream(new File(Minecraft.getMinecraft().mcDataDir, "RLM/texts/Jobs.txt"));
			if (textfile != null) {
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new InputStreamReader(textfile));
				Stream<String> lines = reader.lines();
				for (Object line : lines.toArray()) {
					if (line.toString().contains("/n")) {
						CopyToTextField = false;
					}
					if (line.toString().contains(text) && line.toString().contains(":")) {
						CopyToTextField = true;
					}

		

					if (CopyToTextField==true) {
						sb.append(" " + line);
					}
				}
			}
			description = String.valueOf(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return description;
	}

	@Override
	public void actionPerformed(GuiButton b) {
		if (b.id == 0) {
			if (!this.namefield.getText().isEmpty() && !this.surnamefield.getText().isEmpty()
					&& this.genderGroup.getSelectedbutton() != null) {
				if (RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer) != null) {
					RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer).name = namefield.getText();
					RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer).surname = surnamefield.getText();
					RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer).gender = EnumGender
							.valueOf(genderGroup.getSelectedbutton().getText());
					RLMPlayerProps.get(Minecraft.getMinecraft().thePlayer).profession = EnumJob
							.valueOf(this.jobMenu.selected.text);

					RealLifeMod.network.sendToServer(
							new SetPropertiesPackage(this.namefield.getText(), this.surnamefield.getText(),
									this.genderGroup.getSelectedbutton().getText(), this.jobMenu.selected.text));
					mc.displayGuiScreen(new GuiGamestart());
				}
			} else
				ErrorMessage = "Please fill both fields!";
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
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

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		genderGroup.onMouseClicked(mouseX, mouseY, mouseButton);
		namefield.mouseClicked(mouseX, mouseY, mouseButton);
		surnamefield.mouseClicked(mouseX, mouseY, mouseButton);
		jobMenu.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	private void displayErrorMessage() {
		{
			if (backwardsanimation) {
				if (ErrorColor > Color.yellow.getRGB()) {
					ErrorColor--;
				} else {
					backwardsanimation = false;
				}
			} else if (!backwardsanimation) {
				if (ErrorColor < Color.green.getRGB()) {
					ErrorColor++;
				} else
					backwardsanimation = true;
			}
		}
		drawRect(0, height - 25, width - 100, height - 24, Color.gray.getRGB());
		drawRect(0, height - 24, width - 100, height - 5, Color.black.getRGB());
		drawRect(0, height - 6, width - 100, height - 5, Color.gray.getRGB());

		drawString(fontRendererObj, ErrorMessage, 5, height - 20, ErrorColor);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

}

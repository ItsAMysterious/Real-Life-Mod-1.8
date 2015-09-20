package itsamysterious.mods.reallifemod.init;

import org.lwjgl.input.Keyboard;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.gui.GuiChangelog;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig{
	
	public int openchangelog;

	public ConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(
				RealLifeMod.config.getCategory(Configuration.CATEGORY_GENERAL))
				.getChildElements(), Reference.NAME, false, false, GuiConfig
				.getAbridgedConfigPath(RealLifeMod.config.toString()));
	}
	
	public void initGui()
    {
		super.initGui();
		this.buttonList.add(new GuiButton(0, 5, 5, 75, 20, "Changelog"));
		//Code a changelog.txt-Reader!!!
    }
	
	@Override
    public void onGuiClosed()
    {
		super.onGuiClosed();
        this.entryList.onGuiClosed();
		if(this.openchangelog!=1){
        if (this.configID != null && this.parentScreen instanceof GuiConfig)
        {
            GuiConfig parentGuiConfig = (GuiConfig) this.parentScreen;
            parentGuiConfig.needsRefresh = true;
            parentGuiConfig.initGui();
        }

        if (!(this.parentScreen instanceof GuiConfig))
            Keyboard.enableRepeatEvents(false);
		}
    }
	
    public void actionPerformed(GuiButton button){
    	if(button.id==0){
    		this.openchangelog=1;
    		this.mc.displayGuiScreen(new GuiChangelog(this));
    	}
        RealLifeMod.config.save();
    }
}

package itsamysterious.mods.reallifemod.core.handlers;

import itsamysterious.mods.reallifemod.core.gui.GuiModInit;
import itsamysterious.mods.reallifemod.core.gui.lifesystem.GuiCharacterMenu;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID==GuiModInit.ID){
			return new GuiModInit();
		}
		else
		if(ID==GuiCharacterMenu.ID){
			return new GuiCharacterMenu();
		}
		return null;
	}

}

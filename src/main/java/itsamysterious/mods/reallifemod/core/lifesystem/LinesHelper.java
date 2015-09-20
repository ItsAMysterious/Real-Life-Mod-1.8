package itsamysterious.mods.reallifemod.core.lifesystem;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class LinesHelper {
	
	public static final ChatComponentText ThirstWarning=new ChatComponentText("You better get some water!!");
	public static final ChatComponentText ThirstWarning2=new ChatComponentText(EnumChatFormatting.RED+"DRINK SOMETHING!"+EnumChatFormatting.RESET+" Otherwhise you will die!");
	public static final ChatComponentText DyingOfThirst=new ChatComponentText("You're dying of" + EnumChatFormatting.BOLD+EnumChatFormatting.RED + "thirst"			+ EnumChatFormatting.WHITE + "!");

	public static final ChatComponentText ToiletWarning=new ChatComponentText("You better go and find a" +EnumChatFormatting.ITALIC+"toilet!!");
	public static final ChatComponentText ToiletWarning2=new ChatComponentText("Uh oh - Can't hold it!!");
	
	public static final ChatComponentText Nameinfo = new ChatComponentText("Your name is very important for every situation to get money, to get a job and of course for your mariage");


}

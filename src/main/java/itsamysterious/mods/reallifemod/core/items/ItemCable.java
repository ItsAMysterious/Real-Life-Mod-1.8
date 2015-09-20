package itsamysterious.mods.reallifemod.core.items;

import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCable extends Item {

	public ItemCable() {
		setUnlocalizedName("itemCable");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		RLMPlayerProps props = RLMPlayerProps.get(player);
		if (world.getTileEntity(pos) instanceof TileEntity_Electric) {
			TileEntity_Electric toConnectTile = (TileEntity_Electric) world.getTileEntity(pos);
			if (props.lastTile != null && props.lastTile != toConnectTile) {
				props.lastTile.connectTo(toConnectTile);
				world.markBlockForUpdate(toConnectTile.getPos());
				props.lastTile = toConnectTile;
			} else {
				player.addChatMessage(new ChatComponentText("Now in " + EnumChatFormatting.GREEN + "Connection Mode"
						+ EnumChatFormatting.RESET + "To stop connecting, hold SHIFT+RIGHTCLICK! "));
				props.lastTile = toConnectTile;
			}

		} else {
			if (world.getBlockState(pos).getBlock() instanceof BlockAir) {
				player.addChatMessage(new ChatComponentText("Stopped " + EnumChatFormatting.RED + "Connection Mode"));
				props.lastTile = null;
			} else {
				world.setBlockState(pos, RealLifeMod_Blocks.cable.getDefaultState());
			}

		}
		return true;
	}

}

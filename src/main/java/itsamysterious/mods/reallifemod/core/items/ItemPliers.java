package itsamysterious.mods.reallifemod.core.items;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.lifesystem.RLMPlayerProps;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPliers extends Item{
	
	public ItemPliers() {
		setUnlocalizedName("itemPliers");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
		RLMPlayerProps props = RLMPlayerProps.get(player);
		if(world.getTileEntity(pos) instanceof TileEntity_Electric){
			TileEntity_Electric tile = (TileEntity_Electric)world.getTileEntity(pos);
			if(tile.storedPos!=null){
				tile.disconnect();
				props.lastTile=null;
				if(world.isRemote){
					world.spawnParticle(EnumParticleTypes.CLOUD, pos.getX(), pos.getY(),pos.getZ(), 0.25, 0.5, 0.25);
					player.addChatComponentMessage(new ChatComponentText("The blocks are no longer connected"));
				}
			}
		}
		return stack;
	}
}

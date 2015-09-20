package itsamysterious.mods.reallifemod.core.items;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSolderingIron extends Item{
	
	public ItemSolderingIron() {
		setUnlocalizedName("solderingIron");
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
		setCreativeTab(RealLifeMod.Technologie);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		BlockPos p = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
		if(world.getTileEntity(p) instanceof TileEntity_Electric){
			TileEntity_Electric tile = (TileEntity_Electric) world.getTileEntity(p);
			if(!tile.isUsuable){
				tile.isUsuable=true;
			}
			world.markBlockForUpdate(tile.getPos());
		}
		return stack;
	}
}

package itsamysterious.mods.reallifemod.core.vehicles;

import java.util.ArrayList;
import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Vehicles {
	public static List<VehicleFile> vehicles = new ArrayList<VehicleFile>();

	public static VehicleFile get(String file) {
		for (VehicleFile f : vehicles) {
			if (f.vehicleName.equals(file)) {
				return f;
			}

		}
		return null;
	}

	public static void addVehicle(VehicleFile f) {
		vehicles.add(f);
	}

	public static void setupVehicles() {
		for (VehicleFile f : vehicles) {
			registerVehicle(f);
		}
	}

	public static VehicleFile getFromId(int i) {
		VehicleFile fi = null;
		for (VehicleFile f : vehicles) {
			if (f.getID() == i) {
				fi = f;
			}
		}
		return fi;
	}

	public static void registerVehicle(final VehicleFile f) {

		class newItem extends Item {
			public newItem() {
				setUnlocalizedName(f.vehicleName);
				setCreativeTab(RealLifeMod.Cars);
				GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
			}

			@Override
			public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
					EnumFacing side, float hitX, float hitY, float hitZ) {
				EntityCar car = new EntityCar(worldIn, f, pos.getX(), pos.getY() + 1, pos.getZ(), playerIn);
				if (!worldIn.isRemote) {
					worldIn.spawnEntityInWorld(car);
				}
				return true;
			}
		}
		Item i = new newItem();
		RealLifeMod_Items.carItems.add(i);
	}
}

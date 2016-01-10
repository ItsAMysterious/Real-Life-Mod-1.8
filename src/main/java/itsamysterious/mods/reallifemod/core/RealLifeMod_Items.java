package itsamysterious.mods.reallifemod.core;

import java.util.ArrayList;
import java.util.List;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import itsamysterious.mods.reallifemod.core.items.CopperIngot;
import itsamysterious.mods.reallifemod.core.items.ItemBattery;
import itsamysterious.mods.reallifemod.core.items.ItemCable;
import itsamysterious.mods.reallifemod.core.items.ItemCamera;
import itsamysterious.mods.reallifemod.core.items.ItemCircuitBoard;
import itsamysterious.mods.reallifemod.core.items.ItemCoil;
import itsamysterious.mods.reallifemod.core.items.ItemPliers;
import itsamysterious.mods.reallifemod.core.items.ItemScrabbedMetal;
import itsamysterious.mods.reallifemod.core.items.ItemSolderingIron;
import itsamysterious.mods.reallifemod.core.items.itemControlDevice;
import itsamysterious.mods.reallifemod.core.items.lifesystem.ItemBottle;
import itsamysterious.mods.reallifemod.core.items.lifesystem.ItemBottle_Filled;
import itsamysterious.mods.reallifemod.core.items.lifesystem.ItemDough;
import itsamysterious.mods.reallifemod.core.items.lifesystem.ItemFlour;
import itsamysterious.mods.reallifemod.core.items.lifesystem.ItemSalt;
import itsamysterious.mods.reallifemod.core.items.vehicles.ItemPylon;
import itsamysterious.mods.reallifemod.core.roads.signs.Signfile;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RealLifeMod_Items {
	public static List<Item> itemList = new ArrayList<Item>();
	public static Item circuitboard, itemscrabbedmetal, salt, flour, dough, pylon, cable, pliers, solderingIron,
			controlDevice, copperIngot, itemCoil, battery, camera, bottle, bottle_Filled;
	public static List<Item> carItems = new ArrayList<Item>();

	public static void defineItems() {
		itemList.add(circuitboard = new ItemCircuitBoard());
		itemList.add(itemscrabbedmetal = new ItemScrabbedMetal());
		itemList.add(Item.getItemFromBlock(RealLifeMod_Blocks.block_TV));
		itemList.add(salt = new ItemSalt());
		itemList.add(flour = new ItemFlour());
		itemList.add(dough = new ItemDough());
		itemList.add(pylon = new ItemPylon().setCreativeTab(RealLifeMod.Cars));
		itemList.add(cable = new ItemCable().setCreativeTab(RealLifeMod.Technologie));
		itemList.add(pliers = new ItemPliers().setCreativeTab(RealLifeMod.Technologie));
		itemList.add(solderingIron = new ItemSolderingIron());
		itemList.add(controlDevice = new itemControlDevice());
		itemList.add(copperIngot = new CopperIngot().setCreativeTab(RealLifeMod.Technologie));
		itemList.add(itemCoil = new ItemCoil().setCreativeTab(RealLifeMod.Technologie));
		itemList.add(battery = new ItemBattery().setCreativeTab(RealLifeMod.Technologie));
		itemList.add(camera = new ItemCamera());
		itemList.add(bottle = new ItemBottle().setCreativeTab(CreativeTabs.tabFood));
		itemList.add(bottle_Filled = new ItemBottle_Filled().setCreativeTab(CreativeTabs.tabFood));

	}

	public static void registerCarItemModels() {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		for (Item i : carItems) {
			renderItem.getItemModelMesher().register(i, 0,
					new ModelResourceLocation(Reference.ID + ":" + i.getUnlocalizedName().substring(5), "inventory"));
		}
	}

}

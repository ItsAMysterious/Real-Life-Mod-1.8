package itsamysterious.mods.reallifemod;

import java.io.File;

import com.sun.javafx.logging.Logger;

import itsamysterious.mods.reallifemod.common.CommonProxy;
import itsamysterious.mods.reallifemod.common.ServerTickHandler;
import itsamysterious.mods.reallifemod.config.RealLifeModConfig;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntityDigitalFrame;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntityTarmac;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Computer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Drawer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DrinksFridge;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Pinwheel;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_PowerLine;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TV;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Transformer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_VendingMachine;
import itsamysterious.mods.reallifemod.core.entities.EntityPylon;
import itsamysterious.mods.reallifemod.core.handlers.ClientHandler;
import itsamysterious.mods.reallifemod.core.handlers.CommonHandler;
import itsamysterious.mods.reallifemod.core.handlers.GuiHandler;
import itsamysterious.mods.reallifemod.core.handlers.Keybindings;
import itsamysterious.mods.reallifemod.core.handlers.WorldGenCopper;
import itsamysterious.mods.reallifemod.core.packets.CustomCollisionHandler;
import itsamysterious.mods.reallifemod.core.packets.CustomCollisionPacket;
import itsamysterious.mods.reallifemod.core.packets.MountHandler;
import itsamysterious.mods.reallifemod.core.packets.MountVehicleMessage;
import itsamysterious.mods.reallifemod.core.packets.PacketHandler;
import itsamysterious.mods.reallifemod.core.packets.PacketHandlerPropsSet;
import itsamysterious.mods.reallifemod.core.packets.PropertiesSetPackage;
import itsamysterious.mods.reallifemod.core.packets.SetPositionHandler;
import itsamysterious.mods.reallifemod.core.packets.SetPropertiesPackage;
import itsamysterious.mods.reallifemod.core.packets.UpdateVehiclePacket;
import itsamysterious.mods.reallifemod.core.roads.signs.Signs;
import itsamysterious.mods.reallifemod.core.tiles.RLMTileEntity;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasPump;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasTank;
import itsamysterious.mods.reallifemod.core.vehicles.EntityVehicle;
import itsamysterious.mods.reallifemod.core.vehicles.VehicleFile;
import itsamysterious.mods.reallifemod.core.vehicles.Vehicles;
import itsamysterious.mods.reallifemod.init.Reference;
import itsamysterious.mods.reallifemod.utils.CustomCreativeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.ID, version = Reference.VERSION, name = Reference.NAME, guiFactory = "itsamysterious.mods.reallifemod.init.GuiFactory")
public class RealLifeMod {
	public static int ModEntityID;
	private File vehiclefile;
	private File signfile;

	@Instance
	public static RealLifeMod instance;

	@SidedProxy(clientSide = "itsamysterious.mods.reallifemod.client.ClientProxy", serverSide = "itsamysterious.mods.reallifemod.common.CommonProxy")
	public static CommonProxy proxy;
	public static Configuration config;

	public static SimpleNetworkWrapper network;
	
	public static CreativeTabs Technologie = new CustomCreativeTab("Real Life Technologie") {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return RealLifeMod_Items.cable;
		}
	};

	public static CreativeTabs RLMFurniture = new CustomCreativeTab("Real Life Furniture") {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(RealLifeMod_Blocks.block_Drawer);
		}
	};

	public static CreativeTabs RLMDeco = new CustomCreativeTab("Real Life Decorative") {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(RealLifeMod_Blocks.parquet);
		}
	};

	public static final CreativeTabs Cars = new CustomCreativeTab("Real Life Cars") {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.minecart;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(proxy);
		FMLCommonHandler.instance().bus().register(instance);
		FMLCommonHandler.instance().bus().register(new CommonHandler());
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
		MinecraftForge.EVENT_BUS.register(new ClientHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(this.instance, new GuiHandler());
		RealLifeMod_Blocks.defineBlocks();
		RealLifeMod_Items.defineItems();

		this.vehiclefile = new File(MinecraftServer.getServer().getDataDirectory() + "/RLM/vehicles/");
		this.signfile = new File(MinecraftServer.getServer().getDataDirectory() + "/RLM/signs/");
		registerEntities();
		loadAddonPacks();
		Vehicles.setupVehicles();
		Signs.setupSigns();

		config = new Configuration(event.getSuggestedConfigurationFile());
		RealLifeModConfig.syncConfig();

		network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.ID);
		network.registerMessage(PacketHandler.class, SetPropertiesPackage.class, 0, Side.SERVER);
		network.registerMessage(PacketHandlerPropsSet.class, PropertiesSetPackage.class, 1, Side.CLIENT);
		network.registerMessage(MountHandler.class, MountVehicleMessage.class, 2, Side.SERVER);
		network.registerMessage(SetPositionHandler.class, UpdateVehiclePacket.class, 3, Side.SERVER);
		network.registerMessage(CustomCollisionHandler.class, CustomCollisionPacket.class, 4, Side.SERVER);

		setupTileEntities();
		GameRegistry.registerWorldGenerator(new WorldGenCopper(), 8);
		if (event.getSide().isClient()) {
			B3DLoader.instance.addDomain(Reference.ID);
		}
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		setupCrafting();
		if (event.getSide() == Side.CLIENT) {
			proxy.registerItemModels();
			proxy.registerRenderThings();
			RealLifeMod_Items.registerCarItemModels();
		}

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		config.load();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(Reference.ID)) {
			RealLifeModConfig.syncConfig();
		}
	}

	private void loadAddonPacks() {
		loadVehicles();
		loadSigns();
	}

	public void registerEntities() {
		ModEntityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerModEntity(EntityVehicle.class, "CarEntity", 1928, RealLifeMod.instance, 256, 3, true);
		EntityRegistry.registerModEntity(EntityPylon.class, "EntityPylon", ModEntityID++, RealLifeMod.instance, 80, 1,
				true);
		// EntityRegistry.registerModEntity(EntitySeat.class, "EntitySeat",
		// EntityRegistry.findGlobalUniqueEntityId(), RealLifeMod.instance, 80,
		// 1, true);

	}

	private void loadVehicles() {
		System.out.println("loaded vehicles");
		if (vehiclefile.exists()) {
			if (vehiclefile.isDirectory()) {
				for (File vehicle : vehiclefile.listFiles()) {
					if (vehicle.getName().endsWith(".rlm")) {
						Vehicles.addVehicle(new VehicleFile(vehicle));
					}
				}
			}
		} else {
			vehiclefile.mkdirs();
		}
	}

	private void loadSigns() {
		if (signfile.exists()) {
			if (signfile.isDirectory()) {
				for (File sign : signfile.listFiles()) {
					if (sign.getName().endsWith(".rlm")) {
						Signs.addSign(sign);
					}
				}
			}
		} else {
			signfile.mkdirs();
		}
	}

	private void setupTileEntities() {
		setupTile(TileEntityDigitalFrame.class);
		setupTile(TileEntity_TV.class);
		setupTile(TileEntity_Drawer.class);
		setupTile(TileEntity_Computer.class);
		setupTile(TileEntity_Toilet.class);
		setupTile(TileEntity_GasPump.class);
		setupTile(TileEntity_GasTank.class);
		setupTile(TileEntity_Sign.class);
		setupTile(TileEntity_Lantern.class);
		setupTile(TileEntity_VendingMachine.class);
		setupTile(TileEntity_DrinksFridge.class);
		setupTile(TileEntity_PowerLine.class);
		setupTile(TileEntity_Transformer.class);
		setupTile(TileEntity_Pinwheel.class);
		GameRegistry.registerTileEntity(TileEntityTarmac.class, TileEntityTarmac.class.getName());
	}

	public void setupTile(Class<? extends RLMTileEntity> class1) {
		GameRegistry.registerTileEntity(class1, class1.getName());
	}

	private void loadSongs() {
		/*
		 * File f = new File(Minecraft.getMinecraft().mcDataDir.getPath() +
		 * "/RLM/songs/"); if (f.exists()) { if (f.isDirectory()) { for (File
		 * song : f.listFiles()) { if (song.getName().endsWith(".ogg")) {
		 * VehicleRadio.addSong(f.getPath()); } } } Signs.setupSigns(); } else {
		 * f.mkdirs(); }
		 */
	}

	public void setupCrafting() {
		CraftingManager.getInstance().getRecipeList().remove(new Object[] { "###", '#', Items.wheat });

		GameRegistry.addShapelessRecipe(new ItemStack(RealLifeMod_Items.flour), Items.wheat);
		GameRegistry
				.addShapelessRecipe(new ItemStack(RealLifeMod_Items.dough, 1),
						new Object[] { new ItemStack(RealLifeMod_Items.salt, 1, 1),
								new ItemStack(RealLifeMod_Items.flour, 1, 1),
								new ItemStack(Items.water_bucket, 1, 1) });
		GameRegistry.addSmelting(RealLifeMod_Items.dough, new ItemStack(Items.bread, 3), 0.1f);
	}

}

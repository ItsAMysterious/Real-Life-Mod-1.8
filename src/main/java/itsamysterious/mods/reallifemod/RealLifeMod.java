package itsamysterious.mods.reallifemod;

import java.io.File;

import api.player.render.RenderPlayerAPI;
import itsamysterious.mods.reallifemod.client.ClientProxy;
import itsamysterious.mods.reallifemod.common.CommonProxy;
import itsamysterious.mods.reallifemod.common.ServerTickHandler;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Blocks;
import itsamysterious.mods.reallifemod.core.RealLifeMod_Items;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Chair;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Computer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DartBoard;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DigitalFrame;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Drawer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_DrinksFridge;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_IronFence;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Lantern;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Pinwheel;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_PowerLine;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Railing;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Ramp;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Shelf;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Sign;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TV;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_TVTable;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Table;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Toilet;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Transformer;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Urinal;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_VendingMachine;
import itsamysterious.mods.reallifemod.core.entities.EntityPylon;
import itsamysterious.mods.reallifemod.core.entities.EntitySit;
import itsamysterious.mods.reallifemod.core.eventhandlers.ClientHandler;
import itsamysterious.mods.reallifemod.core.eventhandlers.CommonHandler;
import itsamysterious.mods.reallifemod.core.eventhandlers.GuiHandler;
import itsamysterious.mods.reallifemod.core.eventhandlers.WorldGenCopper;
import itsamysterious.mods.reallifemod.core.gui.phone.PhoneRegistry;
import itsamysterious.mods.reallifemod.core.gui.phone.apps.Browser;
import itsamysterious.mods.reallifemod.core.gui.phone.apps.Phone;
import itsamysterious.mods.reallifemod.core.packets.ControlableInputPacket;
import itsamysterious.mods.reallifemod.core.packets.ControllableInputHandler;
import itsamysterious.mods.reallifemod.core.packets.CustomCollisionHandler;
import itsamysterious.mods.reallifemod.core.packets.CustomCollisionPacket;
import itsamysterious.mods.reallifemod.core.packets.DefecatePacket;
import itsamysterious.mods.reallifemod.core.packets.DefecationHandler;
import itsamysterious.mods.reallifemod.core.packets.KeyHeldHandler;
import itsamysterious.mods.reallifemod.core.packets.MountHandler;
import itsamysterious.mods.reallifemod.core.packets.MountVehicleMessage;
import itsamysterious.mods.reallifemod.core.packets.PacketDriveableKeyHeld;
import itsamysterious.mods.reallifemod.core.packets.PacketHandlerPropsSet;
import itsamysterious.mods.reallifemod.core.packets.PacketPlaySound;
import itsamysterious.mods.reallifemod.core.packets.PlaySoundHandler;
import itsamysterious.mods.reallifemod.core.packets.PropertiesSetPackage;
import itsamysterious.mods.reallifemod.core.packets.SetPositionHandler;
import itsamysterious.mods.reallifemod.core.packets.SetPropertiesHandler;
import itsamysterious.mods.reallifemod.core.packets.SetPropertiesPackage;
import itsamysterious.mods.reallifemod.core.packets.UpdateControlHandler;
import itsamysterious.mods.reallifemod.core.packets.UpdateControlPackage;
import itsamysterious.mods.reallifemod.core.packets.UpdateToiletHandler;
import itsamysterious.mods.reallifemod.core.packets.UpdateToiletPacket;
import itsamysterious.mods.reallifemod.core.packets.UpdateVehiclePacket;
import itsamysterious.mods.reallifemod.core.rendering.Entities.CustomPlayerRenderer;
import itsamysterious.mods.reallifemod.core.roads.signs.Signs;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasPump;
import itsamysterious.mods.reallifemod.core.tiles.TileEntity_GasTank;
import itsamysterious.mods.reallifemod.core.utils.CustomCreativeTab;
import itsamysterious.mods.reallifemod.core.utils.Physics;
import itsamysterious.mods.reallifemod.core.vehicles.EntityCar;
import itsamysterious.mods.reallifemod.core.vehicles.EntityDriveable;
import itsamysterious.mods.reallifemod.core.vehicles.EntitySeat;
import itsamysterious.mods.reallifemod.core.vehicles.EntityWheel;
import itsamysterious.mods.reallifemod.core.vehicles.VehicleFile;
import itsamysterious.mods.reallifemod.core.vehicles.Vehicles;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
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
import scala.tools.nsc.backend.icode.GenICode.PJUMP;

@Mod(modid = Reference.ID, version = Reference.VERSION, name = Reference.NAME, guiFactory = "itsamysterious.mods.reallifemod.init.GuiFactory")
public class RealLifeMod {
	public static int ModEntityID;
	private File vehiclefile;
	private File signfile;

	@Mod.Instance
	public static RealLifeMod instance;

	@SidedProxy(clientSide = "itsamysterious.mods.reallifemod.client.ClientProxy", serverSide = "itsamysterious.mods.reallifemod.common.CommonProxy")
	public static CommonProxy proxy;
	public static Configuration config;

	public static SimpleNetworkWrapper network;

	public static int[] GUIIDs = new int[0];

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
		FMLCommonHandler.instance().bus().register(instance);
		FMLCommonHandler.instance().bus().register(new ClientProxy());
		MinecraftForge.EVENT_BUS.register(proxy);
		MinecraftForge.EVENT_BUS.register(new ClientHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(RealLifeMod.instance, new GuiHandler());
		FMLCommonHandler.instance().bus().register(new CommonHandler());
		
		RenderPlayerAPI.register(Reference.ID, CustomPlayerRenderer.class);

		RealLifeMod_Blocks.defineBlocks();
		RealLifeMod_Items.defineItems();

		Physics.init();
		this.vehiclefile = new File(MinecraftServer.getServer().getDataDirectory() + "/RLM/vehicles/");
		this.signfile = new File(MinecraftServer.getServer().getDataDirectory() + "/RLM/signs/");
		registerEntities();
		loadAddonPacks();
		Vehicles.setupVehicles();
		Signs.setupSigns();
		setupApps();

		config = new Configuration(event.getSuggestedConfigurationFile());
		RealLifeModConfig.syncConfig();

		network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.ID);
		network.registerMessage(SetPropertiesHandler.class, SetPropertiesPackage.class, 0, Side.SERVER);
		network.registerMessage(PacketHandlerPropsSet.class, PropertiesSetPackage.class, 1, Side.CLIENT);
		network.registerMessage(MountHandler.class, MountVehicleMessage.class, 2, Side.SERVER);
		network.registerMessage(SetPositionHandler.class, UpdateVehiclePacket.class, 3, Side.SERVER);
		network.registerMessage(CustomCollisionHandler.class, CustomCollisionPacket.class, 4, Side.SERVER);
		network.registerMessage(UpdateControlHandler.class, UpdateControlPackage.class, 5, Side.SERVER);
		network.registerMessage(ControllableInputHandler.class, ControlableInputPacket.class, 6, Side.SERVER);
		network.registerMessage(KeyHeldHandler.class, PacketDriveableKeyHeld.class, 7, Side.SERVER);
		network.registerMessage(PlaySoundHandler.class, PacketPlaySound.class, 8, Side.CLIENT);
		network.registerMessage(DefecationHandler.class, DefecatePacket.class, 9, Side.SERVER);
		network.registerMessage(UpdateToiletHandler.class, UpdateToiletPacket.class, 10, Side.SERVER);

		setupTileEntities();
		GameRegistry.registerWorldGenerator(new WorldGenCopper(), 8);
		if (event.getSide().isClient()) {
			B3DLoader.instance.addDomain(Reference.ID);
		}
	}

	private void setupApps() {
		PhoneRegistry.registerApp(new Phone());
		PhoneRegistry.registerApp(new Browser());

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
		log("Registering Entities");
		ModEntityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerModEntity(EntityDriveable.class, "DriveableEntity", ModEntityID++, RealLifeMod.instance,
				256, 1, true);
		EntityRegistry.registerModEntity(EntityCar.class, "CarEntity", ModEntityID++, RealLifeMod.instance, 256, 1,
				true);
		EntityRegistry.registerModEntity(EntityPylon.class, "EntityPylon", ModEntityID++, RealLifeMod.instance, 80, 1,
				true);
		EntityRegistry.registerModEntity(EntityWheel.class, "EntityWheel", ModEntityID++, RealLifeMod.instance, 80, 1,
				true);
		EntityRegistry.registerModEntity(EntitySeat.class, "EntitySeat", ModEntityID++, RealLifeMod.instance, 80, 1,
				true);
		EntityRegistry.registerModEntity(EntitySit.class, "EntitySit", ModEntityID++, RealLifeMod.instance, 80, 1,
				false);

	}

	private void loadVehicles() {
		log("loaded vehicles");
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
		setupTile(TileEntity_DigitalFrame.class);
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
		setupTile(TileEntity_Ramp.class);
		setupTile(TileEntity_TVTable.class);
		setupTile(TileEntity_Shelf.class);
		setupTile(TileEntity_IronFence.class);
		setupTile(TileEntity_Railing.class);
		setupTile(TileEntity_Table.class);
		setupTile(TileEntity_DartBoard.class);
		setupTile(TileEntity_Chair.class);
		setupTile(TileEntity_Urinal.class);

	}

	public void setupTile(Class<? extends TileEntity> class1) {
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

	public static int findUnusedGuiID() {
		return GUIIDs[GUIIDs.length] + 1;
	}

	public static void log(String string) {
		System.out.println("[Real Life Mod] " + string);
	}

}

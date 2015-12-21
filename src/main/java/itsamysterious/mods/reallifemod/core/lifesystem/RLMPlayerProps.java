package itsamysterious.mods.reallifemod.core.lifesystem;

import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.gui.GuiGamestart;
import itsamysterious.mods.reallifemod.core.gui.GuiModInit;
import itsamysterious.mods.reallifemod.core.lifesystem.enums.EnumFeelings;
import itsamysterious.mods.reallifemod.core.lifesystem.enums.EnumGender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class RLMPlayerProps implements IExtendedEntityProperties {

	public static final String EXT_PROP_NAME = "RealLifeProperties";
	public TileEntity_Electric lastTile;

	public EntityPlayer player;
	public World world;

	/** Health */
	public double energy;
	public double stamina;
	public EnumGender gender;

	/** Bodyparts */
	public float Head;
	public float Chest;
	public float Arm_Right;
	public float Arm_Left;
	public float Leg_Right;
	public float Leg_Left;
	public float foot_Right;
	public float foot_Left;

	/** Economy and Community */
	public String name;
	public String surname;
	public float money;

	public boolean doneTutorial;
	public String partner;

	/** Counters */
	public double water; // Holds the current saturation of water in the player

	public double pee_value; // How much the player has to pee
	public double poop_value; // How much the player has to defecate

	public int timeWaterless;
	public int waterlowmessagetime;

	/** Temporary variables */
	public boolean pooping; // Determines wether the player should poop or not
	public boolean peeing; // Determines wether the player should poop or not
	public EnumFeelings feeling;

	public RLMPlayerProps() {
	}

	public RLMPlayerProps(EntityPlayer player) {
		this.player = player;
	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(RLMPlayerProps.EXT_PROP_NAME, new RLMPlayerProps(player));
	}

	public static final RLMPlayerProps get(EntityPlayer player) {
		return (RLMPlayerProps) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public void circleOfLife() {
		world = player.worldObj;
		if (world.isRemote) {
			// Starting the Tutorial if the player has no name
			if (this.name==null && this.surname==null) {
			//	Minecraft.getMinecraft().displayGuiScreen(new GuiModInit());
			}
		}

		if (player != null && !player.capabilities.isCreativeMode) {
			updateEffects();
		}
	}

	private void updateEffects() {
		if (getWater(player) < 40 && getWater(player) > 10 && waterlowmessagetime % 200 == 0) {
			player.addChatComponentMessage(LinesHelper.ThirstWarning);

		}
		if (getWater(player) < 10 && getWater(player) > 0.1) {
			player.addChatComponentMessage(LinesHelper.ThirstWarning2);
			player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 100));
		} else if (player.getActivePotionEffect(Potion.confusion) != null) {
			player.removePotionEffect(Potion.confusion.id);
		}

		if (getWater(player) < 0.1) {
			player.addPotionEffect(new PotionEffect(Potion.weakness.getId(), 100));
			timeWaterless++;
			if (timeWaterless == 200) {
				player.addChatComponentMessage(
						new ChatComponentText("Look at the empty bar at the top. It will hold your current amount of "
								+ EnumChatFormatting.AQUA + "Water" + EnumChatFormatting.RESET + "later on. "));
				// player.addChatComponentMessage(LinesHelper.DyingOfThirst);
				// player.setHealth(player.getHealth() - 1);
			}

		}

		if (pee_value > 50) {
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 1));
		} else {
			player.removePotionEffect(Potion.digSlowdown.getId());

		}

		CalculateFeelingFromSurroundings(this.player);

	}

	private void CalculateFeelingFromSurroundings(EntityPlayer player) {
		World world = player.worldObj;

		if (world.provider.isDaytime()) {
			feeling = EnumFeelings.Happy;
		}

		if (!world.provider.isDaytime()) {
			feeling = EnumFeelings.Tense;
		}

	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();

		// Saving the players name,gender & health
		properties.setString("Name", name);
		properties.setString("Surname", surname);
		properties.setString("Gender", EnumGender.toString(gender));
		properties.setDouble("Waterlevel", getWater(player));
		properties.setDouble("Peevalue", pee_value);
		properties.setDouble("Poopvalue", poop_value);
		properties.setDouble("Energy", energy);
		properties.setFloat("Money", money);

		// Saving all the counters
		properties.setDouble("Time_Waterless", timeWaterless);
		properties.setDouble("WaterMessage_Time", waterlowmessagetime);
		properties.setBoolean("Tutorial_Done", doneTutorial);

		// Saving the players cars -> for-loop through list of car entity-ids

		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound tag = compound.getCompoundTag(EXT_PROP_NAME);

		// Loading the players name,gender & health
		name = tag.getString("Name");
		surname = tag.getString("Surname");
		gender = EnumGender.getFromString(tag.getString("Gender"));
		water = tag.getDouble("Waterlevel");
		pee_value = tag.getDouble("Peevalue");
		poop_value = tag.getDouble("Poopvalue");
		energy = tag.getDouble("Energy");
		money = tag.getFloat("Money");

		// Saving all the counters
		timeWaterless = tag.getInteger("Time_Waterless");
		waterlowmessagetime = tag.getInteger("WaterMessage_Time");
		doneTutorial = tag.getBoolean("Tutorial_Done");

	}

	@Override
	public void init(Entity entity, World world) {
		if (entity instanceof EntityPlayer) {
			player = (EntityPlayer) entity;
			water = 2000;
			pee_value = 0;
			poop_value = 0;
		}
	}

	public void copy(RLMPlayerProps props) {
		this.name = props.name;
		this.surname = props.surname;
		this.gender = props.gender;

	}

	public void setGender(EnumGender gender) {
		this.gender = gender;
	}

	public static final String getFullname(EntityPlayerSP thePlayer) {
		return get(thePlayer).getName() + " " + get(thePlayer).getSurname();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public static double getWater(EntityPlayer player) {
		return RLMPlayerProps.get(player).water;
	}

	public static double getPee_Value(EntityPlayer player) {
		return RLMPlayerProps.get(player).pee_value;
	}

	public static double getPoop_Value(EntityPlayer player) {
		return RLMPlayerProps.get(player).poop_value;
	}

	public void setWater(double water) {
		this.water = water;
	}

}

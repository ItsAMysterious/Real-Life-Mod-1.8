package itsamysterious.mods.reallifemod.core.lifesystem;

import itsamysterious.mods.reallifemod.RealLifeMod;
import itsamysterious.mods.reallifemod.core.blocks.tiles.TileEntity_Electric;
import itsamysterious.mods.reallifemod.core.gui.GuiModInit;
import itsamysterious.mods.reallifemod.core.lifesystem.enums.EnumFeelings;
import itsamysterious.mods.reallifemod.core.lifesystem.enums.EnumGender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.fml.server.FMLServerHandler;

public class RLMPlayerProps implements IExtendedEntityProperties, IUpdatePlayerListBox{

	public static final String EXT_PROP_NAME = "RealLifeProperties";
	public TileEntity_Electric lastTile;

	public EntityPlayer player;
	public World world;

	/** Health */
	public double energy;
	public double stamina;
	public EnumGender gender;
	public EnumJob profession;

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
	public float cash;
	public float balance_bank;

	public boolean doneTutorial;
	public String partner;

	/** Counters */

	public double pee_value; // How much the player has to pee
	public double poop_value; // How much the player has to defecate

	public int timeWaterLevelless;
	public int WaterLowMessageTime;

	/** Temporary variables */
	public boolean pooping; // Determines wether the player should poop or not
	public boolean peeing; // Determines wether the player should poop or not
	public EnumFeelings feeling;
	private int updatetick;

	// Food & PeeLevel
	private double prevFoodLevel;
	public double WaterLevel; // Holds the current saturation of WaterLevel in
								// the player
	private double prevWaterLevel;

	private boolean loadedtag = false;

	public RLMPlayerProps() {
	}

	public RLMPlayerProps(EntityPlayer player) {
		this.player = player;
	}

	public static final void register(EntityPlayer player) {
		if (get(player) == null) {
			player.registerExtendedProperties(RLMPlayerProps.EXT_PROP_NAME, new RLMPlayerProps(player));
		}
	}

	public static final RLMPlayerProps get(EntityPlayer player) {
		return (RLMPlayerProps) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void update() {
		this.circleOfLife();
		
		if (gender==null&&loadedtag ==true) {
			player.openGui(RealLifeMod.instance, GuiModInit.ID, player.worldObj, 0, 0, 0);
		}
	}
	
	public void circleOfLife() {

		this.updatetick++;
		this.world = this.player.worldObj;

		if (player != null && !player.capabilities.isCreativeMode) {

			if (world.isRemote) {
				if (this.prevFoodLevel != this.player.getFoodStats().getFoodLevel()) {
					if (prevFoodLevel > this.player.getFoodStats().getFoodLevel()) {
						poop_value += 2.5;
					}
					this.prevFoodLevel = this.player.getFoodStats().getFoodLevel();

				}

			}

			if ((int) prevWaterLevel > (int) WaterLevel) {
				pee_value += 10;
			}
			this.prevWaterLevel = WaterLevel;

			if (this.WaterLevel > 0)
				this.WaterLevel -= 0.000833333333333333f;

			if (WaterLevel < 0)
				WaterLevel = 0;

			if (this.prevFoodLevel > this.player.getFoodStats().getFoodLevel()) {
				this.poop_value += 2.5;
			}

			if (this.peeing) {
				if (this.pee_value > 0) {
					this.pee_value--;
					this.WaterLevel -= 0.25;
				} else
					this.peeing = false;

			}

			if (this.pooping) {
				this.poop_value--;
			}

			updateEffects();
		}

	}

	private void updateEffects() {

		if (WaterLevel < 0.1) {
			// player.addPotionEffect(new PotionEffect(Potion.weakness.getId(),
			// 100));
			timeWaterLevelless++;
			if (timeWaterLevelless == 200) {
				player.addChatComponentMessage(
						new ChatComponentText("Look at the empty bar at the top. It will hold your current amount of "
								+ EnumChatFormatting.AQUA + "WaterLevel" + EnumChatFormatting.RESET + "later on. "));
				// player.addChatComponentMessage(LinesHelper.DyingOfThirst);
				// player.setHealth(player.getHealth() - 1);
			}

		}

		// poop_value = (100/19)*(19-player.getFoodStats().getFoodLevel());
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
		properties.setString("Name", this.name);
		properties.setString("Surname", this.surname);
		properties.setDouble("WaterLevel", this.WaterLevel);
		properties.setDouble("Peevalue", this.pee_value);
		properties.setDouble("Poopvalue", this.poop_value);
		properties.setDouble("Energy", this.energy);
		properties.setFloat("Balance_Cash", this.cash);
		properties.setFloat("Balance_Bank", this.balance_bank);

		properties.setString("Gender", this.gender.name());
		properties.setString("Profession", this.profession.name());
		// Saving all the counters
		properties.setDouble("Time_Waterless", this.timeWaterLevelless);
		properties.setDouble("WaterLowMessageTime", this.WaterLowMessageTime);
		properties.setBoolean("Tutorial_Done", this.doneTutorial);

		// Saving the players cars -> for-loop through list of car entity-ids
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound tag = compound.getCompoundTag(EXT_PROP_NAME);
		// Loading the players name,gender & health
		this.name = tag.getString("Name");
		this.surname = tag.getString("Surname");
		this.gender = EnumGender.valueOf(tag.getString("Gender"));

		this.WaterLevel = tag.getDouble("WaterLevel");
		this.pee_value = tag.getDouble("Peevalue");
		this.poop_value = tag.getDouble("Poopvalue");
		this.energy = tag.getDouble("Energy");
		this.cash = tag.getFloat("Balance_Cash");
		this.balance_bank = tag.getFloat("Balance_Bank");
		this.profession = EnumJob.valueOf(tag.getString("Profession"));
		// Saving all the counters
		this.timeWaterLevelless = tag.getInteger("Time_Waterless");
		this.WaterLowMessageTime = tag.getInteger("WaterLowMessageTime");
		this.doneTutorial = tag.getBoolean("Tutorial_Done");
		System.out.println("Succesfully loaded tag! Values are: ");
		System.out.println(this.name);
		System.out.println(this.surname);
		System.out.println(this.gender.name());
		System.out.println(this.profession.name());
		loadedtag=true;

	}

	@Override
	public void init(Entity entity, World world) {
		if (entity instanceof EntityPlayer) {
			player = (EntityPlayer) entity;
			WaterLevel = 20;
			poop_value = 0;
			pee_value = 50;
		}
	}

	public void copy(EntityPlayer old) {
		NBTTagCompound compound = new NBTTagCompound();
		RLMPlayerProps.get(old).saveNBTData(compound);
		RLMPlayerProps.get(player).loadNBTData(compound);
		RLMPlayerProps.get(player).updatetick = 0;

	}

	public void setGender(EnumGender gender) {
		this.gender = gender;
	}

	public static final String getFullname(EntityPlayer player) {
		return get(player).getName() + " " + get(player).getSurname();
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

	public double getPee_Value(EntityPlayer player) {
		return RLMPlayerProps.get(player).pee_value;
	}

	public double getPoop_Value(EntityPlayer player) {
		return RLMPlayerProps.get(player).poop_value;
	}

	public void setWaterLevel(double WaterLevel) {
		this.WaterLevel = WaterLevel;
	}

}

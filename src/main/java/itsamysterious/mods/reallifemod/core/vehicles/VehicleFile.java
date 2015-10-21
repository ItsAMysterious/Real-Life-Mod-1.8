package itsamysterious.mods.reallifemod.core.vehicles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import itsamysterious.mods.reallifemod.client.forgeobjmodelported.AdvancedModelLoader;
import itsamysterious.mods.reallifemod.client.forgeobjmodelported.IModelCustom;
import itsamysterious.mods.reallifemod.core.sounds.CustomSound;
import itsamysterious.mods.reallifemod.init.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.util.ResourceLocation;

public class VehicleFile {

	public double maxSpeed;
	public double maxReverseSpeed;

	public float mass;
	public float acceleration;
	public Vector3f dimensions;

	public Vector3f steeringwheelpos;

	public Vector3f[] wheelPositions = new Vector3f[4];

	public List<Vector3f> ridersPositions = new ArrayList<Vector3f>();

	public String iconFile;
	public String vehicleName;
	public int numDrivers;

	public String fileName;
	public String modelName;
	public String wheelsName;
	public String steeringWheelName;
	public String transparentpartsname;
	public String textureName;
	public ResourceLocation texture;
	public ResourceLocation speedometertexture;
	public CustomSound startsound;
	public CustomSound stopsound;
	public CustomSound throttlesound;

	public double price;

	public IModelCustom model;
	private int id;
	public float height=1;
	public float width=1;
	public VehicleTypes vehicleType=VehicleTypes.Car;

	public VehicleFile(File f) {
		loadFromFile(f);
		
		for(int i=0;i<3;i++){
			wheelPositions[i]=new Vector3f(1,1,1);
		}
	}

	private void loadFromFile(File f) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {

				if (line.startsWith("maxSpeed:")) {
					this.maxSpeed = Double.parseDouble(line.split(" ")[1]);
				}

				if (line.startsWith("maxReverseSpeed:")) {
					this.maxReverseSpeed = Double.parseDouble(line.split(" ")[1]);

				}
				if (line.startsWith("wheelPositions: ")) {

					for (int i = 0; i < 4; i++) {
						String posVector = line.split(":")[1].split(",")[i].trim();
						Vector3f position = new Vector3f();
						position.x = Float.parseFloat(posVector.split("/")[0]);
						position.y = Float.parseFloat(posVector.split("/")[1]);
						position.z = Float.parseFloat(posVector.split("/")[2]);

						wheelPositions[i] = position;
					}

				}

				/*
				 * if (line.startsWith("steeringWheelPos: ")) { Vector3f
				 * position = new Vector3f(); position.x =
				 * Float.parseFloat(line.split(" ")[1].split("|")[0]);
				 * position.y = Float.parseFloat(line.split("|")[1]); position.z
				 * = Float.parseFloat(line.split("|")[2]); this.steeringwheelpos
				 * = position;
				 * 
				 * }
				 */

				if (line.startsWith("numDrivers: ")) {
					this.numDrivers = Integer.parseInt(line.split(" ")[1]);
				}

				/*
				 * if (line.startsWith("seatPos_") && this.ridersPositions !=
				 * null) {
				 * 
				 * float f1 = Float.parseFloat(line.split(" "
				 * )[1].split("|")[0]); float f2 =
				 * Float.parseFloat(line.split("|")[1]); float f3 =
				 * Float.parseFloat(line.split("|")[2]); int i =
				 * Integer.parseInt(line.split("_")[1].split(":")[0]);
				 * ridersPositions.add(i, new Vector3f(f1, f2, f3)); }
				 */

				if (line.startsWith("iconFile:")) {
					this.iconFile = line.split(" ")[1];
				}

				if (line.startsWith("acceleration:")) {
					this.acceleration = Float.parseFloat(line.split(" ")[1]);
				}

				if (line.startsWith("mass:")) {
					this.mass = Float.parseFloat(line.split(" ")[1]);
				}

				if (line.startsWith("dimensions:")) {
					float f1 = Float.parseFloat(line.split(" ")[1].split(",")[0]);
					float f2 = Float.parseFloat(line.split(",")[1].split(",")[0]);
					float f3 = Float.parseFloat(line.split(",")[1].split(",")[0]);
					this.dimensions = new Vector3f(f1, f2, f3);
				}

				if (line.startsWith("vehicleName:")) {
					this.vehicleName = line.split(" ")[1];
				}

				if (line.startsWith("modelName:")) {
					this.modelName = line.split(" ")[1];
				}

				if (line.startsWith("wheelsName:")) {
					this.wheelsName = line.split(" ")[1];
				}

				if (line.startsWith("steeringWheelName:")) {
					this.steeringWheelName = line.split(" ")[1];
				}

				if (line.startsWith("transparentName:")) {
					this.transparentpartsname = line.split(" ")[1];
				}

				if (line.startsWith("textureName:")) {
					this.texture = new ResourceLocation("reallifemod:textures/vehicle/" + line.split(" ")[1] + ".png");
					this.textureName = line.split(" ")[1];
				}

				if (line.startsWith("fileName:")) {
					this.model = AdvancedModelLoader.loadModel(
							new ResourceLocation("reallifemod:models/vehicle/" + line.split(" ")[1] + ".obj"));
					this.fileName = line.split(" ")[1];
				}

				if (line.startsWith("sound_start:")) {
					this.startsound = new CustomSound(new ResourceLocation(Reference.ID + ":" + line.split(" ")[1]));
				}

				if (line.startsWith("sound_stop:")) {
					this.stopsound = new CustomSound(new ResourceLocation(Reference.ID + ":" + line.split(" ")[1]));
				}

				if (line.startsWith("price:")) {
					this.price = Double.parseDouble(line.split(" ")[1]);
				}

				if (line.startsWith("sound_throttle:")) {
					this.startsound = new CustomSound(new ResourceLocation(Reference.ID + ":" + line.split(" ")[1]));
				}

				if (line.startsWith("ID:")) {
					this.id = Integer.parseInt(line.split(" ")[1]);
				}
			}
			reader.close();
		} catch (IOException e) {
		}
	}

	public int getID() {
		return id;
	}

}

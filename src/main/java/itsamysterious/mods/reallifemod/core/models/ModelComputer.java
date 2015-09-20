package itsamysterious.mods.reallifemod.core.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelComputer extends ModelBase {
	// fields
	ModelRenderer keyboard;
	ModelRenderer mouse;
	ModelRenderer screen;
	ModelRenderer screen2;
	ModelRenderer screen3;

	public ModelComputer() {
		textureWidth = 128;
		textureHeight = 128;

		keyboard = new ModelRenderer(this, 0, 0);
		keyboard.addBox(0F, 0F, 0F, 12, 1, 5);
		keyboard.setRotationPoint(-7F, 23F, -6F);
		keyboard.setTextureSize(512, 256);
		keyboard.mirror = true;
		setRotation(keyboard, 0F, 0F, 0F);
		mouse = new ModelRenderer(this, 0, 13);
		mouse.addBox(0F, 0F, 0F, 2, 1, 3);
		mouse.setRotationPoint(6F, 23F, -5F);
		mouse.setTextureSize(512, 256);
		mouse.mirror = true;
		setRotation(mouse, 0F, 0F, 0F);
		screen = new ModelRenderer(this, 0, 18);
		screen.addBox(0F, 0F, 0F, 14, 10, 1);
		screen.setRotationPoint(-7.5F, 10F, 3.5F);
		screen.setTextureSize(512, 256);
		screen.mirror = true;
		setRotation(screen, 0F, 0F, 0F);
		screen2 = new ModelRenderer(this, 0, 7);
		screen2.addBox(0F, 0F, 0F, 2, 5, 1);
		screen2.setRotationPoint(-1.5F, 18F, 4.5F);
		screen2.setTextureSize(512, 256);
		screen2.mirror = true;
		setRotation(screen2, 0F, 0F, 0F);
		screen3 = new ModelRenderer(this, 9, 7);
		screen3.addBox(0F, 0F, 0F, 5, 1, 3);
		screen3.setRotationPoint(-3F, 23F, 3F);
		screen3.setTextureSize(512, 256);
		screen3.mirror = true;
		setRotation(screen3, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		keyboard.render(f5);
		mouse.render(f5);
		screen.render(f5);
		screen2.render(f5);
		screen3.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(Entity entity,float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}

package itsamysterious.mods.reallifemod.core.rendering.Entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AdvancedPlayerModel extends ModelBase
{
  //fields
    ModelRenderer leftbreast;
    ModelRenderer rightbreast;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer leftarmbottom;
    ModelRenderer tummy;
    ModelRenderer rightarmbottom;
    ModelRenderer leftlegbottom;
    ModelRenderer rightarm;
    ModelRenderer rightlegbottom;
  
  public AdvancedPlayerModel()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      leftbreast = new ModelRenderer(this, 0, 0);
      leftbreast.addBox(-1.5F, 0F, 0F, 3, 3, 3);
      leftbreast.setRotationPoint(2F, 2F, -2F);
      leftbreast.setTextureSize(64, 32);
      leftbreast.mirror = true;
      setRotation(leftbreast, -0.4363323F, 0F, 0F);
      rightbreast = new ModelRenderer(this, 0, 0);
      rightbreast.addBox(-1.5F, 0F, 0F, 3, 3, 3);
      rightbreast.setRotationPoint(-1.5F, 2F, -2F);
      rightbreast.setTextureSize(64, 32);
      rightbreast.mirror = true;
      setRotation(rightbreast, -0.4363323F, 0F, 0F);
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-4F, -8F, -4F, 8, 8, 8);
      head.setRotationPoint(0F, 0F, 0F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 16, 16);
      body.addBox(-4F, 0F, -2F, 8, 7, 4);
      body.setRotationPoint(0F, 0F, 0F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, -0.0174533F, 0F, 0F);
      leftarm = new ModelRenderer(this, 40, 16);
      leftarm.addBox(-1F, -2F, -2F, 4, 7, 4);
      leftarm.setRotationPoint(5F, 2F, 0F);
      leftarm.setTextureSize(64, 32);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 0, 16);
      rightleg.addBox(-2F, 0F, -2F, 4, 6, 4);
      rightleg.setRotationPoint(-2F, 12F, 0F);
      rightleg.setTextureSize(64, 32);
      rightleg.mirror = true;
      setRotation(rightleg, -0.0523599F, 0F, 0F);
      leftleg = new ModelRenderer(this, 0, 16);
      leftleg.addBox(-2F, 0F, -2F, 4, 6, 4);
      leftleg.setRotationPoint(2F, 12F, 0F);
      leftleg.setTextureSize(64, 32);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
      leftarmbottom = new ModelRenderer(this, 0, 0);
      leftarmbottom.addBox(-2F, -1F, -2F, 4, 7, 4);
      leftarmbottom.setRotationPoint(6F, 6F, 0F);
      leftarmbottom.setTextureSize(64, 32);
      leftarmbottom.mirror = true;
      setRotation(leftarmbottom, 0F, 0F, -0.0174533F);
      tummy = new ModelRenderer(this, 0, 0);
      tummy.addBox(-4F, 0F, -2F, 8, 6, 4);
      tummy.setRotationPoint(0F, 12F, 0F);
      tummy.setTextureSize(64, 32);
      tummy.mirror = true;
      setRotation(tummy, -3.141593F, 0F, 0F);
      rightarmbottom = new ModelRenderer(this, 0, 0);
      rightarmbottom.addBox(-2F, -1F, -2F, 4, 7, 4);
      rightarmbottom.setRotationPoint(-6F, 6F, 0F);
      rightarmbottom.setTextureSize(64, 32);
      rightarmbottom.mirror = true;
      setRotation(rightarmbottom, 0F, -3.141593F, 0F);
      leftlegbottom = new ModelRenderer(this, 0, 0);
      leftlegbottom.addBox(-2F, -1F, -2F, 4, 7, 4);
      leftlegbottom.setRotationPoint(2F, 18F, 0F);
      leftlegbottom.setTextureSize(64, 32);
      leftlegbottom.mirror = true;
      setRotation(leftlegbottom, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 40, 16);
      rightarm.addBox(-3F, -2F, -2F, 4, 7, 4);
      rightarm.setRotationPoint(-5F, 2F, 0F);
      rightarm.setTextureSize(64, 32);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      rightlegbottom = new ModelRenderer(this, 0, 0);
      rightlegbottom.addBox(-2F, -1F, -2F, 4, 7, 4);
      rightlegbottom.setRotationPoint(-2F, 18F, 0F);
      rightlegbottom.setTextureSize(64, 32);
      rightlegbottom.mirror = true;
      setRotation(rightlegbottom, 0.5410521F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    leftbreast.render(f5);
    rightbreast.render(f5);
    head.render(f5);
    body.render(f5);
    leftarm.render(f5);
    rightleg.render(f5);
    leftleg.render(f5);
    leftarmbottom.render(f5);
    tummy.render(f5);
    rightarmbottom.render(f5);
    leftlegbottom.render(f5);
    rightarm.render(f5);
    rightlegbottom.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}

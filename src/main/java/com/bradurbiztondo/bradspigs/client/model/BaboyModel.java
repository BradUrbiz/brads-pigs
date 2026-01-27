package com.bradurbiztondo.bradspigs.client.model;
// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;

public class BaboyModel extends EntityModel<BaboyEntity> {
	public static final EntityModelLayer LAYER_LOCATION =
			new EntityModelLayer(Identifier.of("bradspigs", "baboy"), "main");

	private final ModelPart baboy;
	private final ModelPart h_head;
	private final ModelPart body;
	private final ModelPart leftleg;
	private final ModelPart rightleg;
	private final ModelPart leftear;
	private final ModelPart rightear;
	public BaboyModel(ModelPart root) {
		this.baboy = root.getChild("baboy");
		this.h_head = this.baboy.getChild("h_head");
		this.body = this.baboy.getChild("body");
		this.leftleg = this.body.getChild("leftleg");
		this.rightleg = this.body.getChild("rightleg");
		this.leftear = this.body.getChild("leftear");
		this.rightear = this.body.getChild("rightear");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData baboy = modelPartData.addChild("baboy", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 39.0F, 0.0F));

		ModelPartData h_head = baboy.addChild("h_head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.62F, -23.59F, -1.69F, 8.0F, 5.0F, 5.0F, new Dilation(0.0F))
		.uv(8, 19).cuboid(-2.62F, -21.59F, 3.31F, 4.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(16, 19).cuboid(1.0F, -23.0F, 3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 10).cuboid(-3.0F, -23.0F, 3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body = baboy.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -20.0F, -3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftleg = body.addChild("leftleg", ModelPartBuilder.create().uv(10, 10).cuboid(-4.69F, -17.21F, 0.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 19).cuboid(-4.69F, -18.9F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData rightleg = body.addChild("rightleg", ModelPartBuilder.create().uv(0, 10).cuboid(1.38F, -17.21F, 0.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(8, 15).cuboid(1.38F, -18.9F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftear = body.addChild("leftear", ModelPartBuilder.create().uv(16, 15).cuboid(-4.0F, -25.66F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData rightear = body.addChild("rightear", ModelPartBuilder.create().uv(0, 15).cuboid(1.0F, -25.66F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(BaboyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		baboy.render(matrices, vertexConsumer, light, overlay, color);
	}
}
package com.bradurbiztondo.bradspigs.client.model;

import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BaboyModel extends EntityModel<BaboyEntity> {
	public static final EntityModelLayer LAYER_LOCATION =
			new EntityModelLayer(Identifier.of("bradspigs", "baboy"), "main");

	private final ModelPart baboy;
	private final ModelPart h_head;
	private final ModelPart body;
	private final ModelPart leftleg;
	private final ModelPart rightleg;
	private final ModelPart leftear;
	private final ModelPart righthole;
	private final ModelPart rightear;
	private final ModelPart leftarm;
	private final ModelPart tail;
	private final ModelPart rightarm;
	private final ModelPart lefthole;

	public BaboyModel(ModelPart root) {
		this.baboy = root.getChild("baboy");
		this.h_head = this.baboy.getChild("h_head");
		this.body = this.baboy.getChild("body");
		this.leftleg = this.body.getChild("leftleg");
		this.rightleg = this.body.getChild("rightleg");
		this.leftear = this.body.getChild("leftear");
		this.righthole = this.body.getChild("righthole");
		this.rightear = this.body.getChild("rightear");
		this.leftarm = this.body.getChild("leftarm");
		this.tail = this.body.getChild("tail");
		this.rightarm = this.body.getChild("rightarm");
		this.lefthole = this.body.getChild("lefthole");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData baboy = modelPartData.addChild("baboy", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 70.0F, 0.0F));

		ModelPartData h_head = baboy.addChild("h_head", ModelPartBuilder.create().uv(0, 0).cuboid(-13.86F, -70.77F, -5.07F, 27.0F, 15.0F, 15.0F, new Dilation(0.0F))
				.uv(0, 45).cuboid(-7.86F, -64.77F, 9.93F, 15.0F, 6.0F, 3.0F, new Dilation(0.0F))
				.uv(36, 69).cuboid(6.0F, -69.0F, 9.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(48, 69).cuboid(-9.0F, -69.0F, 9.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body = baboy.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftleg = body.addChild("leftleg", ModelPartBuilder.create().uv(30, 30).cuboid(-14.07F, -51.63F, 0.0F, 6.0F, 6.0F, 9.0F, new Dilation(0.0F))
				.uv(48, 57).cuboid(-14.07F, -56.7F, 0.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 0.0F, 0.0F));

		ModelPartData rightleg = body.addChild("rightleg", ModelPartBuilder.create().uv(0, 30).cuboid(4.14F, -51.63F, 0.0F, 6.0F, 6.0F, 9.0F, new Dilation(0.0F))
				.uv(0, 54).cuboid(4.14F, -56.7F, 0.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftear = body.addChild("leftear", ModelPartBuilder.create().uv(24, 57).cuboid(-12.0F, -75.98F, 0.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData righthole = body.addChild("righthole", ModelPartBuilder.create().uv(60, 69).cuboid(2.0F, -18.0F, 12.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -45.0F, 0.0F));

		ModelPartData rightear = body.addChild("rightear", ModelPartBuilder.create().uv(36, 45).cuboid(6.0F, -75.98F, 0.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftarm = body.addChild("leftarm", ModelPartBuilder.create().uv(60, 30).cuboid(-21.0F, -66.0F, 3.0F, 9.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData lefthand_r1 = leftarm.addChild("lefthand_r1", ModelPartBuilder.create().uv(60, 36).cuboid(-3.0F, -3.0F, -3.0F, 3.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-18.0F, -66.0F, 6.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(18, 69).cuboid(-3.0F, -15.0F, -9.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -45.0F, 0.0F));

		ModelPartData rightarm = body.addChild("rightarm", ModelPartBuilder.create().uv(60, 48).cuboid(12.0F, -66.0F, 3.0F, 9.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData righthand_r1 = rightarm.addChild("righthand_r1", ModelPartBuilder.create().uv(0, 66).cuboid(0.0F, -3.0F, -3.0F, 3.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(18.0F, -66.0F, 6.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData lefthole = body.addChild("lefthole", ModelPartBuilder.create().uv(60, 69).cuboid(-6.0F, -18.0F, 12.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -45.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(BaboyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		baboy.render(matrices, vertexConsumer, light, overlay, color);
	}
}
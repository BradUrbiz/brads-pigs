package com.bradurbiztondo.bradspigs.client.renderer;

import com.bradurbiztondo.bradspigs.BradsPigs;
import com.bradurbiztondo.bradspigs.client.model.BaboyModel;
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;

public class BaboyRenderer extends MobEntityRenderer<BaboyEntity, BaboyModel> {
    public BaboyRenderer(EntityRendererFactory.Context context) {
        super(context, new BaboyModel(context.getPart(BaboyModel.LAYER_LOCATION)), 0.4f);
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(BaboyEntity entity) {
        return Identifier.of(BradsPigs.MOD_ID, "textures/entity/baboy.png");
    }

    @Override
    public void render(BaboyEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
package com.bradurbiztondo.bradspigs.client.renderer;

import com.bradurbiztondo.bradspigs.BradsPigs;
import com.bradurbiztondo.bradspigs.client.model.BaboyModel;
import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BaboyRenderer extends MobEntityRenderer<BaboyEntity, BaboyModel> {
    public BaboyRenderer(EntityRendererFactory.Context context) {
        super(context, new BaboyModel(context.getPart(BaboyModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public Identifier getTexture(BaboyEntity entity) {
        return Identifier.of(BradsPigs.MOD_ID, "textures/entity/baboy.png");
    }
}
package com.bradurbiztondo.bradspigs;

import com.bradurbiztondo.bradspigs.client.model.BaboyModel;
import com.bradurbiztondo.bradspigs.client.renderer.BaboyRenderer;
import com.bradurbiztondo.bradspigs.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BradsPigsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.BABOY, BaboyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BaboyModel.LAYER_LOCATION, BaboyModel::getTexturedModelData);
    }
}
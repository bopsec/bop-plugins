package com.bop;

import com.google.inject.Inject;
import com.google.inject.Provides;
import javax.inject.Singleton;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Instance Coords",
        description = "Shows instance slot number and type",
        tags = {"instance", "coords"}
)
@Singleton
public class InstanceCoordsPlugin extends Plugin
{
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private InstanceCoordsOverlay overlay;

    @Inject
    private InstanceTileOverlay tileOverlay;

    @Provides
    InstanceCoordsConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(InstanceCoordsConfig.class);
    }

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
        overlayManager.add(tileOverlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        overlayManager.remove(tileOverlay);
    }
}

package com.bop;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("instancecoords")
public interface InstanceCoordsConfig extends Config
{
    @ConfigItem(
            keyName = "markTile",
            name = "Mark single tile",
            description = "Highlight the tile at the Slot X and Slot Y position",
            position = 0
    )
    default boolean markTile()
    {
        return false;
    }

    @ConfigItem(
            keyName = "tileX",
            name = "Slot X",
            description = "X from the overlay's Pos in slot readout",
            position = 1
    )
    default int tileX()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "tileY",
            name = "Slot Y",
            description = "Y from the overlay's Pos in slot readout",
            position = 2
    )
    default int tileY()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "additionalMarkers",
            name = "Additional tile markers",
            description = "Add any number of markers as X,Y,#RRGGBB entries separated by semicolons, for example 12,34,#FF0000; 20,21,#00FF00",
            position = 3
    )
    default String additionalMarkers()
    {
        return "";
    }
}

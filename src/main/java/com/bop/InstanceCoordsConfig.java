package com.bop;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("instancecoords")
public interface InstanceCoordsConfig extends Config
{
    @ConfigItem(
            keyName = "markTile",
            name = "Mark tile",
            description = "Highlight a tile at the configured position in the current instance slot",
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
}

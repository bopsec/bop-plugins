package com.bop;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

final class InstanceTileMarker
{
    final int x;
    final int y;
    final Color color;

    private InstanceTileMarker(int x, int y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    static List<InstanceTileMarker> parse(String value)
    {
        List<InstanceTileMarker> markers = new ArrayList<>();
        if (value == null || value.isEmpty())
        {
            return markers;
        }

        for (String entry : value.split("[;\\r\\n]+"))
        {
            String[] parts = entry.trim().split(",", -1);
            if (parts.length != 3)
            {
                continue;
            }

            String hex = parts[2].trim();
            if (!hex.matches("#[0-9a-fA-F]{6}"))
            {
                continue;
            }

            try
            {
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                markers.add(new InstanceTileMarker(x, y,
                        new Color(Integer.parseInt(hex.substring(1), 16))));
            }
            catch (NumberFormatException ignored)
            {
                // Ignore malformed entries without hiding the other markers.
            }
        }
        return markers;
    }
}

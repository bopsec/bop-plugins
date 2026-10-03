package com.bop;

import com.google.inject.Inject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.WorldView;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

@Singleton
public class InstanceTileOverlay extends Overlay
{
    private static final Color FILL = new Color(0, 255, 255, 60);
    private static final Color OUTLINE = Color.CYAN;

    private final Client client;
    private final InstanceCoordsConfig config;

    @Inject
    public InstanceTileOverlay(Client client, InstanceCoordsConfig config)
    {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!config.markTile())
        {
            return null;
        }

        Player player = client.getLocalPlayer();
        if (player == null)
        {
            return null;
        }

        WorldView worldView = player.getWorldView();
        if (worldView == null)
        {
            return null;
        }

        WorldPoint playerLocation = player.getWorldLocation();
        InstanceSlotCoords.Slot slot = playerLocation == null ? null
                : InstanceSlotCoords.from(playerLocation.getX(), playerLocation.getY());
        if (slot == null)
        {
            slot = InstanceSlotCoords.from(worldView.getBaseX(), worldView.getBaseY());
        }
        if (slot == null)
        {
            return null;
        }

        WorldPoint target = slot.tileAt(config.tileX(), config.tileY(), worldView.getPlane());
        if (target == null)
        {
            return null;
        }

        LocalPoint tile = LocalPoint.fromWorld(worldView, target);
        if (tile == null)
        {
            return null;
        }

        Polygon polygon = Perspective.getCanvasTilePoly(client, tile, worldView.getPlane());
        if (polygon != null)
        {
            OverlayUtil.renderPolygon(graphics, polygon, OUTLINE, FILL, new BasicStroke(2));
        }
        return null;
    }
}

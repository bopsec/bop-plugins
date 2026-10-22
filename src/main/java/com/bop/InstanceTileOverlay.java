package com.bop;

import com.google.inject.Inject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;
import java.util.Objects;
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
    private static final BasicStroke STROKE = new BasicStroke(2);

    private final Client client;
    private final InstanceCoordsConfig config;
    private String cachedMarkerText;
    private List<InstanceTileMarker> cachedMarkers;

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
        String markerText = config.additionalMarkers();
        if (!config.markTile() && (markerText == null || markerText.isEmpty()))
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

        if (config.markTile())
        {
            renderMarker(graphics, worldView, slot, config.tileX(), config.tileY(), Color.CYAN);
        }

        if (!Objects.equals(markerText, cachedMarkerText) || cachedMarkers == null)
        {
            cachedMarkerText = markerText;
            cachedMarkers = InstanceTileMarker.parse(markerText);
        }
        for (InstanceTileMarker marker : cachedMarkers)
        {
            renderMarker(graphics, worldView, slot, marker.x, marker.y, marker.color);
        }
        return null;
    }

    private void renderMarker(Graphics2D graphics, WorldView worldView, InstanceSlotCoords.Slot slot,
                              int x, int y, Color color)
    {
        WorldPoint target = slot.tileAt(x, y, worldView.getPlane());
        if (target == null)
        {
            return;
        }

        LocalPoint tile = LocalPoint.fromWorld(worldView, target);
        if (tile == null)
        {
            return;
        }

        Polygon polygon = Perspective.getCanvasTilePoly(client, tile, worldView.getPlane());
        if (polygon != null)
        {
            Color fill = new Color(color.getRed(), color.getGreen(), color.getBlue(), 60);
            OverlayUtil.renderPolygon(graphics, polygon, color, fill, STROKE);
        }
    }
}

package com.bop;

import com.google.inject.Inject;
import javax.inject.Singleton;

import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.TitleComponent;

import java.awt.*;

@Singleton
public class InstanceCoordsOverlay extends OverlayPanel
{
    private final Client client;

    @Inject
    public InstanceCoordsOverlay(Client client)
    {
        this.client = client;

        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.MED);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();

        if (client.getLocalPlayer() == null ||
                client.getLocalPlayer().getWorldView() == null)
        {
            return null;
        }

        panelComponent.getChildren().add(title("Instance Coords"));

        int baseX = client.getLocalPlayer().getWorldView().getBaseX();
        int baseY = client.getLocalPlayer().getWorldView().getBaseY();
        WorldPoint wp = client.getLocalPlayer().getWorldLocation();
        int trueX = wp.getX();
        int trueY = wp.getY();

        // Try true coords first, fall back to base coords.
        InstanceSlotCoords.Slot info = InstanceSlotCoords.from(trueX, trueY);
        if (info == null)
        {
            info = InstanceSlotCoords.from(baseX, baseY);
        }

        if (info == null)
        {
            panelComponent.getChildren().add(line("Status", "Not in instance"));
            return super.render(graphics);
        }

        panelComponent.getChildren().add(line("Type", info.type.name()));
        panelComponent.getChildren().add(line("True", trueX + ", " + trueY));
        panelComponent.getChildren().add(line("Base", baseX + ", " + baseY));
        panelComponent.getChildren().add(line("Slot #", String.valueOf(info.index)));
        panelComponent.getChildren().add(line("Pos in slot", info.localX + ", " + info.localY));

        return super.render(graphics);
    }

    private TitleComponent title(String text)
    {
        return TitleComponent.builder().text(text).color(Color.CYAN).build();
    }

    private TitleComponent line(String label, String value)
    {
        return TitleComponent.builder().text(label + ": " + value).color(Color.WHITE).build();
    }

}

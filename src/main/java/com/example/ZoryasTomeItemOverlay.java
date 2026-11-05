package com.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.TextComponent;

public class ZoryasTomeItemOverlay extends WidgetItemOverlay
{
    private static final Color TEXT_COLOR = Color.WHITE;
    private final ZoryasTomePlugin plugin;
    private final ZoryasTomeConfig config;

    @Inject
    ZoryasTomeItemOverlay(ZoryasTomePlugin plugin, ZoryasTomeConfig config)
    {
        this.plugin = plugin;
        this.config = config;
        showOnInventory();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem item)
    {
        if (itemId != ZoryasTomePlugin.CHARGED_TOME_ITEM_ID || plugin.getStacks() <= 0)
        {
            return;
        }

        Rectangle bounds = item.getCanvasBounds();
        TextComponent count = new TextComponent();
        count.setText(Integer.toString(plugin.getStacks()));
        count.setColor(TEXT_COLOR);
        count.setPosition(new Point(bounds.x + 2, bounds.y + 12));
        count.render(graphics);

        if (config.showTimer() && plugin.getTicksRemaining() > 0)
        {
            String timer = config.timerUnit() == ZoryasTomeConfig.TimerUnit.TICKS
                ? Integer.toString(plugin.getTicksRemaining())
                : Integer.toString((plugin.getTicksRemaining() * 3 + 4) / 5);
            TextComponent countdown = new TextComponent();
            countdown.setText(timer);
            countdown.setColor(TEXT_COLOR);
            int width = graphics.getFontMetrics().stringWidth(timer);
            countdown.setPosition(new Point(bounds.x + bounds.width - width - 2,
                bounds.y + bounds.height - 2));
            countdown.render(graphics);
        }
    }
}

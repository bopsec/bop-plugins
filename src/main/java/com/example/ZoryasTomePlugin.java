package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.Counter;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@PluginDescriptor(
	name = "Zorya's Tome Stacks",
	description = "Shows remaining Zorya's Tome stacks",
	tags = "book"
)
public class ZoryasTomePlugin extends Plugin
{
    private static final int STACKS_VARP = 5926;
    private static final int TIMER_TICKS = 50;
    static final int CHARGED_TOME_ITEM_ID = 34725;

    @Inject private Client client;
    @Inject private ItemManager itemManager;
    @Inject private InfoBoxManager infoBoxManager;
    @Inject private OverlayManager overlayManager;
    @Inject private ZoryasTomeItemOverlay itemOverlay;

    private Counter stackCounter;
    private int stacks;
    private int ticksRemaining;

    @Provides
    ZoryasTomeConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ZoryasTomeConfig.class);
    }

    @Override
    protected void startUp()
    {
        overlayManager.add(itemOverlay);
        updateStacks(false);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(itemOverlay);
        removeCounter();
        stacks = 0;
        ticksRemaining = 0;
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged event)
    {
        if (event.getVarpId() == STACKS_VARP)
        {
            updateStacks(true);
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (ticksRemaining > 0)
        {
            ticksRemaining--;
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOGGED_IN)
        {
            updateStacks(false);
        }
        else if (event.getGameState() == GameState.LOGIN_SCREEN)
        {
            removeCounter();
            stacks = 0;
            ticksRemaining = 0;
        }
    }

    int getStacks()
    {
        return stacks;
    }

    int getTicksRemaining()
    {
        return ticksRemaining;
    }

    private void updateStacks(boolean varpChanged)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return;
        }

        int newStacks = client.getVarpValue(STACKS_VARP);
        if (newStacks != stacks && varpChanged && newStacks > 0)
        {
            ticksRemaining = TIMER_TICKS;
        }
        stacks = newStacks;
        if (stacks <= 0)
        {
            ticksRemaining = 0;
            removeCounter();
            return;
        }

        if (stackCounter == null)
        {
            stackCounter = new Counter(itemManager.getImage(CHARGED_TOME_ITEM_ID), this, stacks);
            stackCounter.setTooltip("Zorya's Tome stacks");
            infoBoxManager.addInfoBox(stackCounter);
        }
        else
        {
            stackCounter.setCount(stacks);
        }
    }

    private void removeCounter()
    {
        if (stackCounter != null)
        {
            infoBoxManager.removeInfoBox(stackCounter);
            stackCounter = null;
        }
    }
}


package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("zoryasTomeStacks")
public interface ZoryasTomeConfig extends Config
{
    enum TimerUnit { TICKS, SECONDS }

    @ConfigItem(keyName = "showTimer", name = "Show inventory timer",
        description = "Show a countdown on the tome in your inventory")
    default boolean showTimer()
    {
        return true;
    }

    @ConfigItem(keyName = "timerUnit", name = "Timer unit",
        description = "Display the countdown in game ticks or seconds")
    default TimerUnit timerUnit()
    {
        return TimerUnit.TICKS;
    }
}

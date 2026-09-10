package com.bop;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("LootInside")
public interface LootInsideConfig extends Config
{
	@ConfigItem(
		keyName = "onlyBingo",
		name = "Only with clan events active",
		description = "Plugin only works if the clan events plugin is active",
		position = 0
	)
	default boolean onlyBingo()
	{
		return false;
	}
}

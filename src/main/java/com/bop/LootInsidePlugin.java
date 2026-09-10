package com.bop;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.PostMenuSort;
import net.runelite.api.gameval.ObjectID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginManager;

@PluginDescriptor(
	name = "Loot Inside",
	description = "For bingo"
)
public class LootInsidePlugin extends Plugin
{
	private static final String WARNING_OPTION = "Loot inside!";
	private static final String CLAN_EVENTS_PLUGIN_CLASS = "com.clanevents.ClanEventsPlugin";
	private static final String CHAT_MESSAGE = "You should loot inside!";

	@Inject
	private Client client;

	@Inject
	private PluginManager pluginManager;

	@Inject
	private LootInsideConfig config;

	@Provides
	LootInsideConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(LootInsideConfig.class);
	}

	@Subscribe(priority = -1)
	public void onPostMenuSort(PostMenuSort event)
	{
		if (client.getVarbitValue(VarbitID.TOB_SHOULD_HAVE_LOOT) == 0)
		{
			return;
		}
		if (config.onlyBingo()) {
			if (pluginManager.getPlugins().stream()
				.anyMatch(n ->
					CLAN_EVENTS_PLUGIN_CLASS.equals(n.getClass().getName()) && !pluginManager.isPluginActive(n))) {
				return;
			}
		}

		MenuEntry exitEntry = null;
		for (MenuEntry entry : client.getMenu().getMenuEntries())
		{
			if (entry.getIdentifier() == ObjectID.TOB_TREASUREROOM_TELEPORTOUT
				&& isGameObjectOption(entry.getType()))
			{
				exitEntry = entry;
				break;
			}
		}

		if (exitEntry == null)
		{
			return;
		}

		client.getMenu().createMenuEntry(-1)
			.setOption(WARNING_OPTION)
			.setTarget(exitEntry.getTarget())
			.setType(MenuAction.RUNELITE);
	}

	private static boolean isGameObjectOption(MenuAction action)
	{
		return action == MenuAction.GAME_OBJECT_FIRST_OPTION
			|| action == MenuAction.GAME_OBJECT_SECOND_OPTION
			|| action == MenuAction.GAME_OBJECT_THIRD_OPTION
			|| action == MenuAction.GAME_OBJECT_FOURTH_OPTION
			|| action == MenuAction.GAME_OBJECT_FIFTH_OPTION;
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		if (event.getMenuOption().equals(WARNING_OPTION)) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", CHAT_MESSAGE, "", false);
		}
	}
}

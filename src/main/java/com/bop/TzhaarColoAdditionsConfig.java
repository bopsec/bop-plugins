package com.bop;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("TzhaarColoAdditions")
public interface TzhaarColoAdditionsConfig extends Config
{
	@ConfigSection(
		name = "Inferno",
		description = "Inferno settings",
		position = 0
	)
	String infernoSection = "inferno";

	@ConfigSection(
		name = "Colosseum",
		description = "Fortis Colosseum settings",
		position = 1
	)
	String colosseumSection = "colosseum";

	@ConfigSection(
		name = "Pillar marking settings",
		description = "Also applies to Sol arena guards",
		position = 2
	)
	String pillarMarkingSection = "pillarMarking";

	@ConfigItem(
		keyName = "hideInfernoPillars",
		name = "Hide Inferno pillars",
		description = "Removes the visible pillar objects in the Inferno",
		position = 0,
		section = infernoSection
	)
	default boolean hideInfernoPillars()
	{
		return true;
	}

	@ConfigItem(
		keyName = "hideColosseumPillars",
		name = "Hide Colosseum pillars",
		description = "Removes the visible pillar objects in the Fortis Colosseum",
		position = 1,
		section = colosseumSection
	)
	default boolean hideColosseumPillars()
	{
		return true;
	}

	@ConfigItem(
		keyName = "markPillarTiles",
		name = "Mark pillar tiles",
		description = "Highlights the tiles underneath hidden pillars",
		position = 2,
		section = pillarMarkingSection
	)
	default boolean markPillarTiles()
	{
		return true;
	}

	@ConfigItem(
		keyName = "pillarMarkerStyle",
		name = "Pillar marker style",
		description = "Choose how hidden pillar tiles are marked",
		position = 3,
		section = pillarMarkingSection
	)
	default PillarMarkerStyle pillarMarkerStyle()
	{
		return PillarMarkerStyle.BORDERED_TILE;
	}

	@Alpha
	@ConfigItem(
		keyName = "pillarTileColor",
		name = "Pillar tile colour",
		description = "The colour used to highlight tiles underneath hidden pillars",
		position = 4,
		section = pillarMarkingSection
	)
	default Color pillarTileColor()
	{
		return new Color(
			40,
			40,
			40,
			150);
	}

	@ConfigItem(
		keyName = "hideInfernoOuterScene2",
		name = "Hide Inferno scenery",
		description = "Hides scenery and graphics objects outside the Inferno Arena. CAN BE BUGGY!",
		position = 5,
		section = infernoSection
	)
	default boolean hideInfernoOuterScene2()
	{
		return false;
	}

	@ConfigItem(
		keyName = "hideColosseumOuterScene2",
		name = "[experimental]Hide Colo scenery",
		description = "Hides (almost) everything outside the colosseum arena.",
		position = 9,
		section = colosseumSection
	)
	default boolean hideColosseumOuterScene2()
	{
		return false;
	}

	@ConfigItem(
		keyName = "hideSolArenaGuards",
		name = "Hide Sol arena guards",
		description = "Removes the guards that wall off the arena during the Sol Heredit fight",
		position = 7,
		section = colosseumSection
	)
	default boolean hideSolArenaGuards()
	{
		return false;
	}

	@ConfigItem(
		keyName = "markSolArenaGuardTiles",
		name = "Mark Sol arena guards",
		description = "Highlights the tiles underneath the Sol arena guards",
		position = 8,
		section = colosseumSection
	)
	default boolean markSolArenaGuardTiles()
	{
		return false;
	}
}

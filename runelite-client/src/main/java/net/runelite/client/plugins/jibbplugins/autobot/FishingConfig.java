package net.runelite.client.plugins.jibbplugins.autobot;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("Mining")
public interface FishingConfig extends Config {
    @ConfigSection(
            name = "General",
            description = "General",
            position = 0,
            closedByDefault = false
    )
    String generalSection = "general";

    @ConfigItem(
            keyName = "Fish",
            name = "Fish",
            description = "Choose the starting state",
            position = 0,
            section = generalSection
    )
    default FishingScript.BigState StartingState()
    {
        return FishingScript.BigState.KillingChickens;
    }

}

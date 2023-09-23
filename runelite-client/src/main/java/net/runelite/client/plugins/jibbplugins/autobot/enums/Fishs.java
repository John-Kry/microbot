package net.runelite.client.plugins.jibbplugins.autobot.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.game.FishingSpot;
import org.apache.commons.lang3.ArrayUtils;

@Getter
@RequiredArgsConstructor
public enum Fishs {
    SHRIMP("shrimp", FishingSpot.SHRIMP.getIds(),"net"),
    TROUT("trout/salmon", FishingSpot.SALMON.getIds(),"lure"),
    TUNA("tuna/swordfish", ArrayUtils.addAll(FishingSpot.LOBSTER.getIds(), FishingSpot.HARPOONFISH.getIds()),"harpoon"),
    LOBSTER("lobster", FishingSpot.LOBSTER.getIds(),"cage"),
    MONKFISH("monkfish", FishingSpot.MONKFISH.getIds(),"net"),

    LAVA_EEL("lava eel", FishingSpot.LAVA_EEL.getIds(), "lure"),

    SHARK("shark", FishingSpot.SHARK.getIds(),"harpoon");

    private final String name;
    private final int[] fishingSpot;
    private final String action;

    @Override
    public String toString() {
        return name;
    }
}

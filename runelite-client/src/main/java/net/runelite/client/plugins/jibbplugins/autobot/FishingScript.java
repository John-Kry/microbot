package net.runelite.client.plugins.jibbplugins.autobot;

import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.util.combat.Rs2Combat;
import net.runelite.client.plugins.microbot.util.npc.Rs2Npc;
import net.runelite.client.plugins.microbot.util.player.Rs2Player;
import net.runelite.client.plugins.microbot.util.tabs.Tab;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class FishingScript extends Script {

    public static double version = 1.3;

    public enum BigState{
       Init,
       KillingChickens,
       KillingCows,
       KillingGiants
    }

    private enum chickenState{
        Init,
        WalkingToChickens,
        Gearing,
        FindingChicken,
        KillingChicken
    }

    public static WorldPoint ChickenCoop = new WorldPoint(3237,3296,0);
    public void KillChickens(FishingConfig config){
        var s = chickenState.WalkingToChickens;
        if (Microbot.getClient().getLocalPlayer()
                .getWorldLocation()
                .distanceTo(ChickenCoop)>15){
            Microbot.getWalker().walkTo(ChickenCoop);
        }
        if (Arrays.stream(Rs2Npc.getNpcs()).anyMatch(npc -> npc.getName().equalsIgnoreCase("Chicken"))){
           s=chickenState.KillingChicken;
        }
        while(s != chickenState.Gearing){
            var attackLevel  = Microbot.getClient().getRealSkillLevel(Skill.ATTACK);
            var strength= Microbot.getClient().getRealSkillLevel(Skill.STRENGTH);
            if (attackLevel >= 15 && strength <50){
                Tab.switchToCombatOptionsTab();
                Rs2Combat.setAttackStyle(WidgetInfo.COMBAT_STYLE_TWO);
            }
            else if (strength >=50 && attackLevel <50){
                Tab.switchToCombatOptionsTab();
                Rs2Combat.setAttackStyle(WidgetInfo.COMBAT_STYLE_ONE);
            }else if(strength >=50 && attackLevel >=50){
                Tab.switchToCombatOptionsTab();
                Rs2Combat.setAttackStyle(WidgetInfo.COMBAT_STYLE_ONE);
            }

            switch (s) {
                case KillingChicken:
                    Microbot.status = "Looking for target";
                    var chicken = Rs2Npc.getAttackableNpcs();
                    for (var c : chicken){
                        if (!c.getName().equalsIgnoreCase("Chicken")){
                            continue;
                        }
                        if (c.isInteracting()){
                            continue;
                        }

                       if (c != null && !c.isDead()){
                            Rs2Npc.interact(c, "attack");
                            Microbot.status = "Sleeping Until Dead";
                            sleepUntil(()-> !Rs2Player.isAnimating() && c.isDead(),30000);
                            sleep(1000, 2000);
                            break;
                       }
                    }
                    break;
            }
        }
    }

    public boolean run(FishingConfig config) {
        var bigState = config.StartingState();
        mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            if (!super.run()) return;

            try {
                switch (bigState) {
                    case Init:
                        break;
                    case KillingChickens:
                        KillChickens(config);
                        break;
                    case KillingCows:
                        break;
                    case KillingGiants:
                        break;
                }
                if (Microbot.isMoving() || Microbot.isAnimating() || Microbot.pauseAllScripts) {
                    return;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        return true;
    }
}

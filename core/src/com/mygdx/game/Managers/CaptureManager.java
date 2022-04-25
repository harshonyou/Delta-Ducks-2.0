package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Faction;
import com.mygdx.game.PirateGame;
import com.mygdx.game.UI.Page;

public final class CaptureManager {

    public static boolean initialised = false;

    private static PirateGame reference;

    private static float captureBonus;
    private static float destroyBonus;

    public static void Initialise(PirateGame r) {
        if (initialised) {
            return;
        }
        initialised = true;
        reference = r;
        captureBonus = 0;
        destroyBonus = 0;
    }

    public static void pause() {
        if (!initialised) {
            return;
        }
        reference.pause();
    }

    public static void changeScreen() {
        if (!initialised) {
            return;
        }
        reference.setScreen(reference.pause);
    }

    public static void handler(Building flag) {
        if (!initialised) {
            return;
        }
        reference.pause();
        reference.capture.updateFlag(flag);
        reference.setScreen(reference.capture);
    }

//    public static void handlerResolver() {
//        if (!initialised) {
//            return;
//        }
//
//    }

    public static void setCaptureBonus(float b) {
        captureBonus = b;
    }

    public static void setDestroyBonus(float b) {
        destroyBonus = b;
    }

    public static void captureHandler(String name) {
        CaptionManager.setMaxTime(5f);
        CaptionManager.setDisplay("You have captured " + name +" college\nbut you will still need to fight it's ships.\n(Gold Gained : " +captureBonus+ ")");
        GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + captureBonus));
    }

    public static void destroyHandler(String name, int num) {
        CaptionManager.setMaxTime(5f);
        CaptionManager.setDisplay("You have destroyed " + name +" college\nand "+ num +" of it's ships.\n(Gold Gained : " +destroyBonus+ ")");
        GameManager.getPlayer().setPlunder((int) (GameManager.getPlayer().getPlunder() + destroyBonus));
    }
}

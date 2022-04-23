package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;

public class DifficultyManager {
    public static boolean initialised = false;

    public enum Difficulty {EASY, MEDIUM, HARD};
    public static Difficulty currentDifficulty;

    public static void Initialise(Difficulty difficulty) {
        if (initialised) {
            return;
        }
        initialised = true;
        currentDifficulty = difficulty;
        difficultyHandler();
    }

    private static void difficultyHandler() {
        switch (currentDifficulty) {
            case EASY:
                easyHandler();
                break;
            case MEDIUM:
                mediumHandler();
                break;
            default:
                hardHandler();
                break;
        }
    }

    private static void easyHandler() {
        System.out.println("Easy");
    }

    private static void mediumHandler() {
        System.out.println("Medium");
    }

    private static void hardHandler() {
        System.out.println("Hard");
    }
}

package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveManager {

    public static void save() {
        Preferences prefs = Gdx.app.getPreferences("saved_preference");

        prefs.putString("name", "Donald Duck");

        prefs.flush();
    }
}

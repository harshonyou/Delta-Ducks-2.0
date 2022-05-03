package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.UI.*;

import java.util.logging.Level;

/**
 * Contains class instances of game UI screens.
 */
public class PirateGame extends Game {
    public MenuScreen menu;
    public LevelScreen level;
    public GameScreen game;
    public PauseScreen pause;
    public EndScreen end;
    public Stage stage;
    public Skin skin;
    public CaptureScreen capture;
    private static int id_map;

    public static GameManager GM;

    /**
     * Create instances of game stage and UI screens.
     */
    @Override
    public void create() {
        ResourceManager.initFont("font/boy.ttf");
        loadResources();
        // cant load any more resources after this point (just functionally I choose not to implement)
        stage = new Stage(new ScreenViewport());
        createSkin();
        menu = new MenuScreen(this);
        level = new LevelScreen(this);
        game = new GameScreen(this, id_map);
        capture = new CaptureScreen(this);
        pause = new PauseScreen(this);
        end = new EndScreen(this);
        setScreen(menu);
//        setScreen(capture);
    }

    public static void loadResources() {
        // load resources
        int id_ship = ResourceManager.addTexture("ship.png");
        id_map = ResourceManager.addTileMap("Map.tmx");
//        System.out.println(id_map);
        int atlas_id = ResourceManager.addTextureAtlas("Boats.txt");
        int extras_id = ResourceManager.addTextureAtlas("UISkin/skin.atlas");
        int enhancement_id = ResourceManager.addTextureAtlas("UISkin/enhancement.atlas");
        int button_id = ResourceManager.addTextureAtlas("UISkin/buttons.atlas");
        int buildings_id = ResourceManager.addTextureAtlas("Buildings.txt");
        ResourceManager.addTexture("menuBG.jpg");
        ResourceManager.addTexture("Chest.png");

        ResourceManager.addTexture("blank.png");
        ResourceManager.addTexture("Arrow.png");
        ResourceManager.addTexture("Coin.png");
        ResourceManager.addTexture("HeatFull.png");
        ResourceManager.addTexture("Shield.png");

        ResourceManager.addTexture("darealthang.png");
        ResourceManager.addTexture("luffy.jpg");
        ResourceManager.addTexture("datshipdoe.png");

        ResourceManager.addTexture("Tome.png");
        ResourceManager.addTexture("ShieldT2.png");

        ResourceManager.addTexture("custom/ALL DUCK BOAT FINAL.png");

        ResourceManager.addTexture("Idle.png");

        //datshipdoe.png ALL DUCK BOAT FINAL.png
        ResourceManager.loadAssets();
    }

    /**
     * Clean up prevent memory leeks
     */
    @Override
    public void dispose() {
        menu.dispose();
        game.dispose();
        stage.dispose();
        skin.dispose();
    }

    /**
     * load ui skin from assets
     */
    private void createSkin() {
        skin = new Skin(Gdx.files.internal("UISkin/skin.json"));
    }
}
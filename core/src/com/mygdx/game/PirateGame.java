package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

    /**
     * Create instances of game stage and UI screens.
     */
    @Override
    public void create() {
        // load resources
        int id_ship = ResourceManager.addTexture("ship.png");
        int id_map = ResourceManager.addTileMap("Map.tmx");
        int atlas_id = ResourceManager.addTextureAtlas("Boats.txt");
        int extras_id = ResourceManager.addTextureAtlas("UISkin/skin.atlas");
        int enhancement_id = ResourceManager.addTextureAtlas("UISkin/enhancement.atlas");
        int button_id = ResourceManager.addTextureAtlas("UISkin/buttons.atlas");
        int buildings_id = ResourceManager.addTextureAtlas("Buildings.txt");
        ResourceManager.addTexture("menuBG.jpg");
        ResourceManager.addTexture("Chest.png");
        ResourceManager.addTexture("blank.png");
        ResourceManager.loadAssets();
        // cant load any more resources after this point (just functionally I choose not to implement)
        stage = new Stage(new ScreenViewport());
        createSkin();
        menu = new MenuScreen(this);
        level = new LevelScreen(this);
        game = new GameScreen(this, id_map);
        pause = new PauseScreen(this);
        end = new EndScreen(this);
        setScreen(menu);
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
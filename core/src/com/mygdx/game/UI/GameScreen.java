package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Components.ComponentEvent;
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Entitys.College;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Entitys.Ship;
import com.mygdx.game.Managers.*;
import com.mygdx.game.PirateGame;
import com.mygdx.game.Quests.Quest;

import static com.mygdx.utils.Constants.*;

public class GameScreen extends Page {
    private Label healthLabel;
    private Label armorLabel;
    private Label dosh;
    private Label ammo;
    private final Label questDesc;
    private final Label questName;
    private int enhancement_id;
    private int button_id;

    private Label healthTax;
    private Label speedTax;
    private Label ammoTax;
    private Label armorTax;
    private Label immunityTax;
    private Label bulletspeedTax;

    private Pixmap pixmap;
    private float ratio = .045f;
    private Window minimapWindow;
    private Table minimapTable;

    private Table caption;
    private Label cc;

    /*private final Label questComplete;
    private float showTimer = 0;
    // in seconds
    private static final float showDuration = 1;*/

    /**
     * Boots up the actual game: starts PhysicsManager, GameManager, EntityManager,
     * loads texture atlases into ResourceManager. Draws quest and control info.
     *
     * @param parent PirateGame UI screen container
     * @param id_map the resource id of the tile map
     */
    public GameScreen(PirateGame parent, int id_map) {
        super(parent);
        INIT_CONSTANTS();
        PhysicsManager.Initialise(false); // drawing debug mode

        /*int id_ship = ResourceManager.addTexture("ship.png");
        int id_map = ResourceManager.addTileMap("Map.tmx");
        int atlas_id = ResourceManager.addTextureAtlas("Boats.txt");
        int extras_id = ResourceManager.addTextureAtlas("UISkin/skin.atlas");
        int buildings_id = ResourceManager.addTextureAtlas("Buildings.txt");
        ResourceManager.loadAssets();*/


        GameManager.SpawnGame(id_map);
        //QuestManager.addQuest(new KillQuest(c));
        enhancement_id = ResourceManager.getId("UISkin/enhancement.atlas");
        button_id = ResourceManager.getId("UISkin/buttons.atlas");


        EntityManager.raiseEvents(ComponentEvent.Awake, ComponentEvent.Start);

        Window questWindow = new Window("Current Quest", parent.skin);

        questWindow.getTitleLabel().setAlignment(3);

        Quest q = QuestManager.currentQuest();
        Table t = new Table();
        questName = new Label("NAME", parent.skin);
        t.add(questName);
        t.row();
        questDesc = new Label("DESCRIPTION", parent.skin);
        if (q != null) {
            questName.setText(q.getName());
            questDesc.setText(q.getDescription());
        }
        /*questComplete = new Label("", parent.skin);
        actors.add(questComplete);*/

        t.add(questDesc).left();

        questWindow.add(t);
//        actors.add(questWindow);


        minimapWindow = new Window("Minimap", parent.skin);
        minimapWindow.getTitleLabel().setAlignment(3);

        minimapTable = new Table();

        minimapTable.setFillParent(true);
        actors.add(minimapTable);
        minimapTable.bottom().left();

//        actors.add(minimapWindow);

        Table t1 = new Table();
        t1.top().right();
        t1.setFillParent(true);
        actors.add(t1);

        Window tutWindow = new Window("Controls", parent.skin);

        tutWindow.getTitleLabel().setAlignment(3);


        Table table = new Table();
        tutWindow.add(table);
        t1.add(tutWindow);

        table.add(new Label("Move with", parent.skin)).top().left();
        table.add(new Image(parent.skin, "key-w"));
        table.add(new Image(parent.skin, "key-s"));
        table.add(new Image(parent.skin, "key-a"));
        table.add(new Image(parent.skin, "key-d"));
        table.row();
        table.add(new Label("Shoot in direction of mouse", parent.skin)).left();
        //table.add(new Image(parent.skin, "space"));
        table.add(new Image(parent.skin, "mouse"));
        table.row();
        table.add(new Label("Shoot in direction of ship", parent.skin)).left();
        table.add(new Image(parent.skin, "space"));
        table.row();
        table.add(new Label("Pause", parent.skin)).left();
        table.add(new Image(ResourceManager.getSprite(button_id, "keyboard_162.png")));

        EnhancementManager.Initialise();

        Window enhWindow = new Window("Enhancements", parent.skin);

        enhWindow.getTitleLabel().setAlignment(3);

        Table enhTable = new Table();
        enhWindow.add(enhTable);

//        enhTable.add(new Label("Press 1", parent.skin)).top().left();
        enhTable.add();
        enhTable.add(new Label("Button", parent.skin)).center();
        enhTable.add();
        enhTable.add(new Label("Cost", parent.skin)).right();
        enhTable.row();

        enhTable.add(new Image(ResourceManager.getSprite(button_id, "keyboard_1.png"))).size(.6f*TILE_SIZE);
        enhTable.add(new Label("Health", parent.skin)).top().left();
        enhTable.add(new Image(ResourceManager.getSprite(enhancement_id, "Icons_12.png"))).size(.6f * TILE_SIZE).padLeft(10f);
        healthTax = new Label("N/A", parent.skin);
        enhTable.add(healthTax).right();
        enhTable.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Coin.png")))).size(.6f * TILE_SIZE);
        enhTable.row();

        enhTable.add(new Image(ResourceManager.getSprite(button_id, "keyboard_2.png"))).size(.6f*TILE_SIZE);;
        enhTable.add(new Label("Speed", parent.skin)).top().left();
        enhTable.add(new Image(ResourceManager.getSprite(enhancement_id, "Icons_10.png"))).size(.6f * TILE_SIZE).padLeft(10f);
        speedTax = new Label("N/A", parent.skin);
        enhTable.add(speedTax).right();
        enhTable.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Coin.png")))).size(.6f * TILE_SIZE);
        enhTable.row();

        enhTable.add(new Image(ResourceManager.getSprite(button_id, "keyboard_3.png"))).size(.6f*TILE_SIZE);;
        enhTable.add(new Label("Ammo", parent.skin)).top().left();
        enhTable.add(new Image(ResourceManager.getSprite(enhancement_id, "Icons_29.png"))).size(.6f * TILE_SIZE).padLeft(10f);
        ammoTax = new Label("N/A", parent.skin);
        enhTable.add(ammoTax).right();
        enhTable.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Coin.png")))).size(.6f * TILE_SIZE);
        enhTable.row();

        enhTable.add(new Image(ResourceManager.getSprite(button_id, "keyboard_4.png"))).size(.6f*TILE_SIZE);;
        enhTable.add(new Label("Armor", parent.skin)).top().left();
        enhTable.add(new Image(ResourceManager.getSprite(enhancement_id, "Icons_40.png"))).size(.6f * TILE_SIZE).padLeft(10f);
        armorTax = new Label("N/A", parent.skin);
        enhTable.add(armorTax).right();
        enhTable.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Coin.png")))).size(.6f * TILE_SIZE);
        enhTable.row();

        enhTable.add(new Image(ResourceManager.getSprite(button_id, "keyboard_5.png"))).size(.6f*TILE_SIZE);;
        enhTable.add(new Label("Immunity", parent.skin)).top().left();
        enhTable.add(new Image(ResourceManager.getSprite(enhancement_id, "Icons_30.png"))).size(.6f * TILE_SIZE).padLeft(10f);
        immunityTax = new Label("N/A", parent.skin);
        enhTable.add(immunityTax).right();
        enhTable.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Coin.png")))).size(.6f * TILE_SIZE);
        enhTable.row();

        enhTable.add(new Image(ResourceManager.getSprite(button_id, "keyboard_6.png"))).size(.6f*TILE_SIZE);;
        enhTable.add(new Label("Bullet Speed", parent.skin)).top().left();
        enhTable.add(new Image(ResourceManager.getSprite(enhancement_id, "Icons_22.png"))).size(.6f * TILE_SIZE).padLeft(10f);
        bulletspeedTax = new Label("N/A", parent.skin);
        enhTable.add(bulletspeedTax).right();
        enhTable.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Coin.png")))).size(.6f * TILE_SIZE);
        enhTable.row();

        Table t2 = new Table();
        t2.bottom().right();
        t2.setFillParent(true);
        actors.add(t2);
//        enhTable.bottom().right();
//        enhTable.setFillParent(true);
//        enhTable.setColor(new Color(0.1f, 0.1f, 0.1f, .5f));
//        actors.add(enhTable);

//        enhTable.debug();
//        enhTable.scaleBy(.5f);

        t2.add(enhWindow);
//        t2.scaleBy(.5f);

//        DifficultyManager.Initialise(DifficultyManager.Difficulty.EASY);

        caption = new Table();
        cc = new Label("", parent.skin);
//        cc.setSize(50,50);
        cc.setColor(Color.WHITE);

        caption.add(cc);
        caption.bottom();
//        caption.bottom().center();
        caption.setFillParent(true);
//        caption.debug();
        caption.padBottom(20f);
        actors.add(caption);
    }

    private float accumulator;

    /**
     * Called every frame calls all other functions that need to be called every frame by rasing events and update methods
     *
     * @param delta delta time
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(BACKGROUND_COLOUR.x, BACKGROUND_COLOUR.y, BACKGROUND_COLOUR.z, 1);

        EntityManager.raiseEvents(ComponentEvent.Update, ComponentEvent.Render);

        accumulator += EntityManager.getDeltaTime();

        // fixed update loop so that physics manager is called regally rather than somewhat randomly
        while (accumulator >= PHYSICS_TIME_STEP) {
            PhysicsManager.update();
            accumulator -= PHYSICS_TIME_STEP;
        }

        GameManager.update();
        // show pause screen if esc is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            parent.pause();
            parent.setScreen(parent.pause);
        }
        super.render(delta);
    }

    /**
     * disposed of all stuff it something is missing from this method you will get memory leaks
     */
    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.cleanUp();
        EntityManager.cleanUp();
        RenderingManager.cleanUp();
        PhysicsManager.cleanUp();
    }

    /**
     * Resize camera, effectively setting the viewport to display game assets
     * at pixel ratios other than 1:1.
     *
     * @param width  of camera viewport
     * @param height of camera viewport
     */
    @Override
    public void resize(int width, int height) {
        //((Table) actors.get(0)).setFillParent(false);
        super.resize(width, height);
        OrthographicCamera cam = RenderingManager.getCamera();
        cam.viewportWidth = width / ZOOM;
        cam.viewportHeight = height / ZOOM;
        cam.update();

        // ((Table) actors.get(0)).setFillParent(true);
    }

    /**
     * Update the UI with new values for health, quest status, etc.
     * also called once per frame but used for actors by my own convention
     */
    //private String prevQuest = "";
    @Override
    protected void update() {
        super.update();
        Player p = GameManager.getPlayer();

        if (p.getHealth() <= 0) {
            parent.setScreen(parent.end);
        }

        minimapTable.clear();
        int width = Math.round(100 * ratio * TILE_SIZE);
        int height = Math.round(100 * ratio * TILE_SIZE);


//        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.1f, 0.1f, 0.1f, .6f));
        pixmap.fillRectangle(0, 0, width, height);


        for(Ship ship : GameManager.getShips()) {
            int player_radius;
            Color clr;
            if(ship == GameManager.getPlayer()) {
                player_radius = 4;
                clr = new Color(.1f, 0.1f, 1f, .3f);
            } else if(ship.getFaction() == GameManager.getPlayer().getFaction()) {
                player_radius = 1;
                clr = new Color(.1f, 1f, .1f, .3f);
            }
            else {
                player_radius = 2;
                clr = new Color(1f, 0.1f, 0.1f, .3f);
            }
            int player_x = Math.round(ship.getPosition().x * ratio) + player_radius/2;
            int player_y = height - Math.round(ship.getPosition().y * ratio) - player_radius/2;
            pixmap.setColor(clr);
            pixmap.fillCircle(player_x, player_y, player_radius);
        }
        for (College college : GameManager.getColleges()) {
            for(Building building: college.getBuildings()) {
                if(building.isAlive()) {
                    int player_x = Math.round(building.getPosition().x * ratio) + 3/2;
                    int player_y = height - Math.round(building.getPosition().y * ratio) - 3/2;
                    if(building.getFaction() == GameManager.getPlayer().getFaction()) {
                        pixmap.setColor(new Color(.1f, 1f, .1f, .3f));
                    } else {
                        pixmap.setColor(new Color(1f, 0.1f, 0.1f, .3f));
                    }
                    pixmap.fillCircle(player_x, player_y, 3);
                }
            }
        }

        cc.setText(EnhancementManager.getdisplay());
//        cc.setText("Hey");

        minimapTable.add(new Image(new Texture(new PixmapTextureData(pixmap, Pixmap.Format.RGBA8888, false, false, true))));


        healthLabel.setText(String.valueOf(p.getHealth()));
        armorLabel.setText(String.valueOf(p.getArmor()));
        dosh.setText(String.valueOf(p.getPlunder()));
        ammo.setText(String.valueOf(p.getAmmo()));

        healthTax.setText(String.valueOf((int)EnhancementManager.getTaxation(EnhancementManager.enhancement.HEALTH)));
        speedTax.setText(String.valueOf((int)EnhancementManager.getTaxation(EnhancementManager.enhancement.SPEED)));
        ammoTax.setText(String.valueOf((int)EnhancementManager.getTaxation(EnhancementManager.enhancement.AMMO)));
        armorTax.setText(String.valueOf((int)EnhancementManager.getTaxation(EnhancementManager.enhancement.ARMOR)));
        immunityTax.setText(String.valueOf((int)EnhancementManager.getTaxation(EnhancementManager.enhancement.IMMUNITY)));
        bulletspeedTax.setText(String.valueOf((int)EnhancementManager.getTaxation(EnhancementManager.enhancement.BULLETSPEED)));

        if (!QuestManager.anyQuests()) {
            parent.end.win();
            parent.setScreen(parent.end);

        } else {
            Quest q = QuestManager.currentQuest();
            /*if(Objects.equals(prevQuest, "")) {
                prevQuest = q.getDescription();
            }
            if(!Objects.equals(prevQuest, q.getDescription())) {
                questComplete.setText("Quest Competed");
                prevQuest = "";
            }*/
            questName.setText(q.getName());
            questDesc.setText(q.getDescription());
        }
        /*if(!questComplete.getText().equals("")) {
            showTimer += EntityManager.getDeltaTime();
        }
        if(showTimer >= showDuration) {
            showTimer = 0;
            questComplete.setText("");
        }*/
        EnhancementManager.update();
    }

    /**
     * Draw UI elements showing player health, plunder, and ammo.
     */
    @Override
    protected void CreateActors() {
        Table table = new Table();
        table.setFillParent(true);
        actors.add(table);

        table.add(new Image(ResourceManager.getTexture(ResourceManager.getId("HeatFull.png")))).top().left().size(1.25f * TILE_SIZE);
        healthLabel = new Label("N/A", parent.skin);
        table.add(healthLabel).top().left().size(50);

        table.row();

        table.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Shield.png")))).top().left().size(1.25f * TILE_SIZE);
        armorLabel = new Label("N/A", parent.skin);
        table.add(armorLabel).top().left().size(50);

        table.row();

        table.setDebug(false);

        table.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Coin.png")))).top().left().size(1.25f * TILE_SIZE);
        dosh = new Label("N/A", parent.skin);
        table.add(dosh).top().left().size(50);

        table.row();

        table.add(new Image(ResourceManager.getTexture(ResourceManager.getId("Arrow.png")))).top().left().size(1.25f * TILE_SIZE);
        ammo = new Label("N/A", parent.skin);
        table.add(ammo).top().left().size(50);

        table.top().left();
    }
}
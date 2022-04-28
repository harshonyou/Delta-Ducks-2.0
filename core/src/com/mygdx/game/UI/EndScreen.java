package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Entitys.Player;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;

import static com.mygdx.utils.Constants.VIEWPORT_HEIGHT;

/**
 * Contains widgets defining the game end screen.
 */
public class EndScreen extends Page {
    Label wonText;
    Label playerStats;

    private BitmapFont font;
    private Skin skinButton;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;

    public EndScreen(PirateGame game) {
        super(game);
    }

    /**
     * Set game end screen status to report a win.
     */
    public void win() {
        wonText.setText("Congrats You Have Won");
    }

    /**
     * Create game end screen widgets, initialised to game loss status.
     */
    @Override
    protected void CreateActors() {
        font = ResourceManager.genFont(50);
        skinButton = new Skin();
        buttonAtlas = new TextureAtlas("button/buttonmap.atlas");
        skinButton = new Skin(buttonAtlas);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.up = skinButton.getDrawable("buttonpress");
        textButtonStyle.down = skinButton.getDrawable("buttonpress");
        textButtonStyle.over = skinButton.getDrawable("button");

        Table t = new Table();
//        t.setBackground(new TextureRegionDrawable(ResourceManager.getTexture("menuBG.jpg")));

        float space = VIEWPORT_HEIGHT * 0.15f;
        t.setFillParent(true);
        actors.add(t);
        wonText = new Label("You have lost, try again!", new Label.LabelStyle(font, Color.BLACK));
        wonText.setFontScale(.5f);
        t.top();
        t.add(wonText).spaceBottom(space);
        t.row();
        playerStats = new Label("Player Stats:\n", new Label.LabelStyle(font, Color.BLACK));
        playerStats.setFontScale(.4f);
        t.add(playerStats).spaceBottom(space);
        t.row();
        TextButton b = new TextButton("Exit", textButtonStyle);
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                System.exit(0);
            }
        });
        b.getLabel().setFontScale(.3f);
        t.add(b).size(150, 100);

        t.center();
    }

    @Override
    protected void update() {
        super.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            System.exit(0);
        }
    }

    /**
     * Get player stats such as plunder etc. and display game end screen.
     */
    @Override
    public void show() {
        super.show();
        Player p = GameManager.getPlayer();
        String stats = String.format("Health: %s\nAmmo: %s\nPlunder: %s", p.getHealth(), p.getAmmo(), p.getPlunder());
        playerStats.setText(stats);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Table t = (Table) actors.get(0);
        TextureRegion luffy = new TextureRegion(
                ResourceManager.getTexture("darealthang.png"),
                ResourceManager.getTexture("darealthang.png").getWidth()/2 - width/2,
                ResourceManager.getTexture("darealthang.png").getHeight()/2 - height/2,
                width,
                height
        );
        t.setBackground(new TextureRegionDrawable(luffy)); // prevent the bg being stretched
    }
}

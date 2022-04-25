package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Managers.SaveManager;
import com.mygdx.game.PirateGame;

import static com.mygdx.utils.Constants.VIEWPORT_HEIGHT;

/**
 * Contains widgets defining the start-of-game menu screen.
 */
public class MenuScreen extends Page {
    private BitmapFont font;
    private Skin skinButton;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;

    public MenuScreen(PirateGame parent) {
        super(parent);
    }

    /**
     * Create menu widgets such as start button, labels, etc.
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
        t.setFillParent(true);

        float space = VIEWPORT_HEIGHT * 0.05f;

//        t.setBackground(new TextureRegionDrawable(ResourceManager.getTexture("luffy.jpg")));
        Label l = new Label("Pirates the movie the game", new Label.LabelStyle(font, Color.BLACK));
        l.setFontScale(.5f);
        t.add(l).top().spaceBottom(space * 0.5f);
        t.row();

        TextButton play = new TextButton("Play", textButtonStyle);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.level);
            }
        });
        play.getLabel().setFontScale(.3f);
        t.add(play).top().size(150, 100).spaceBottom(space);
        t.row();

        TextButton resume = new TextButton("Resume", textButtonStyle);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                parent.setScreen(parent.game);
                SaveManager.load();
                parent.setScreen(parent.game);
            }
        });
        resume.getLabel().setFontScale(.3f);
        t.add(resume).top().size(150, 100).spaceBottom(space);
        t.row();

//        TextButton quit = new TextButton("Quit", parent.skin);
        TextButton quit = new TextButton("Quit", textButtonStyle);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                System.exit(0);
            }
        });
        quit.getLabel().setFontScale(.3f);
        t.add(quit).size(150, 100).top().spaceBottom(space);

        t.center();

        actors.add(t);
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    public void hide() {
        super.hide();
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

//        t.debug();

    }
}
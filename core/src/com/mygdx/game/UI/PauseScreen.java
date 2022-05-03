package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Managers.SaveManager;
import com.mygdx.game.PirateGame;

import static com.mygdx.utils.Constants.VIEWPORT_HEIGHT;

/**
 * added the whole class for assessment 2
 * Contains widgets defining the pause screen.
 */
public class PauseScreen extends Page {
    private BitmapFont font;
    private Skin skinButton;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;

    public PauseScreen(PirateGame parent) {
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

//        t.setBackground(new TextureRegionDrawable(ResourceManager.getTexture("menuBG.jpg")));
        Label l = new Label("Pause Screen", new Label.LabelStyle(font, Color.BLACK));
        l.setFontScale(.5f);
        t.add(l).top().spaceBottom(space * 0.5f);
        t.row();

        TextButton resume = new TextButton("Resume", textButtonStyle);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.game);
            }
        });
        resume.getLabel().setFontScale(.3f);
        t.add(resume).top().size(150, 100).spaceBottom(space);
        t.row();

        TextButton save = new TextButton("Save", textButtonStyle);
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveManager.save();
//                SaveManager.test();
                System.out.println("Saved!");
            }
        });
        save.getLabel().setFontScale(.3f);
        t.add(save).top().size(150, 100).spaceBottom(space);
        t.row();

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
         /*
        Added the background image
         */
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

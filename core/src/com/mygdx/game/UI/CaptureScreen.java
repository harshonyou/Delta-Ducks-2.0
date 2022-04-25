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
import com.mygdx.game.Entitys.Building;
import com.mygdx.game.Managers.CaptureManager;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.Managers.SaveManager;
import com.mygdx.game.PirateGame;

import static com.mygdx.utils.Constants.VIEWPORT_HEIGHT;
/**
 * Contains widgets defining the start-of-game menu screen.
 */
public class CaptureScreen extends Page {
    private BitmapFont font;
    private Skin skinButton;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;

    private Building tempFlag;

    public CaptureScreen(PirateGame parent) {
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
        Label l = new Label("Would you like to either\ncapture or destroy the college?", new Label.LabelStyle(font, Color.BLACK));
        l.setFontScale(.5f);
        l.setAlignment(3);
        t.add(l).top().spaceBottom(space * 0.5f);
        t.row();

        TextButton capture = new TextButton("Capture", textButtonStyle);
        capture.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                CaptureManager;
                tempFlag.setFaction();
                tempFlag.updateFlag();
                parent.setScreen(parent.game);
            }
        });
        capture.getLabel().setFontScale(.3f);
        t.add(capture).top().size(150, 100).spaceBottom(space);
        t.row();

        TextButton destroy = new TextButton("Destroy", textButtonStyle);
        destroy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tempFlag.destroyFlag();
                parent.setScreen(parent.game);
            }
        });
        destroy.getLabel().setFontScale(.3f);
        t.add(destroy).top().size(150, 100).spaceBottom(space);
        t.row();

        t.center();

        actors.add(t);
    }

    public void updateFlag(Building flag) {
        tempFlag = flag;
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
    }
}

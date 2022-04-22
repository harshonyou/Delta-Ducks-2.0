package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Managers.ResourceManager;
import com.mygdx.game.PirateGame;

import static com.mygdx.utils.Constants.VIEWPORT_HEIGHT;

/**
 * Contains widgets defining the start-of-game menu screen.
 */
public class MenuScreen extends Page {
    public MenuScreen(PirateGame parent) {
        super(parent);
    }
    private static int difficulty;
    /**
     * Create menu widgets such as start button, labels, etc.
     */
    @Override
    protected void CreateActors() {
        Table t = new Table();
        t.setFillParent(true);

        float space = VIEWPORT_HEIGHT * 0.25f;

        t.setBackground(new TextureRegionDrawable(ResourceManager.getTexture("menuBG.jpg")));
        Label l = new Label("Pirates the movie the game", parent.skin);
        l.setFontScale(2);
        t.add(l).top().spaceBottom(space * 0.5f);
        t.row();

        TextButton playEasy = new TextButton("Play - Easy Difficulty", parent.skin);
        playEasy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficulty = 1;
                parent.setScreen(parent.game);
            }
        });
        t.add(playEasy).top().size(175, 25).spaceBottom(space);
        t.row();

        TextButton playMedium = new TextButton("Play - Medium Difficulty", parent.skin);
        playMedium.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficulty = 2;
                parent.setScreen(parent.game);
            }
        });
        t.add(playMedium).top().size(175, 25).spaceBottom(space);
        t.row();

        TextButton playHard = new TextButton("Play - Hard Difficulty", parent.skin);
        playHard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficulty = 3;
                parent.setScreen(parent.game);
            }
        });
        t.add(playHard).top().size(175, 25).spaceBottom(space);
        t.row();

        TextButton quit = new TextButton("Quit", parent.skin);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                System.exit(0);
            }
        });
        t.add(quit).size(100, 25).top().spaceBottom(space);

        t.top();

        actors.add(t);
    }

    public static int getDifficulty() {
        return difficulty;
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
        t.setBackground(new TextureRegionDrawable(ResourceManager.getTexture("menuBG.jpg"))); // prevent the bg being stretched
    }
}

package com.mygdx.game.Entitys;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Components.Pirate;
import com.mygdx.game.Components.PlayerController;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Managers.GameManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Managers.ResourceManager;

/**
 * Player's ship entity.
 */
public class Player extends Ship {

    private Renderable healthBar;

    /**
     * Adds ship with PlayerController component and sets its speed.
     *
     * @param speed of movement across map
     */
    private Player(float speed) {
        super();

        PlayerController pc = new PlayerController(this, speed);
        addComponent(pc);

        setName("Player");

        healthBar = new Renderable(ResourceManager.getId("blank.png"), RenderLayer.Transparent);

        addComponents(healthBar);
        healthBar.show();
    }

    @Override
    public void update() {
        super.update();
        if (getHealth() <= 0) {
            healthBar.hide();
        } else {
            if (getHealth() > 80f)
                healthBar.setColor(Color.valueOf("58BA49"));
            else if (getHealth() > 70f)
                healthBar.setColor(Color.valueOf("92C83E"));
            else if (getHealth() > 60f)
                healthBar.setColor(Color.valueOf("E9D93B"));
            else if (getHealth() > 50f)
                healthBar.setColor(Color.valueOf("EF9D2F"));
            else if (getHealth() > 40f)
                healthBar.setColor(Color.valueOf("F24824"));
            else if (getHealth() > 30f)
                healthBar.setColor(Color.valueOf("D72531"));
            else
                healthBar.setColor(Color.valueOf("7A1115"));
            healthBar.setSize(3*(getHealth()/10), 1);
        }
    }

    /**
     * Adds ship with PlayerController component, loading its speed from GameManager settings.
     */
    public Player() {
        this(GameManager.getSettings().get("starting").getFloat("playerSpeed"));
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }

    public int getAmmo() {
        return getComponent(Pirate.class).getAmmo();
    }

    public void setPlayerSpeed(float s) {
        getComponent(PlayerController.class).setPlayerSpeed(s);
    }
    
}

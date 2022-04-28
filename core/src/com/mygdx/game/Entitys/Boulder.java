package com.mygdx.game.Entitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Components.Obstacles;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.*;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;

public class Boulder extends Entity implements CollisionCallBack {
    private static int boulderCount = 0;
    private float timeoutCounter;
    private float TIMEOUT = 1f;

    private boolean contact;


    public Boulder() {
        super(4);
        setName("Boulder (" + boulderCount++ + ")");

        Transform t = new Transform();
        t.setPosition(800, 800);
//        Renderable r = new Renderable(3, "white-up", RenderLayer.Transparent); Icons_14.png
        Renderable r = new Renderable(5, "Icons_14.png", RenderLayer.Transparent);
        RigidBody rb = new RigidBody(PhysicsBodyType.Static, r, t, false, true);
        rb.setCallback(this);
        rb.addTrigger(40, "corrode");

        Obstacles o = new Obstacles();

        addComponents(t, r, rb, o);

        timeoutCounter = 0;
        contact = false;
    }

    public void initiateContact() {

    }

    public void stopContact() {

    }

    @Override
    public void update() {
        super.update();
        timeoutCounter += EntityManager.getDeltaTime();

        if(contact) {
            if (timeoutCounter > TIMEOUT) {
                if(GameManager.getPlayer().getArmor() - 1 <= 0) {
                    GameManager.getPlayer().setArmor(0);
                } else {
                    GameManager.getPlayer().setArmor(GameManager.getPlayer().getArmor() - 1);
                }
                timeoutCounter = 0;
            }
        }
    }

    @Override
    public void BeginContact(CollisionInfo info) {
    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    @Override
    public void EnterTrigger(CollisionInfo info) {
        if(info.a instanceof Player) {
            CaptionManager.setDisplay("Your armor will corrode if you stay near the boulder.");
            contact = true;
        }
    }

    @Override
    public void ExitTrigger(CollisionInfo info) {
        if(info.a instanceof Player) {
            CaptionManager.setDisplay("");
            contact = false;
        }
    }
}

package com.mygdx.game.Entitys;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Obstacles;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;

public class Boulder extends Entity implements CollisionCallBack {
    private static int shipCount = 0;


    public Boulder() {
        super(4);
        setName("Boulder (" + shipCount++ + ")");

        Transform t = new Transform();
        t.setPosition(800, 800);
//        Renderable r = new Renderable(3, "white-up", RenderLayer.Transparent); Icons_14.png
        Renderable r = new Renderable(5, "Icons_14.png", RenderLayer.Transparent);
        RigidBody rb = new RigidBody(PhysicsBodyType.Static, r, t);
        rb.setCallback(this);

        Obstacles o = new Obstacles();

        addComponents(t, r, rb, o);
    }

    @Override
    public void BeginContact(CollisionInfo info) {
//        System.out.println("Bruh");
    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    @Override
    public void EnterTrigger(CollisionInfo info) {
//        System.out.println("Yikes");
    }

    @Override
    public void ExitTrigger(CollisionInfo info) {

    }
}

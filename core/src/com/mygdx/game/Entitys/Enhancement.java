package com.mygdx.game.Entitys;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Components.Obstacles;
import com.mygdx.game.Components.Renderable;
import com.mygdx.game.Components.RigidBody;
import com.mygdx.game.Components.Transform;
import com.mygdx.game.Managers.CaptionManager;
import com.mygdx.game.Managers.EnhancementManager;
import com.mygdx.game.Managers.RenderLayer;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;
import com.mygdx.game.Physics.PhysicsBodyType;

public class Enhancement extends Entity implements CollisionCallBack {
    private static int enhancementCount = 0;
    private static int random;
    private boolean contact;

    public Enhancement() {
        super(4);
        setName("Boulder (" + enhancementCount++ + ")");

        Transform t = new Transform();
        t.setPosition(900, 700);

        random = (int)(Math.random() * (6)) + 1;
        String ran;

        switch (random) {
            case 1:
                ran = "Icons_12.png";
                break;
            case 2:
                ran = "Icons_10.png";
                break;
            case 3:
                ran = "Icons_29.png";
                break;
            case 4:
                ran = "Icons_40.png";
                break;
            case 5:
                ran = "Icons_30.png";
                break;
            default:
                ran = "Icons_22.png";
                break;
        }

        Renderable r = new Renderable(5, ran, RenderLayer.Transparent);
        RigidBody rb = new RigidBody(PhysicsBodyType.Static, r, t, false, true);
        rb.setCallback(this);

        Obstacles o = new Obstacles();

        r.setSize(20, 20);
        r.setDisplacement(5, 5);

        addComponents(t, r, rb, o);

        contact = false;
    }

    @Override
    public void update() {
        super.update();

        if(contact) {
            this.getComponent(Renderable.class).hide();
            Transform t = this.getComponent(Transform.class);
            t.setPosition(-50, -50);
        }
    }

    @Override
    public void BeginContact(CollisionInfo info) {
        if(info.a instanceof Player && !contact) {
            switch (random) {
                case 1:
                    EnhancementManager.healthHandler(true);
                    break;
                case 2:
                    EnhancementManager.speedHandler(true);
                    break;
                case 3:
                    EnhancementManager.ammoHandler(true);
                    break;
                case 4:
                    EnhancementManager.armorHandler(true);
                    break;
                case 5:
                    EnhancementManager.immunityHandler(true);
                    break;
                default:
                    EnhancementManager.infiniteAmmoHandler(true);
                    break;
            }
            contact = true;
        }
    }

    @Override
    public void EndContact(CollisionInfo info) {

    }

    @Override
    public void EnterTrigger(CollisionInfo info) {

    }

    @Override
    public void ExitTrigger(CollisionInfo info) {

    }
}

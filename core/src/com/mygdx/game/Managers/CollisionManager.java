package com.mygdx.game.Managers;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Entitys.Entity;
import com.mygdx.game.Physics.CollisionCallBack;
import com.mygdx.game.Physics.CollisionInfo;

/**
 * Handels collision callbacks for box2d
 */
public class CollisionManager implements ContactListener {
    private static boolean initialized = false;

    public CollisionManager() {
        if (initialized) {
            throw new RuntimeException("Collision manager cant be instantiated more then once");
        }
        initialized = true;
    }

    /**
     * called for every contact that box2d detects prior to collision restitution (doesn't matter if it is a trigger/sensor)
     *
     * @param contact the contact data
     */
    @Override
    public void beginContact(Contact contact) {
        // generally calls the correct callback on the appropriate objects (not as intuitive as id like though)
        Fixture fa = contact.getFixtureA();
        Body ba = fa.getBody();
        Object oa = ba.getUserData();
        CollisionCallBack cbA = (CollisionCallBack) oa;

        Fixture fb = contact.getFixtureB();
        Body bb = fb.getBody();
        Object ob = bb.getUserData();
        CollisionCallBack cbB = (CollisionCallBack) ob;

        final CollisionInfo info = new CollisionInfo();
        info.fA = fa;
        info.fB = fb;

        info.bA = ba;
        info.bB = bb;

        info.a = (Entity) cbA;
        info.b = (Entity) cbB;

        if (cbA != null) {
            // fa is sensor but not fb
            if (fa.isSensor() && cbB != null && !fb.isSensor()) {
                cbB.EnterTrigger(info);
            } else {
                cbA.BeginContact(info);
            }
        }

        if (cbB != null) {
            if (fb.isSensor() && cbA != null && !fa.isSensor()) {
                cbA.EnterTrigger(info);
            } else {
                cbB.BeginContact(info);
            }
        }
    }

    /**
     * called for every contact that box2d detects after collision restitution (doesn't matter if it is a trigger/sensor)
     *
     * @param contact the contact data
     */
    @Override
    public void endContact(Contact contact) {
        // generally calls the correct callback on the appropriate objects (not as intuitive as id like though)
        Fixture fa = contact.getFixtureA();
        Body ba = fa.getBody();
        Object oa = ba.getUserData();
        CollisionCallBack cbA = (CollisionCallBack) oa;

        Fixture fb = contact.getFixtureB();
        Body bb = fb.getBody();
        Object ob = bb.getUserData();
        CollisionCallBack cbB = (CollisionCallBack) ob;

        final CollisionInfo info = new CollisionInfo();
        info.fA = fa;
        info.fB = fb;

        info.bA = ba;
        info.bB = bb;

        info.a = (Entity) cbA;
        info.b = (Entity) cbB;

        if (cbA != null) {
            if (fa.isSensor() && cbB != null && !fb.isSensor()) {
                cbB.ExitTrigger(info);
            } else {
                cbA.EndContact(info);
            }
        }

        if (cbB != null) {
            if (fb.isSensor() && cbA != null && !fa.isSensor()) {
                cbA.ExitTrigger(info);
            } else {
                cbB.EndContact(info);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

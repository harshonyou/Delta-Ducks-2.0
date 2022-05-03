package com.mygdx.game.Components;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Utilities;

/**
 * Position, Scale, Rotation (in radians clockwise)
 */
public class Transform extends Component implements Location<Vector2> {
    private final Vector2 position;
    private final Vector2 scale;
    private float rotation;

    /**
     * position = (0, 0)
     * scale = (0, 0)
     * rotation = 0
     * rot not used but easy to add functionality for
     */
    public Transform() {
        position = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
        type = ComponentType.Transform;
    }

    /**
     * Set position associated with the Transform component.
     *
     * @param pos 2D vector specifying the position
     * @param rb  true to pass on the position to the parent's RigidBody
     */
    public void setPosition(Vector2 pos, boolean rb) {
        setPosition(pos.x, pos.y, rb);
    }

    /**
     * Set position associated with the Transform component.
     *
     * @param x   coordinate
     * @param y   coordinate
     * @param rb_ true to pass on the position to the parent's RigidBody
     */
    public void setPosition(float x, float y, boolean rb_) {
        position.set(x, y);
        if (parent != null && rb_) {
            RigidBody rb = parent.getComponent(RigidBody.class);
            if (rb != null) {
                rb.setPosition(position);
            }
        }
    }

    /**
     * Set position associated with the Transform component.
     *
     * @param pos 2D vector specifying the position
     */
    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }

    /**
     * Set position associated with the Transform component.
     *
     * @param x coordinate
     * @param y coordinate
     */
    public void setPosition(float x, float y) {
        position.set(x, y);
        if (parent != null) {
            RigidBody rb = parent.getComponent(RigidBody.class);
            if (rb != null) {
                rb.setPosition(position, true);
            }
        }
    }

    public void setScale(Vector2 pos) {
        scale.set(pos);
    }

    public void setScale(float x, float y) {
        scale.set(x, y);
    }

    /**
     * @param rot in Radians
     */
    public void setRotation(float rot) {
        rotation = rot;
    }

    public Vector2 getPosition() {
        return position;
    }

    /**
     * Added for assessment 2
     * @return the velocity of an object
     */
    public Vector2 getVelocity() {
        RigidBody rb = parent.getComponent(RigidBody.class);
        return rb.getVelocity();
    }

    /**
     * returns the box2d position of the parent or the transform pos if no rigidbody found
     *
     * @return the center of the Entity or bottom left
     */
    public Vector2 getCenter() {
        RigidBody rb = parent.getComponent(RigidBody.class);
        if (rb == null) {
            return getPosition();
        }
        return rb.getBody().getWorldCenter();
    }

    // For Ai Navigation

    public void getLocation() {

    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return Utilities.vectorToAngle(vector);
    }

    /**
     * Return new vector combining input vector with input angle in radians.
     *
     * @param outVector 2D vector
     * @param angle     in radians
     * @return the angle as a vector
     */
    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return Utilities.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Transform();
    }

    public Vector2 getScale() {
        return scale;
    }

    /**
     * @return radians
     */
    public float getRotation() {
        return rotation;
    }
}

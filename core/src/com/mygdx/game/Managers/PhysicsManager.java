package com.mygdx.game.Managers;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Components.TileMap;

import java.util.ArrayList;
import java.util.Objects;

import static com.mygdx.utils.Constants.PHYSICS_TIME_STEP;

/**
 * Manages the box2D world and bodies for the collision detection and physics
 */
public final class PhysicsManager {
    private static final float TILE_SIZE_INV = 1.0f;
    public static boolean initialized = false;
    public static World box2DWorld;
    private static ArrayList<Body> box2DBodies;
    private static Box2DDebugRenderer debug;

    public static void Initialize() {
        Initialize(false);
    }

    /**
     * Draw the box2D world with debug borders shown.
     *
     * @param drawDebug true to show debug borders
     */
    public static void Initialize(boolean drawDebug) {
        if (initialized) {
            return;
        }
        initialized = true;
        box2DWorld = new World(new Vector2(0, 0), true);
        box2DBodies = new ArrayList<>();
        box2DWorld.setContactListener(new CollisionManager());
        if (drawDebug) {
            debug = new Box2DDebugRenderer(true, false, true, true, false, true);
        }
    }

    public static int createBody(BodyDef bDef, FixtureDef fDef, Object userData) {
        tryInit();
        bDef.fixedRotation = true;
        Body b = box2DWorld.createBody(bDef);
        b.createFixture(fDef);
        b.setUserData(userData);
        box2DBodies.add(b);
        return box2DBodies.size();
    }

    private static Shape tile_getShape(Rectangle rectangle) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(
                rectangle.width * 0.5f * TILE_SIZE_INV,
                rectangle.height * 0.5f * TILE_SIZE_INV);
        return polygonShape;
    }

    private static Vector2 tile_getCenter(Rectangle rectangle) {
        Vector2 center = new Vector2();
        rectangle.getCenter(center);
        return center.scl(TILE_SIZE_INV);
    }

    /**
     * Populates the map with box2D bodies necessary for collisions to happen.
     *
     * @param map tilemap we are operating on
     */
    public static void createMapCollision(TileMap map) {
        MapLayers layers = map.getTileMap().getLayers();
        MapObjects objects = null;
        for (MapLayer layer : layers) {
            if (Objects.equals(layer.getName(), "Objects")) {
                objects = layer.getObjects();
                break;
            }
        }
        if (objects == null) {
            return;
        }

        for (MapObject object : objects) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            //create a dynamic within the world body (also can be KinematicBody or StaticBody
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = box2DWorld.createBody(bodyDef);

            //create a fixture for each body from the shape
            Fixture fixture = body.createFixture(tile_getShape(rectangle), 1);
            fixture.setFriction(0.1f);

            //setting the position of the body's origin. In this case with zero rotation
            body.setTransform(tile_getCenter(rectangle), 0);
        }
    }

    public static Body getBody(int id) {
        return box2DBodies.get(id - 1);
    }

    private static void tryInit() {
        if (!initialized) {
            Initialize();
        }
    }

    public static void update() {
        tryInit();
        box2DWorld.step(PHYSICS_TIME_STEP, 6, 2);

        if (debug != null) {
            debug.render(box2DWorld, RenderingManager.getCamera().combined);
        }
    }

    public static void cleanUp() {
        tryInit();
        box2DWorld.dispose();
        if (debug != null) {
            debug.dispose();
        }
    }
}
